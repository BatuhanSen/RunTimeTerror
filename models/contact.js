const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const contactSchema = new Schema(
  {
    username: {
      type: String,
      required: true,
    },
    surname: {
      type: String,
    },
    email: {
      type: String,
      required: true,
    },
    phoneNumber: {
      type: Number,
    },
    message: {
      type: String,
      required: true,
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Contact", contactSchema);
