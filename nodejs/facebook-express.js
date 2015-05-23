var winston = require('winston');
winston.setLevels({
    debug: 0,
    info: 1,
    silly: 2,
    warn: 3,
    error: 4,
});
winston.addColors({
    debug: 'green',
    info: 'cyan',
    silly: 'magenta',
    warn: 'yellow',
    error: 'red'
});
winston.remove(winston.transports.Console);
winston.add(winston.transports.Console, {
    level: 'debug',
    colorize: true
});

winston.debug("debug");
winston.info("info");
winston.silly("silly");
winston.warn("warn");


var async = require('async');
var graph = require('fbgraph');
var fbAppId = process.env.FB_APP_ID;
var fbAppSecret = process.env.FB_APP_SECRET;
var secrets = {
    facebook: {
        clientID: fbAppId,
        clientSecret: fbAppSecret,
        callbackURL: '/auth/facebook/callback',
        passReqToCallback: true
    },
    db: 'mongodb://localhost:27017/test',
    sessionSecret: process.env.SESSION_SECRET || 'Your Session Secret goes here'
};
/**
 * Connect to MongoDB.
 */
var session = require('express-session');
var MongoStore = require('connect-mongo')(session);
var mongoose = require('mongoose');
mongoose.connect(secrets.db);
mongoose.connection.on('error', function() {
    console.error('MongoDB Connection Error. Please make sure that MongoDB is running.');
});

var express = require('express');
var passport = require('passport');
var path = require('path');
var expressValidator = require('express-validator');
var connectAssets = require('connect-assets');
var multer = require('multer');
var methodOverride = require('method-override');

var cookieParser = require('cookie-parser');
var compress = require('compression');
var bodyParser = require('body-parser');

var app = express();
app.use(compress());
app.use(connectAssets({
    paths: [path.join(__dirname, 'public/css'), path.join(__dirname, 'public/js')]
}));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(multer({
    dest: path.join(__dirname, 'public/images')
}));
app.use(expressValidator());
app.use(methodOverride());
app.use(cookieParser());
app.use(session({
    resave: true,
    saveUninitialized: true,
    secret: secrets.sessionSecret,
    store: new MongoStore({
        url: secrets.db,
        autoReconnect: true
    })
}));
app.use(passport.initialize());
app.use(passport.session());
var flash = require('flash');
app.use(flash());
var lusca = require('lusca');
app.use(lusca({
    // AA: csrf: true,
    csrf: false,
    xframe: 'SAMEORIGIN',
    xssProtection: true
}));
app.use(function(req, res, next) {
    res.locals.user = req.user;
    next();
});
app.use(function(req, res, next) {
    if (/api/i.test(req.path)) req.session.returnTo = req.path;
    next();
});

app.use(express.static('.'));

// to auth package
passport.serializeUser(function(user, done) {
    winston.info("serializeUser, id = ", user.id);
    done(null, user.id);
});

// to auth package
passport.deserializeUser(function(id, done) {
    winston.info("deserializeUser, id = ", id);
    User.findById(id, function(err, user) {
        done(err, user);
    });
});

// to auth package
var FacebookStrategy = require('passport-facebook').Strategy;

// to auth package
var User = require('./User');


/* 
    OAuth Strategy Overview
    
    -User is already logged in .
*-Check if there is an existing account with a provider id.
*-If there is, return an error message.(Account merging not supported) 
* -Else link new OAuth account with currently logged - in user.
*-User is not logged in .
*-Check if it 's a returning user. 
* -If returning user, sign in and we are done.
*-Else check if there is an existing account with user 's email. 
* -If there is, return an error message.
 *-Else create a new account.
*/



/**
 * Login Required middleware.
 */
// to auth package, exported
exports.isCustomAuthenticated = function(req, res, next) {
    winston.info("in isAuthenticated()");

    if (req.body !== undefined && (req.body.FBAppScopeId !== undefined && (req.body.loginCookie !== undefined || req.body.adminPassword !== undefined))) {
        // it's an ios call
        if (req.body.adminPassword !== undefined && req.body.adminPassword == "bathtub") {
            winston.info("admin login");
            return next();
        } else {
            winston.info("isAuthenticated, waterfall; body = " + JSON.stringify(req.body, null, 4));
            async.waterfall(
                [

                    function(cb2) {
                        User.findOne({
                                facebook: req.body.FBAppScopeId
                            },
                            function(err, user) {
                                if (err) {
                                    cb2(err);
                                    return;
                                }
                                if (user === null) {
                                    cb2("no user with given FBAppScopeId " + FBAppScopeId);
                                    return;
                                }
                                if (user.loginCookie !== req.body.loginCookie) {
                                    cb2("bad login credentials");
                                    return;
                                }
                                // all checks pass
                                req.user = user; // trying to mimic passport
                                cb2(null);
                            });
                    }
                ],
                function(err) {
                    winston.info("in final callback, err = ", err);
                    if (err) {
                        res.json({
                            code: 500,
                            msg: err
                        });
                        return;
                    }
                    return next();
                }
            );
        }
    }
};

