'use strict';

// load dependencies
var mongoose = require('mongoose');

var FlowerSchema = new mongoose.Schema({

    name: {
        type: String
    },

    missing: {
        type: String
    }

});

module.exports.model = mongoose.model('Flower', FlowerSchema);
