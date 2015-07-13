var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/disc', function(error) {
    if (error) {
        console.log(error);
    } else {
        console.log("Connection success");
    }
});

var Flower = require('./Flower.js').model;

var f = new Flower({
    name: "ann"
});

f.save(function(err) {
    if (err) {
        console.log(err);
    }

    Flower.findOne({
            name: "ann"
        },
        function(err, flower) {
            if (err) {
                console.log("Error " + err);
            } else {
                console.log("Flower " + flower);
                console.log("missing " + flower.missing);
            }
        }
    );

});

console.log("undefined == null", undefined == null);
console.log("undefined === null", undefined === null);
