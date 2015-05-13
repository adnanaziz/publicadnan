(function(exps) {
   'use strict';
    console.log("in wierd export block");
    var wierdExport = function() {
        console.log("in wierdExport()");
    };
    exps.wierdExport = wierdExport;
})(module.exports);
