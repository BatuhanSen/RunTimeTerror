const express = require("express");
const { body } = require("express-validator");

const Post = require("../models/post");
const postController = require("../controllers/post");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

router.get("/", isAuth, postController.getPosts);

router.post("/", isAuth, postController.addPost);

router.get("/:postId", isAuth, postController.getPost);

router.put("/:postId", isAuth, postController.updatePost);

router.delete("/:postId", isAuth, postController.deletePost);

module.exports = router;
