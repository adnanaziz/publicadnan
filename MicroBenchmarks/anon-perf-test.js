var async = require('async');
var N = 25000;

var tester = function(x) {
    var result = 0;
    for (var j = 0; j < N; j++) {
        result = (result + j + x) % N;
    }
    return result;
}


var final_result = 0;
var K = 3;
console.log("start named function code" + new Date());
for (var i = 0; i < N; i++) {
    final_result = tester(K);
}

console.log("start anonymous function code" + new Date());
final_result = 0;
for (var i = 0; i < N; i++) {
    (function() {
        for (var j = 0; j < N; j++) {
            final_result = (final_result + j + K) % N;
        }
    })();
}

console.log("start async + anonymous function code" + new Date());
final_result = 0;
async.waterfall([
    function(callback) {
        for (var i = 0; i < N; i++) {
            async.waterfall([
                function(callback) {
                    for (var j = 0; j < N; j++) {
                        final_result = (final_result + j + K) % N;
                    }
                }
            ]);
        }
        callback("done" + new Date());
    }
    //TODO: get this call right, it's being called early
], console.log);
