const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const earthquakeSchema = new Schema(
  {
    magnitude: {
      type: Number,
      required: true,
    },
    depth: {
      type: Number,
      required: true,
    },
    latitude: {
      type: Number,
      required: true,
    },
    longitude: {
      type: Number,
      required: true,
    },
    location: {
      type: String,
      required: true,
    },
    occured_at: {
      type: Date,
      required: true,
    },
    updated_at: {
      type: Date,
      required: true,
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Earthquake", earthquakeSchema);
