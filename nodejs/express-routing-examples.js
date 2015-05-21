var express = require('express');
var session = require('express-session');
var cookieParser = require('cookie-parser');
var flash = require('connect-flash');

var app = express();

app.use(cookieParser('secret'));
app.use(session({
    cookie: {
        maxAge: 60000
    }
}));
app.use(flash());

app.get('/', function(req, res) {
    res.send('Hello World, redux!');
});

app.get('/t1', function(req, res, next) {
        console.log("entered t1 route");
        next();
    },
    function(req, res) {
        res.json({
            result: "it worked"
        });
    });

app.get('/uglyurl', function(req, res) {
    req.flash("aField", "it worked!");
    res.redirect('/niceurl');
});

app.get('/niceurl', function(req, res) {
    res.send('Hello World, from niceurl!' + JSON.stringify(req.flash("aField")));
});

// nice way to debug routes:
// http://forbeslindesay.github.io/express-route-tester/
app.get('/:id(.?123|[a-z]*456)', function(req, res) {
    console.log('req.params', JSON.stringify(req.params, null, 4));
    res.send('AAA');
});

app.get('/:id(\\d\\d\\d)', function(req, res) {
    console.log('req.params', JSON.stringify(req.params, null, 4));
    res.send('CCC');
});

app.get('/:id(\\d+)', function(req, res) {
    console.log('req.params', JSON.stringify(req.params, null, 4));
    res.send('BBB');
});


app.get('/:type(old|new)/:id', function(req, res) {
    console.log('type, id, req.params', req.params.type, req.params.id, JSON.stringify(req.query, null, 4));
    res.send('1: type, id, req.params' + req.params.type + req.params.id + JSON.stringify(req.params, null, 4));
});

app.get('/:type(old|NEWER)/:id', function(req, res) {
    console.log('type, id, req.params', req.params.type, req.params.id, JSON.stringify(req.query, null, 4));
    res.send('2: type, id, req.params' + req.params.type + req.params.id + JSON.stringify(req.params, null, 4));
});

var server = app.listen(3000, function() {

    var host = server.address().address;
    var port = server.address().port;

    console.log('Example app listening at http://%s:%s', host, port);
});
