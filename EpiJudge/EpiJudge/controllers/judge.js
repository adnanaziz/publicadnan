// empty

// var coreutils = require('../utils/core');

var mockPostDict = {};
mockPostDict.summary = "Reverse a singly linked list";
mockPostDict.description = "Take a singly linked list and reverse it in O(n) time.";
mockPostDict.skeleton = "public class Foo {\n\tpublic static void main(String[] args) {\n\t\tSystem.out.println(\"Hello World!\");\n\t}\n}";
mockPostDict.hint = "Start form the end.";
mockPostDict.testcase = "[1,3,4,9]";
mockPostDict.hashtags = "#List #Java #InPlace #ArrayList #HashTable #Medium #AdnansFavorites"

module.exports.servePost = function(req, res) {
    var postid = req.params.postid;
    res.render('templates/post.html', mockPostDict);
}
