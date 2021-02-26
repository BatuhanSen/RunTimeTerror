const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const locationSchema = new Schema({
  city: {
    type: String,
  },
  town: {
    type: String,
  },
  village: {
    type: String,
  },
  latitude: {
    type: Number,
    required: true,
  },
  longitude: {
    type: Number,
    required: true,
  },
});

module.exports = mongoose.model("Location", locationSchema);
