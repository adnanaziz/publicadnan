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

var print = console.log;

module.exports.postToDict = postToDict;

var postToDict;

var POST_SUFFIX = ".post";
var endsWith = function(str, suffix) {
    if (suffix.length > str.length) {
        return false;
    } else {
        return str.substring(str.length - POST_SUFFIX.length, str.length) === POST_SUFFIX;
    }
}

function parseFile(filename, callback) {
    var fileContent;
    console.log("filename = " + filename);
    try {
        fileContent = fs.readFileSync('posts/' + filename, 'utf8');
    } catch (readFileSyncError) {
        return callback("error reading file: " + readFileSyncError);
    }
    //console.log("fileContent = " + fileContent);

    var lines = fileContent.split("\n");

    var result = {};
    for (var i = 0; i < lines.length; i++) {
        if (lines[i].match(/^\s*public class/)) {
            var idx = lines[i].indexOf("public");
            lines[i] = lines[i].substring(idx + "public".length);
        } 
        if (lines[i].match(/^\s*package com.epi/)) {
            lines[i] = "// " + lines[i];
        } 
        if (lines[i].match(/^\s*import /)) {
            lines[i] = "// " + lines[i];
        } 
        var currentField;
        if (!lines[i].match(/^\s*@Override/) && lines[i].match(/^\s*@/)) {
            console.log("matched line " + lines[i] + " " + i);
            currentField = lines[i].trim().substring(1); // drop leading @
            result[currentField] = "";
            i++;
            while (!(lines[i].match(/^\s*@/)) && !(lines[i].match(/^\s*\*\//))) {
                result[currentField] += lines[i];
                // console.log("lines[i] = " + lines[i]);
                i++;
                print(lines[i]);
            }
            i--; // back up one line
        }
    }
    
    // now get the skeleton
    var skeleton = "";
    var begin = -1;
    var end;
    var skeletonWrite = false;
    for (var i = 0; i < lines.length; i++) {
        var line = lines[i];
        if (line.match(/\/\/\ @judge-include-display/)) {
            if (!skeletonWrite && begin == -1) {
                begin = i;
            }
            skeletonWrite = true;
        }
        if (line.match(/\/\/\ @judge-exclude-display/)) {
            skeletonWrite = false;
            end = i;
        }
        if (skeletonWrite && !line.match(/\/\/\ @judge-include-display/)) {
            if (i > 0 && line.match(/^\s*}\s*$/)) { 
                // stuff in a blank line
                skeleton += "\n" + line + "\n";
            } else {
                skeleton += line + "\n";
            }
        }
    }
    //TODO(AA): why is this import here when we add again?
    skeleton =  "import java.util.*;\nimport java.math.BigInteger;n\nclass Solution {\n\n" + skeleton;
    skeleton = skeleton + "\n}";
    print("---skeleton = " + skeleton);
    result.javaskeleton = skeleton;

/*
    if "// @judge-include-display" in line:
        if not skeletonWrite and begin == -1:
            begin = lineNum
        skeletonWrite = True
    if "// @judge-exclude-display" in line:
        skeletonWrite = False
        end = lineNum
    if skeletonWrite and not "// @judge-include-display" in line:
        skeleton.write("%s\n" % line)
    lineNum = lineNum + 1
*/

    // now create the test cases
    var testcase = "";
    console.log("begin, end = " + begin + " " + end);
    result.begin = begin;
    for (var i = 0; i < begin; i++) {
        testcase += lines[i] + "\n";
    }
    testcase += "// @judge-fill\n";   
    for (var i = end+1; i < lines.length; i++) {
        testcase += lines[i] + "\n";
    }
    
    var prototypes = ["LinkedListPrototypeTemplate.java", "BinaryTreePrototypeTemplate.java", "BinarySearchTreePrototypeTemplate.java"]; 
    for (var i = 0; i < prototypes.length; i++) {
        try {
            fileContent = fs.readFileSync('posts/' + prototypes[i], 'utf8');

            var tmp1 = fileContent.replace(/\s*import/g, "// import");
            var tmp2 = tmp1.replace(/package com.epi;/g, "// package com.epi;\n");

            if (tmp2.match(/\npublic\ class\ BinaryTreePrototypeTemplate\ {/)) {
                print("matched special static class");
                //tmp2 = tmp2.replace("public class BinaryTreePrototypeTemplate {", "// public class BinaryTreePrototypeTemplate {");
                tmp2 = tmp2.replace("public class BinaryTreePrototypeTemplate {", "\n// public class BinaryTreePrototypeTemplate {");
                tmp2 = tmp2.replace("\n}", "\n//}");
                tmp2 = tmp2.replace("public static class", "class");
            }  else if (tmp2.match(/\npublic\ class\ BinarySearchTreePrototypeTemplate\ {/)) {
                print("matched special static class");
                //tmp2 = tmp2.replace("public class BinaryTreePrototypeTemplate {", "// public class BinaryTreePrototypeTemplate {");
                tmp2 = tmp2.replace("public class BinarySearchTreePrototypeTemplate {", "\n// public class BinaryTreePrototypeTemplate {");
                tmp2 = tmp2.replace("\n}", "\n//}");
                tmp2 = tmp2.replace("public static class", "class");
            }  

            testcase = testcase + "\n\n" + tmp2;
        } catch (readFileSyncError) {
            return callback("error reading file: " + readFileSyncError);
        }
    }

    result.javatestcase = "import java.util.*;\nimport java.math.BigInteger;\n\n" + testcase;
    console.log(">>> result = " + JSON.stringify(result, null, 4));
    callback(null, result);

/*
    for i in range(0,begin):
        test.write("%s\n" % lines[i])
    
    test.write("// @judge-fill")
    
    for i in range(end+1,len(lines)):
        test.write("%s\n" % lines[i])
*/

};

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
                    if (!endsWith(list[i],POST_SUFFIX)) {
                        continue;
                    }
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
                var postKey = list[i].substring(0,list[i].length - POST_SUFFIX.length);
                postToDict[postKey] = dict;
            }
            console.log("postToDict is now " + JSON.stringify(postToDict));
            createSearchIndex(callback);
        });
}

