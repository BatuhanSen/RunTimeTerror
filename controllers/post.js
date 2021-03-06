const Post = require("../models/post");

exports.getPosts = (req, res, next) => {
  Post.find()
    .then((posts) => {
      if (posts.length == 0) {
        const error = new Error("Could not find any posts");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Posts fetched successfully",
        data: {
          posts: posts,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getPost = (req, res, next) => {
  const postId = req.params.postId;

  Post.findById(postId)
    .then((post) => {
      if (!post) {
        const error = new Error("Could not find post the post");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Post fetched successfully",
        data: {
          post: post,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addPost = (req, res, next) => {
  const title = req.body.title;
  const content = req.body.content;
  const imageUrl = req.body.imageUrl;

  const post = new Post({
    title: title,
    content: content,
    imageUrl: imageUrl,
  });

  post
    .save()
    .then((result) => {
      res.status(201).json({
        message: "Post created successfully",
        data: {
          post: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.updatePost = (req, res, next) => {
  const postId = req.body.postId;

  const title = req.body.title;
  const content = req.body.content;
  const imageUrl = req.body.imageUrl;

  Post.findById(postId)
    .then((post) => {
      if (!post) {
        const error = new Error("Could not find the post");
        error.statusCode = 404;
        throw error;
      }
      post.title = title || post.title;
      post.content = content || post.content;
      post.imageUrl = imageUrl || post.imageUrl;

      return post.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "The post updated successfully",
        data: {
          post: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.deletePost = (req, res, next) => {
  const postId = req.body.postId;

  Post.findById(postId)
    .then((post) => {
      if (!post) {
        const error = new Error("Could not find the post");
        error.statusCode = 404;
        throw error;
      }
      return Post.findByIdAndRemove(postId);
    })
    .then((result) => {
      res.status(200).json({
        message: "The post deleted successfully",
        data: {
          post: result,
        },
      });
    })
    .catch((err) => next(err));
};
