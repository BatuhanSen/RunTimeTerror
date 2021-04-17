const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const emergencySchema = new Schema(
  {
    personCount: {
      type: Number,
      default: 1,
    },
    message: {
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
    address: {
      type: String,
      required: true,
    },
    user: {
      type: Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Emergency", emergencySchema);