// to auth package, exported
exports.isPassportAuthenticated = function(req, res, next) {
    // not an api request, coming from a browser, so let passport handle it
    if (req.isAuthenticated()) {
        return next();
    }
    res.redirect('/login.html');
};

/**
 * Authorization Required middleware.
 */
// to auth package, exported. likely unused?
exports.isAuthorized = function(req, res, next) {
    var provider = req.path.split('/').slice(-1)[0];

    winston.info("isAuthorized, req.path = " + req.path);
    winston.info("isAuthorized, provider = " + provider);

    if (_.find(req.user.tokens, {
        kind: provider
    })) {
        next();
    } else {
        res.redirect('/auth/' + provider);
    }
};

/**
 * Sign in with Facebook.
 */
// to auth package
passport.use(new FacebookStrategy(secrets.facebook, function(req, accessToken, refreshToken, profile, done) {
    winston.info("0. accessToken = " + accessToken);
    winston.info("0. refreshToken = " + refreshToken);
    winston.info("0. profile = " + JSON.stringify(4, null, profile));
    winston.info("0. profile.id = " + profile.id);
    if (req.user) {
        winston.info("fbs 1. req.user is true");
        User.findOne({
            facebook: profile.id
        }, function(err, existingUser) {
            if (existingUser) {
                winston.info("fbs 2. existingUser is true, err = " + err);
                req.flash('errors', {
                    msg: 'There is already a Facebook account that belongs to you.' +
                        'Sign in with that account or delete it, then link it with your current account.'
                });
                done(err);
            } else {
                winston.info("fbs 3. existingUser is false");
                User.findById(req.user.id, function(err, user) {
                    user.facebook = profile.id;
                    user.tokens.push({
                        kind: 'facebook',
                        accessToken: accessToken
                    });
                    user.profile.name = user.profile.name || profile.displayName;
                    user.profile.gender = user.profile.gender || profile._json.gender;
                    user.profile.picture =
                        user.profile.picture ||
                        'https://graph.facebook.com/' + profile.id + '/picture?type=large';
                    user.save(function(err) {
                        req.flash('info', {
                            msg: 'Facebook account has been linked.'
                        });
                        done(err, user);
                    });
                });
            }
        });
    } else {
        winston.info("fbs 4. req.user is false");
        User.findOne({
            facebook: profile.id
        }, function(err, existingUser) {
            if (existingUser) {
                winston.info("fbs 5. - existing user, user = " + existingUser);
                return done(null, existingUser);
            }
            winston.info("fbs 6. no existing user");
            User.findOne({
                email: profile._json.email
            }, function(err, existingEmailUser) {
                if (existingEmailUser) {
                    req.flash('errors', {
                        msg: 'There is already an account using this email address.' +
                            'Sign in to that account and link it with Facebook manually ' +
                            'from Account Settings.'
                    });
                    done(err);
                } else {
                    winston.info("fbs 7. new User()");
                    var user = new User();
                    user.email = profile._json.email;
                    user.facebook = profile.id;
                    user.tokens.push({
                        kind: 'facebook',
                        accessToken: accessToken
                    });
                    user.profile.name = profile.displayName;
                    user.profile.gender = profile._json.gender;
                    user.profile.picture =
                        'https://graph.facebook.com/' + profile.id + '/picture?type=large';
                    user.profile.location =
                        (profile._json.location) ? profile._json.location.name : '';
                    user.save(function(err) {
                        done(err, user);
                    });
                }
            });
        });
    }
}));


var server = app.listen(3000, function() {
    var host = server.address().address;
    var port = server.address().port;
    winston.info('Example app listening at http://%s:%s', host, port);
});

// with app.js
app.use(function(req, res, next) {
    if (/api\/browser/i.test(req.path)) req.session.returnTo = req.path;
    next();
});

// with app.js
app.get('/auth/facebook', passport.authenticate('facebook'));

app.get('/auth/facebook/callback', passport.authenticate('facebook', {
    failureRedirect: '/login.html'
}), function(req, res) {
    winston.info('req.session.returnTo: ' + req.session.returnTo);
    res.redirect(req.session.returnTo || '/');
});

// two logout functions
app.get('/logout', exports.isPassportAuthenticated, function(req, res) {
    req.logout();
    res.redirect('logout.html');
});

