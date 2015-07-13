'use strict';

// load dependencies
var mongoose = require('mongoose');
var autoIncrement = require('mongoose-auto-increment');

var CountCheckerSchema = new mongoose.Schema({

    myid: {
        type: Number
    }

});

CountCheckerSchema.plugin(autoIncrement.plugin, { model: 'CountChecker', field: 'autoId', unique: false });

module.exports.model = mongoose.model('CountChecker', CountCheckerSchema);
