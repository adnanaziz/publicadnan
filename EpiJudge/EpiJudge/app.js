/**
 * Module dependencies.
 */
var express = require('express');
//var cookieParser = require('cookie-parser');
//var compress = require('compression');
var favicon = require('serve-favicon');
//var session = require('express-session');
//var bodyParser = require('body-parser');
//var logger = require('morgan');
var errorHandler = require('errorhandler');
//var lusca = require('lusca');
//var methodOverride = require('method-override');
//var multer = require('multer');

//var _ = require('lodash');
//var MongoStore = require('connect-mongo')(session);
var path = require('path');
//var mongoose = require('mongoose');
//var passport = require('passport');
//var expressValidator = require('express-validator');
//var connectAssets = require('connect-assets');
//var useragent = require('express-useragent');

/**
 * Controllers, i.e., route handlers
 */

// Utilities
// var coreutils = require('./utils/core');
//var discauth = require('./discutils/discauth');
// var dbutils = require('./utils/dbutils');

var winston = require('winston');
var print = winston.debug;
var str = function(a) {
    JSON.stringify(a, null, 4);
}

winston.level = 'debug';

// var config = require('./config/config.json');
// var secrets = require('./config/secrets.js');
var config = {};

// To get long stack traces
if (process.env.NODE_ENV !== 'production') {
    require('longjohn');
}


/**
 * Create Express server.
 */
var app = express();

/**
 * Connect to MongoDB.
 */
//mongoose.connect(secrets.db);
//mongoose.connection.on('error', function() {
//    console.error('MongoDB Connection Error. Please make sure that MongoDB is running.');
//});

/**
 * Express configuration.
 */
// app.set('port', process.env.DISC_PORT || config.development.express.port || 3000);
app.set('port', process.env.DISC_PORT || 3000);

var swig = require('swig');
app.engine('html', swig.renderFile);
app.set('view engine', 'html');
app.set('views', __dirname + '/views');

swig.setDefaults({
    // loader: swig.loaders.fs(path.join(__dirname, '/views')),
    cache: false
});

// Swig will cache templates for you, but you can disable
// that and use Express's caching instead, if you like:
app.set('view cache', false);

//app.use(compress());
//app.use(connectAssets({
//    paths: [path.join(__dirname, 'public/css'), path.join(__dirname, 'public/js')]
//}));

//app.use(logger('dev'));
app.use(favicon(path.join(__dirname, 'public/favicon.png')));

//app.use(bodyParser.json(//{
//    limit: '50mb'
//}));
//app.use(bodyParser.urlencoded({
//    limit: '50mb',
//    extended: true
//}));

//app.use(multer({
//    dest: path.join(__dirname, 'public/images')
//}));

//app.use(expressValidator());
//app.use(methodOverride());
//app.use(cookieParser());

/*
app.use(session({
    resave: true,
    saveUninitialized: true,
    secret: secrets.sessionSecret,
    store: new MongoStore({
        url: secrets.db,
        autoReconnect: true
    })
}));
*/

//app.use(passport.initialize());
//app.use(passport.session());
/*
app.use(lusca({
    csrf: false,
    xframe: 'SAMEORIGIN',
    xssProtection: true
}));
*/

app.use(function(req, res, next) {
    res.locals.user = req.user;
    next();
});

app.use(express.static(path.join(__dirname, 'public'), {
    maxAge: 31557600000
}));

var adminUserNames = {
    "Adnan Aziz": true,
    "Tsung-Hsien Lee": true
};


app.post('/api/tagcloud', function(req, res) {
    res.json({
        "LinkedList1": "http://localhost",
        "LinkedList2": "http://localhost",
        "LinkedList3": "http://localhost",
        "LinkedList4": "http://localhost",
        "LinkedList5": "http://localhost",
        "Array1": "http://localhost",
        "Array2": "http://localhost",
        "Array3": "http://localhost",
        "Array4": "http://localhost",
        "Array5": "http://localhost",
        "HashTable": "http://localhost"
    });
});

var mockPostDict = {};
mockPostDict.summary = "Reverse a singly linked list";
mockPostDict.description = "Take a singly linked list and reverse it in O(n) time.";
mockPostDict.skeleton = "class Solution {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}";
mockPostDict.hint = "Start form the end.";
mockPostDict.testcase = "[1,3,4,9]";
mockPostDict.hashtags = "#List #Java #InPlace #ArrayList #HashTable #Medium #AdnansFavorites"

var servePost = function(req, res) {
    var postid = req.params.postid;
    res.render('templates/post.html', mockPostDict);
}

app.get('/post/:id', servePost);


app.post('/api/articlecloud', function(req, res) {
    res.json({
        "A Day To Remember": "http://localhost",
        "Why Java?": "http://localhost",
        "C++ vs. C": "http://localhost",
        "Quicken your coding": "http://localhost",
        "Knuth on Concurrency": "http://localhost"
    });
});

app.post('/api/trendingposts', function(req, res) {
    res.json({
        "Remove duplicates": "http://localhost",
        "Max-difference in an array": "http://localhost",
        "Shortest path in a maze": "http://localhost",
        "The third reader-writer locker problem ": "http://localhost"
    });
});

app.post('/api/search', function(req, res) {
    res.json({
        "Search Result 1": "http://localhost",
        "Search Result 2": "http://localhost",
        "Search Result 3": "http://localhost"
    });
});


/**
 * Error Handler.
 */
app.use(errorHandler());

// Handle 404
app.use(function(req, res) {
    res.status(404);
    res.render('templates/routingerror.html', {
        title: '404: Not Found',
        url: req.url,
        host: app.address().address,
        serverErrorMessage: "Cannot find specified URL."
    });
});

// Handle 500
app.use(function(error, req, res, next) {
    res.status(500);
    res.render('templates/routingerror.html', {
        title: '500: Internal Server Error',
        serverErrorMessage: error
    });
});


/**
 * Start Express server.
 */
app.listen(app.get('port'), function() {
    console.log('Express server listening on port %d in %s mode', app.get('port'), app.get('env'));
});

module.exports = app;
