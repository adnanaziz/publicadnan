var express = require('express');
var app = express();

var PORT_NUMBER = 3000;

// Tell server to serve up static content from the directory ./public. 
// use for images, html, css, javascript, etc.
app.use(express.static('public'));

// To process POST data, we need a body-parser

// https://www.npmjs.com/package/body-parser
var bodyParser = require('body-parser');

// tell server to use bodyParser to parse application/json
app.use(bodyParser.json())

app.get('/', function(req, res) {
    res.send('<h1>Hello World!</h1>Poor man\'s HTML. <p> An image served from local directory.<p> <img src="f1.png" alt="f1"></img>' +
               '<p> An image with absolute URL path.<p>' +
               '<img src="http://localhost:' + PORT_NUMBER + '/' + 'f2.png" alt="f2"></img>'
               );
});

app.get('/api/ping', function(req, res) {
    res.json({
        code: 200,
        msg: "Pong"
    });
});

app.get('/api/mock', function(req, res) {
    res.json({
        code: 200,
        result: [{
            name: "Andy",
            age: 12
        }, {
            name: "Bob",
            age: 14
        }, {
            name: "Charlie",
            age: 16
        }, ]
    });
});

// note that we need the ./ for path to work, also file suffix must be .json (not .js)
var jsonFromFile = require('./sample.json');
var aService = function(req, res) {
    res.json(jsonFromFile);
};

app.get('/api/mockServeFromFile', aService);
app.post('/api/mockServeFromFile', aService);

// listen for GET requests of the form api/place/1234, api/place/87432. 
// 1234, 87432 are encoded as Id
app.get('/api/place/:anId(\\d+)', function(req, res) {
    res.json({
        msg: "Your route id was " + req.params.anId
    });
});

var loremIpsum = require('lorem-ipsum');

var makePost = function(length, dictionary) {
    var title = loremIpsum();
    var args = {
        count: length, // Number of words, sentences, or paragraphs to generate.
        units: 'paragraphs', // Generate words, sentences, or paragraphs.
        sentenceLowerBound: 5, // Minimum words per sentence.
        sentenceUpperBound: 15, // Maximum words per sentence.
        paragraphLowerBound: 3, // Minimum sentences per paragraph.
        paragraphUpperBound: 7, // Maximum sentences per paragraph.
        format: 'plain', // Plain text or html
        // words: ['ad', 'dolor', ...], // Custom word dictionary. Uses dictionary.words (in lib/dictionary.js) by default.
        random: Math.random // A PRNG function. Uses Math.random by default
    };
    if (dictionary !== undefined) {
        args.words = dictionary;
    }

    var body = loremIpsum(args);
    return ({
        postTitle: title,
        postBody: body
    });
};

var loremRoute = '/api/lorem/:textLength(\\d+)';
// shows how we access JSON object sent to server.
// req.body contains the JSON object.
// body-parser needs to be enabled, see earlier remarks about it.
app.post(loremRoute, function(req, res) {

    var aPost = makePost(req.params.textLength, req.body.dictionary);
    res.json({
        post: aPost
    });
});


var server = app.listen(PORT_NUMBER, function() {

    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});
