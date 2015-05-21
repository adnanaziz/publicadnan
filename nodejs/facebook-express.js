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

passport.serializeUser(function(user, done) {
    console.log("serializeUser, id = ", user.id);
    done(null, user.id);
});

passport.deserializeUser(function(id, done) {
    console.log("deserializeUser, id = ", id);
    User.findById(id, function(err, user) {
        done(err, user);
    });
});

var FacebookStrategy = require('passport-facebook').Strategy;

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
exports.isAuthenticated = function(req, res, next) {
    console.log("in isAuthenticated()");
    if (req.isAuthenticated()) return next();
    res.redirect('/login.html');
};

/**
 * Authorization Required middleware.
 */
exports.isAuthorized = function(req, res, next) {
    var provider = req.path.split('/').slice(-1)[0];

    console.log("isAuthorized, req.path = " + req.path);
    console.log("isAuthorized, provider = " + provider);

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
passport.use(new FacebookStrategy(secrets.facebook, function(req, accessToken, refreshToken, profile, done) {
    console.log("0. accessToken = " + accessToken);
    console.log("0. refreshToken = " + refreshToken);
    console.log("0. profile = " + JSON.stringify(4, null, profile));
    console.log("0. profile.id = " + profile.id);
    if (req.user) {
        console.log("fbs 1. req.user is true");
        User.findOne({
            facebook: profile.id
        }, function(err, existingUser) {
            if (existingUser) {
                console.log("fbs 2. existingUser is true, err = " + err);
                req.flash('errors', {
                    msg: 'There is already a Facebook account that belongs to you.' +
                        'Sign in with that account or delete it, then link it with your current account.'
                });
                done(err);
            } else {
                console.log("fbs 3. existingUser is false");
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
        console.log("fbs 4. req.user is false");
        User.findOne({
            facebook: profile.id
        }, function(err, existingUser) {
            if (existingUser) {
                console.log("fbs 5. - existing user, user = " + existingUser);
                return done(null, existingUser);
            }
            console.log("fbs 6. no existing user");
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
                    console.log("fbs 7. new User()");
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

app.use(function(req, res, next) {
    if (/api/i.test(req.path)) req.session.returnTo = req.path;
    next();
});

var server = app.listen(3000, function() {
    var host = server.address().address;
    var port = server.address().port;
    console.log('Example app listening at http://%s:%s', host, port);
});

app.get('/auth/facebook', passport.authenticate('facebook'));

app.get('/auth/facebook/callback', passport.authenticate('facebook', {
    failureRedirect: '/login.html'
}), function(req, res) {
    console.log('req.session.returnTo: ' + req.session.returnTo);
    res.redirect(req.session.returnTo || '/');
});

app.get('/api/userdata', exports.isAuthenticated, function(req, res) {
    console.log('in userdata');
    res.json({
        user: req.user
    });
});

app.get('/logout', exports.isAuthenticated, function(req, res) {
    req.logout();
    res.redirect('logout.html');
});
