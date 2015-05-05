// Q1: are Approach 1 and Approach equivalent, for general
// computations, e.g., instead of x + 1, we have a complex
// computation that takes a long time?

/// Approach 1
var foo1 = function(x) {
    return x + 1;
}

var bar1 = foo1(2);
console.log(bar1);

var logger = function(arg) {
    console.log(arg);
}

// Approach 2
var foo2 = function(x, callback) {
    var y = x + 1;
    callback(y);
}

foo2(2, logger)


// Q2: How to capture the result of aFunc and bFunc? I.e., why are 
// beta and betadash never defined?
var aFunc = function(p) {
    return setTimeout(
        function(u) {
            console.log("Starting computation");
            return u + 1;
        },
        3000);
}

var beta = aFunc(3);
console.log("beta = " + beta);

var betadash;
var bFunc = function(p) {
    return setTimeout(
        function(u) {
            console.log("Starting computation");
            betadash = u + 1; // refers to betadash from enclosing scope
        },
        3000);
}

aFunc(3);
console.log("betadash = " + betadash);

var cFunc = function(p) {
    setTimeout(
        function(u) {
            console.log("Wasted some time in cFunc");
            console.log("in cFunc beta = " + beta);
            console.log("in cFunc betadash = " + betadash);
        },
        6000);
}

cFunc();
console.log("now beta = " + beta);
console.log("now betadash = " + betadash);

// Q3: assigning with callback
var result;
var pFunc = function(arg, callback) {
    setTimeout(
        function(u) {
            console.log("Wasted some time in pFunc");
            result = 42;
        },
        3000);
}
pFunc();
console.log("After calling pFunc, result = " + result);

var qFunc = function() {
    setTimeout(
        function() {
            console.log("Wasted some time in qFunc");
            console.log("After time wasted in qFunc, result = " + result);
        },
        6000);
}

qFunc();

// Q4: filter example
var A = [1, 2, 3, 4, 5, 6, 7, 8, 9];

var isEven = function(n) {
    var result = 0;
    // slow down isEven:
    for (var i = 0; i < 100000000; i++) {
        result += i;
    }
    console.log("slowed down, result = " + result);
    return (n % 2) === 0;
}

console.log("A = " + A);
var B = A.filter(isEven);
// why is this correct? 
console.log("B = " + B);

var isEvenWithDelay = function(n) {
    var result = 0;
    setTimeout(
        function() {
            console.log("Wasted some time in isEvenWithDelay");
            return (n % 2) === 0;
        },
        200);
}

var C = A.filter(isEvenWithDelay);
// why is this not correct?
console.log("C = " + C);

// Q5: using async - is there anyway to "block" till async.filter completes?
var async = require('async');

var D;

var f1 = function(results) {
    console.log("async filter callback, results = " + results);
    D = results;
}

var isEvenWithDelayAndCallback = function(n, callback) {
    var result = 0;
    setTimeout(
        function() {
            console.log("Wasted some time in isEvenWithDelayAndCallback");
            callback((n % 2) === 0);
        },
        200);
}

async.filter(A, isEvenWithDelayAndCallback, f1);
console.log("immediately after async.filter, D = " + D);

setTimeout(
    function() {
        console.log("Wasted some time before querying D again");
        console.log("D after timeout = " + D);
    }, 10000);

// Q6: using async to filter A twice, once by divisble by 2, then divisible by 3
// Please check if my final approach is correct.
var async = require('async');

var isDiv3WithDelayAndCallback = function(n, callback) {
    var result = 0;
    setTimeout(
        function() {
            console.log("Wasted some time in isDiv3WithDelayAndCallback");
            callback((n % 3) === 0);
        },
        200);
}

var E;

setTimeout(
    function() {
        console.log("Wasted some time before querying E ");
        console.log("E after timeout = " + E);
    }, 10000);

var w1 = function(callback) {
    console.log("in w1");
    async.filter(A, isEvenWithDelayAndCallback,
        function(intermediate) {
            console.log("w1 intermediate = " + intermediate);
            callback(null, intermediate);
        });
}

var w2 = function(arg, callback) {
    console.log("in w2");
    console.log("arg = " + arg);
    async.filter(arg, isDiv3WithDelayAndCallback,
        function(intermediate) {
            console.log("w2 intermediate = " + intermediate);
            E = intermediate;
            callback(null, intermediate);
        });
}

// how to pass argument to w1?
async.waterfall([
        w1,
        w2
    ],
    function(err, result) {
        console.log("waterfall result = " + result);
    }
);

setTimeout(
    function() {
        console.log("Wasted some time before querying E again");
        console.log("E after timeout = " + E);
    }, 10000);
