'use strict';

// load dependencies
var mongoose = require('mongoose');

var FlowerSchema = new mongoose.Schema({

    name: {
        type: String
    },

    identifier: {
        type: Number
    }

});

module.exports.model = mongoose.model('Flower', FlowerSchema);