var si = require('search-index')({});

function createSearchIndex(callback) {
    //var data = postToDict;
    var data = [];
    console.log("Creating search index...");
    for (var key in postToDict) {
        var newPostDict = {};
        console.log("postToDict[key] = " + JSON.stringify(postToDict[key]));
        for (var key2 in postToDict[key]) {
            console.log("key2 = " + key2);
            newPostDict[key2] = postToDict[key][key2];
        }
        newPostDict["postname"] = key;
        console.log("newPostDict = " + JSON.stringify(newPostDict));
        data.push(newPostDict);
    }

    si.add(data, {}, function(err) {
        if (err) {
            console.log('oops! ' + err);
        } else {
            console.log("Success search index...");
            console.log("data = " + JSON.stringify(data, null, 4));
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

var slugToFileName = {
    "parity":"Parity1.java",
    "dnf":"DutchNationalFlag.java",
    "integer-string-conversion": "InterconvertingStringInteger.java",
    "base-conversion": "ConvertBase.java",
    "list-cycle": "CheckingCycle.java",
    "stack-with-max": "StackWithMax.java",
    "binary-tree-level-order": "BinaryTreeLevelOrder.java",
    "is-binary-tree-balanced": "BalancedBinaryTree.java",
    "merge-sorted-files": "MergeSortedArrays.java",
    "first-occurence-of-k": "BinarySearchFirstK.java",
    "anonymous-letter": "AnonymousLetter.java",
    "intersect-two-sorted-arrays": "IntersectSortedArrays3.java",
    "render-a-calendar": "RenderingCalendar.java",
    "is-binary-tree-a-bst": "IsBinaryTreeABST.java",
    "powerset": "PowerSet.java", 
    "number-of-ways": "NumberWays.java", 
    "3sum": "ThreeSum.java",
    "paint-matrix": "PaintingIterative.java",
    "gcd": "GCD1.java",
    "biggest-product-n-minus-1-entries": "BiggestProductNMinus1.java"

};

module.exports.servePost = function(req, res) {
    var postid = req.params.id;
    print("postid = " + postid);
    print("filename = " + slugToFileName[postid]);
    parseFile(slugToFileName[postid], function(err, postDict) {
        if (err) {
            console.log("Error " + err);
            res.render('templates/routingerror.html', {serverError:500, serverErrorMessage:err});
        } else {
            //problemFileToDict(postid, function(err, postDict) {
                res.render('templates/post.html', postDict);
            //});
        }
    });
};

module.exports.search = function(req, res) {
    var q = {};
    //console.log("searchTerm = " + req.params.searchTerm);
    //if (req.params.searchTerm !== undefined) {
    //q.query = {
    //    '*': [req.params.searchTerm]
    //};
    var postedQuery = req.body
    if (req.body === undefined || req.body.searchTerm === undefined) {
        console.log("req.body = " + JSON.stringify(req.body));
        return res.json({code:500, message:"Missing body or searchTerm"});
    }
    console.log("searchTerm = " + req.body.searchTerm);
    q.query = { '*': [req.body.searchTerm] };
    si.search(q, function(err, searchResults) {
        console.log("in search callback");
        if (err) {
            return res.json({
                code: 500,
                "message": err
            });
        }
        var result = [];
        for (var i = 0; i < searchResults.hits.length; i++) {
            result.push({postname:searchResults.hits[i].document.postname,
                        summary:searchResults.hits[i].document.summary});
        }
        console.log("search results = " + JSON.stringify(result, null, 4));
        res.json({
            code: 200,
            result: result
        });
    });
}

module.exports.autocomplete = function(req, res) {
    var q = {};
    console.log("autocompleteTerm = " + req.query.term);
    var options = {
        beginsWith: req.query.term, // The beginning of the text to match
        field: "*", // The field to use (defaults to all fields)
        threshold: 1, // The amount of characters to ignore before returning matches (default: 3)
        limit: 10, // The maximum amount of suggestions
        type: 'ID' // The type of autosuggest returned- can be `simple`, `ID` or `count`
    }
    si.match(options, function(err, matches) {
        //matches is an array of all the terms that match the input.
        if (err) {
            return res.json({code:500, message:err});
        }
        console.log("matches = " + matches);
        var result = [];
        for (var i = 0 ; i < matches.length; i++) {
            result.push(matches[i][0]);
        }
        res.json(result);
    })
};
