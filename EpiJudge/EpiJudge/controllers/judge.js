// empty

// var coreutils = require('../utils/core');

var mockPostDict = {};
mockPostDict.summary = "Reverse a singly linked list";
mockPostDict.description = "Take a singly linked list and reverse it in O(n) time.";
mockPostDict.skeleton = "class Foo {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}";
mockPostDict.hint = "Start form the end.";
mockPostDict.testcase = "[1,3,4,9]";
mockPostDict.hashtags = "#List #Java #InPlace #ArrayList #HashTable #Medium #AdnansFavorites"


var fs = require('fs')

var problemFileToDict = function(filename, callback) {
    fs.readFile('posts/' + filename, 'utf8', function(err, data) {
        if (err) {
            return callback(err);
        }
        var fields = data.split("\n--");
        var dict = {};
        console.log("fields = " + fields);
        for (var i = 0; i < fields.length; i++) {
            console.log("fields[i] = " + fields[i]);
            var lines = fields[i].trim().split("\n");
            console.log("lines = " + lines);
            var key = lines[0];
            var value = lines.slice(1).join("\n");
            dict[key] = value;
        }
        console.log("dict = " + JSON.stringify(dict, null, 4));
        callback(null, dict);
    });
};

module.exports.servePost = function(req, res) {
    var postid = req.params.id;
    console.log("in judge controller servepost, postid = " + postid);
    //res.render('templates/post.html', mockPostDict);
    var dict = problemFileToDict(postid,
        function(err, postDict) {
            if (err) {
                return res.json({
                    code: 500,
                    "message": err
                });
            }
            res.render('templates/post.html', postDict);
            //res.render('templates/post.html', mockPostDict);
        });
};
