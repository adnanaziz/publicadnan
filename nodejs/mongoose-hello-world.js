var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/disc', function(error) {
    if (error) {
        console.log(error);
    } else {
        console.log("Connection success");
    }
});

//var Flower = require('./Flower.js').model;

// retrieve connection instance
mongoose.connection.once('open', function() {
    // set up counters
    mongoose.connection.collection('buds').count(function(err, count) {
        if (err) {
            logger.error(err);
            return;
        }

        var addFlower = function(flowerName, callback) {
            console.log("adding flower with name", flowerName);
            mongoose.connection.collection('buds').insert({
                _id: flowerName,
                seq: -1,
            }, function(err, result) {
                if (err) {
                    console.log(err);
                    callback(err);
                } else {
                    callback(null);
                }
            });
        }

        if (count == 0) {
            console.log("creating buds");
            addFlower("roses", function() {
                console.log("done");
            });
            //async.series([
            //    async.apply(addCounter, "roses"),
            //    async.apply(addCounter, "tulips")
            //]);
        }
    });
});