// err implies user did not have right accessToken
// to auth package
var updateFbUser = function(existingFbUser, facebookuser, callback) {
    var user;
    // if we're here, he had a valid auth token/FBAppScopeId
    // TODO: use crypto to create right cookie
    var loginCookie = "cookie_" + facebookuser.FBAppScopeId;
    if (existingFbUser !== null) {
        // update the user
        //TODO: dups code in next block, use iter over facebookuser
        user = existingFbUser;
        user.email = facebookuser.email;
        user.profile = {
            picture: facebookuser.pictureUrl,
            name: facebookuser.name
        };
        user.loginCookie = loginCookie;
    } else {
        user = new User({
            facebook: facebookuser.id,
            email: facebookuser.email,
            profile: {
                picture: facebookuser.pictureUrl,
                name: facebookuser.name,
            }
        });
        user.loginCookie = loginCookie;
    }

    user.save(function(err, storeduser) {
        if (err) {
            winston.info("updateFbUserRes err:" + err);
            return callback(err);
        }
        callback(null, loginCookie);
    });
};

// to auth package
var options = {
    timeout: 3000,
    pool: {
        maxSockets: Infinity
    },
    headers: {
        connection: 'keep-alive'
    }
};

// to auth package
var getUserData = function(accessToken, existingUser, callback) {
    // idea: err automatically acts as an invalid login. we dont care
    /// about the login cookie now, since accessToken trumps that
    winston.info("accessToken =", accessToken);
    winston.info("existingUser =", JSON.stringify(existingUser, null, 4));
    async.waterfall(
        [

            function(cb1) {
                // TODO: why are website, location not coming? gender comes for doroty but not for adnan?
                var url = "me" + "?fields=name,email,website,location,gender&access_token=" + accessToken;
                graph.setOptions(options)
                    .get(url, cb1);
            },
            function(fbuser, cb2) {
                winston.info("2." + JSON.stringify(fbuser, null, 4));
                var url = "/picture?width=160&height=160&access_token=" + accessToken;
                graph.get(url, function(err, profilepic) {
                    if (err) {
                        cb2(err);
                        return;
                    }
                    winston.warn("fbuser = ", fbuser);
                    //TODO: can we just clone?
                    var facebookuser = {
                        name: fbuser.name,
                        email: fbuser.email,
                        location: fbuser.location,
                        gender: fbuser.gender,
                        website: fbuser.website,
                        id: fbuser.id,
                        pictureUrl: profilepic.location
                    }
                    winston.warn("callback with existingUser, facebookuser:", JSON.stringify(existingUser), JSON.stringify(facebookuser));

                    callback(null, existingUser, facebookuser);
                });
            }
        ]
    );

};

// to auth package
var logoutUser = function(req, res) {
    winston.info("logoutUser: 1");
    var request = req.body;
    if (request.FBAppScopeId === undefined) {
        // use server response call
        res.json({
            code: 500,
            msg: "no FBAppScopeId " + FBAppScopeId
        });
        return;
    }
    winston.info("logoutUser: 2");
    User.findOne({
            facebook: request.FBAppScopeId
        },
        function(err, user) {
            if (err) {
                // use server response call
                res.json({
                    code: 500,
                    msg: "logout user lookup error " + FBAppScopeId
                });
                return;
            }
            winston.info("logoutUser, user = " + user);
            user.loginCookie = "loggedOut";
            user.save(function(err, saveduser) {
                // use server response call
                if (err) {
                    res.json({
                        code: 500,
                        msg: "logout save error " + err
                    });
                    return
                }
                winston.info("logoutUser, save, succ = " + user);
                // use server response call
                res.json({
                    code: 200,
                    msg: "succ logout"
                });
            });
        }
    );
};

// to auth package
var createOrUpdateUser = function(req, res) {
    var request = req.body;
    if (request.FBAppScopeId === undefined) {
        res.json({
            code: 500,
            msg: "no FBAppScopeId " + FBAppScopeId
        });
        return;
    }
    User.findOne({
            facebook: request.FBAppScopeId
        },
        // user may be null if non matched. this could happen on a new user using
        // the app for the first time.
        function(err, user) {
            if (err) {
                res.json({
                    code: 500,
                    msg: "user lookup error " + FBAppScopeId
                });
                return;
            }
            async.waterfall(
                [
                    async.apply(getUserData, request.accessToken, user),
                    updateFbUser
                ],
                function(err, loginCookie) {
                    if (err) {
                        res.json({
                            code: 500,
                            msg: err
                        });
                        return;
                    }
                    res.json({
                        code: 200,
                        loginCookie: loginCookie
                    });
                });
        }
    );
};

// for ios (or any programmatic) requests.
app.post('/api/createOrUpdateFBUser', createOrUpdateUser);
app.post('/api/logoutUser', exports.isCustomAuthenticated, logoutUser);
app.post('/api/getUserData', exports.isCustomAuthenticated, function(req, res) {
    res.json(req.user);
});

// for browser requests.
app.get('/api/browser/getUserData', exports.isPassportAuthenticated, function(req, res) {
    res.send(req.user);
});
