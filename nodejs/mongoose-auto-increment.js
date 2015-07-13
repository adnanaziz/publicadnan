var mongoose = require('mongoose');


mongoose.connect('mongodb://localhost/test', function(error) {
    if (error) {
        console.log(error);
    } else {
        console.log("Connection success");
        // mongoose.connection.db.dropCollection('countcheckers', function(err, result) {console.log});
    }
});

autoIncrement = require('mongoose-auto-increment');
autoIncrement.initialize(mongoose.connection);


var CountChecker = require('./CountChecker.js').model;
var AnotherChecker = require('./AnotherChecker.js').model;

for (var i = 0; i < 100; i++) {
    var obj = new CountChecker({
        myid: i
    });
    if (i == 0) {
        obj.resetCount(function(err, nextCount) {
            console.log(err);
            console.log(nextCount);
        });
    }
    obj.save(console.log);
}

//process.exit(code=0);
