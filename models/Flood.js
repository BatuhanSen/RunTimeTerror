const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const floodSchema = new Schema(
  {
    quantity: {
      type: Number,
      required: true,
    },
    intensity: {
      type: Number,
      required: true,
    },
    location: {
      type: Schema.Types.ObjectId,
      ref: "Location",
      required: true,
    },
    duration: {
      type: Number,
      required: true,
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Flood", floodSchema);
