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

var postToDict;

function initPostToDict(callback) {
    postToDict = {};
    fs.readdir('./posts',
        function(err, list) {
            if (err) {
                return callback(err);
            }
            for (var i = 0; i < list.length; i++) {
                var data;
                try {
                    data = fs.readFileSync('posts/' + list[i], 'utf8');
                } catch (readFileSyncError) {
                    console.log("error reading file: " + readFileSyncError);
                    continue;
                }
                var fields = data.split("\n--");
                var dict = {};
                for (var j = 0; j < fields.length; j++) {
                    var lines = fields[j].trim().split("\n");
                    var key = lines[0];
                    var value = lines.slice(1).join("\n");
                    dict[key] = value;
                }
                postToDict[list[i]] = dict;
            }
            callback(null);
        });
}

var problemFileToDict = function(filename, callback) {
    if (postToDict === undefined) {
        return initPostToDict(callback);
    } else {
        callback(null, postToDict[filename]);
    }
};

module.exports.servePost = function(req, res) {
    var postid = req.params.id;
    console.log("in judge controller servepost, postid = " + postid);
    problemFileToDict(postid, function(err, postdict) {
        res.render('templates/post.html', postdict);
    });
};
