var async = require('async');
var testFile1 = 'md5_1';
var testFile2 = 'iphone6plus_1.jpg';
var testFile3 = 'iphone6plus_2.jpg';
var testFiles = ['internet-1.jpg', testFile3, 'rc-1.jpg', 'IMG_3732.JPG'];

var exif = require('exif');
var ExifImage = require('exif').ExifImage;

var exifParser = require('exif-parser');
var fs = require('fs');

var testExif = function(fileName, callback) {
    try {
        new ExifImage({
            image: fileName
        }, function(err, exifData) {
            if (err) {
                console.log("testExif::" + err);
                return callback(err);
            }
            console.log("---\n1. exif:" + fileName + " " + JSON.stringify(exifData, null, 4));
        });
    } catch (error) {
        return callback(error);
    }
};

var testExifParser = function(fileName, callback) {
    fs.readFile(fileName, function(err, buffer) {
        if (err) {
            console.log("testExifParser::" + err);
            return callback(err);
        }
        var parser = exifParser.create(buffer);
        var result = parser.parse();
        console.log("---\n2. exif-parser:" + fileName + " " + JSON.stringify(result, null, 4));
    });
}

async.each(testFiles,
    function(filename, callback) {
        testExif(filename, callback);
        testExifParser(filename, callback);
        callback(null);
    },
    function(err) {
        if (err) {
            console.log("Error: " + err);
        }
    });

/*
fs.open(testFile1, 'r', function(status, fd) {
    if (status) {
        console.log(status.message);
        return;
    }
    var buffer = new Buffer(65536);
    fs.read(fd, buffer, 0, 65536, 0, function(err, num) {
        // console.log(buffer.toString('utf-8', 0, num));
    });
    var parser = exifParser.create(buffer);
    var result = parser.parse();
    console.log(result);
});
*/
