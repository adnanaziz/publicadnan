(function(exps) {

    function namedFunction() {
        console.log("namedFunction() called");
    }

    var callExportedRoutine = function() {
        return exps.sayHelloEnglish() + " " + exps.sayHelloSpanish();
    }

    exps.sayHelloEnglish = function() {
        console.log("exps.sayHelloSpanish() = " + exps.sayHelloSpanish());
        return "Hello";
    }

    exps.sayHelloSpanish = function() {
        return "Hola";
    }

    exps.sayBoth = function() {
        namedFunction();
        return callExportedRoutine();
    }

    'use strict';
    console.log("in wierd export block");
    var wierdExport = function() {
        console.log("in wierdExport()");
        return "wierd return string";
    };
    exps.wierdExport = wierdExport;
})(typeof window === 'undefined' ? module.exports : window);
