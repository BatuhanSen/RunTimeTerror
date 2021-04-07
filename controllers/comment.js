const Comment = require("../models/comment");
const comment = require("../models/comment");
const User = require("../models/user");

exports.getComments = (req, res, next) => {
  const postId = req.body.postId;

  Comment.find({ postId: postId })
    .then((comments) => {
      if (comments.length == 0) {
        const error = new Error("Could not find any comments");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "comments fetched successfully",
        data: {
          comments: comments,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getComment = (req, res, next) => {
  const commentId = req.params.commentId;

  Comment.findById(commentId)
    .then((comment) => {
      if (!comment) {
        const error = new Error("Could not find comment the comment");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "comment fetched successfully",
        data: {
          comment: comment,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addComment = (req, res, next) => {
  const content = req.body.content;
  const imageUrl = req.body.imageUrl;
  const postId = req.body.postId;
  let author;
  let post;

  const comment = new Comment({
    content: content,
    imageUrl: imageUrl,
    post: postId,
    author: req.body.userId,
  });

  Comment.save()
    .then((result) => {
      return Post.findById(postId);
    })
    .then((p) => {
      post = p;
      p.comments.push(comment);
      return p.save();
    })
    .then((result) => {
      return User.findById(req.body.userId);
    })
    .then((user) => {
      author = user;
      user.comments.push(comment);
      return user.save();
    })
    .then((result) => {
      res.status(201).json({
        message: "comment created successfully",
        data: {
          comment: comment,
          post: post,
          author: {
            _id: author._id,
            username: author.username,
          },
        },
      });
    })
    .catch((err) => next(err));
};

exports.updateComment = (req, res, next) => {
  const commentId = req.body.commentId;

  const content = req.body.content;
  const imageUrl = req.body.imageUrl;

  Comment.findById(commentId)
    .then((comment) => {
      if (!comment) {
        const error = new Error("Could not find the comment");
        error.statusCode = 404;
        throw error;
      }
      comment.content = content || comment.content;
      comment.imageUrl = imageUrl || comment.imageUrl;

      return comment.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "The comment updated successfully",
        data: {
          comment: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.deleteComment = (req, res, next) => {
  const commentId = req.body.commentId;
  const postId = req.body.postId;
  const userId = req.body.userId;

  comment
    .findById(commentId)
    .then((comment) => {
      if (!comment) {
        const error = new Error("Could not find the comment");
        error.statusCode = 404;
        throw error;
      }
      return comment.findByIdAndRemove(commentId);
    })
    .then((result) => {
      return Post.findById(postId);
    })
    .then((post) => {
      post.comments.pull(commentId);
      return post.save();
    })
    .then((result) => {
      return User.findById(userId);
    })
    .then((user) => {
      user.comments.pull(commentId);
      return user.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "The comment deleted successfully",
      });
    })

    .catch((err) => next(err));
};
