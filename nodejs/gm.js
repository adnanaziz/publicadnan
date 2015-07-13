var gm = require('gm');

// internet-1.jpg     iphone6plus_1.jpg  iphone6plus_2.jpg  rc-1.jpg

var img0 = "internet-1.jpg";
var img1 = "iphone6plus_1.jpg"
var img2 = "iphone6plus_2.jpg";
var img3 = "rc-1.jpg";

gm(img0)
.composite(img1)
//.composite(img2)
//.composite(img3)
.geometry('+100+150')
.write('composite.png', function(err) {
    if(!err) console.log("Written composite image.");
    else console.log(err);
});

gm(img0)
.montage(img1)
.montage(img2)
.montage(img3)
.geometry('+100+150')
.write('montage.png', function(err) {
    if(!err) console.log("Written montage image.");
});
