var opts = {
    "adnan": "aziz",
    "bob": "bitter"
};

var foo = {};
for ( var key in opts) {
    module.exports[key] = opts[key];
}
console.log(module.exports);
