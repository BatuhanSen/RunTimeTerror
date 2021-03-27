const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const fireSchema = new Schema(
  {
    status: {
      type: Number,
      required: true,
    },
    duration: {
      type: Number,
      required: true,
    },
    location: {
      type: Schema.Types.ObjectId,
      ref: "Location",
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Fire", fireSchema);
