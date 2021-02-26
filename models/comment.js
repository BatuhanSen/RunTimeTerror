const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const commentSchema = new Schema(
  {
    content: {
      type: String,
      required: true,
    },
    imageUrl: {
      type: String,
    },
    post: {
      type: Schema.Types.ObjectId,
      ref: "Post",
      required: true,
    },
    author: {
      type: Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
    metadata: {
      likes: {
        type: Number,
      },
      dislikes: {
        type: Number,
      },
    },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Comment", commentSchema);
