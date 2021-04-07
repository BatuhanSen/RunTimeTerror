const Post = require("../models/post");
const User = require("../models/user");

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
  let author;

  const post = new Post({
    title: title,
    content: content,
    imageUrl: imageUrl,
    author: req.body.userId,
  });

  post
    .save()
    .then((result) => {
      return User.findById(req.body.userId);
    })
    .then((user) => {
      author = user;
      user.posts.push(post);
      return user.save();
    })
    .then((result) => {
      res.status(201).json({
        message: "Post created successfully",
        data: {
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
  const userId = req.body.userId;

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
      return User.findById(userId);
    })
    .then((user) => {
      user.posts.pull(postId);
      return user.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "The post deleted successfully",
      });
    })

    .catch((err) => next(err));
};
