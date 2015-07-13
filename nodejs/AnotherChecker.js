'use strict';

// load dependencies
var mongoose = require('mongoose');
var autoIncrement = require('mongoose-auto-increment');

var AnotherCheckerSchema = new mongoose.Schema({

    myname: {
        type: String
    }

});

AnotherCheckerSchema.plugin(autoIncrement.plugin, { model: 'AnotherChecker', field: 'anotherAutoId' });

module.exports.model = mongoose.model('AnotherChecker', AnotherCheckerSchema);
