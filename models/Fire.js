const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const fireSchema = new Schema(
  {
    status: {
      type: Number,
      required: true,
    },
    occured_at: {
      type: Date,
      required: true,
    },
    riskStatus: {
      type: String,
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
    city: {
      type: String,
      default: "",
    },
    town: {
      type: String,
      default: "",
    },
    village: {
      type: String,
      default: "",
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Fire", fireSchema);
