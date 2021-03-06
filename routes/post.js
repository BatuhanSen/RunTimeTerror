const express = require("express");
const { body } = require("express-validator");

const Post = require("../models/post");
const postController = require("../controllers/post");

const router = express.Router();

router.get("/", postController.getPosts);

router.post("/", postController.addPost);

router.get("/:postId", postController.getPost);

router.put("/:postId", postController.updatePost);

router.delete("/:postId", postController.deletePost);

module.exports = router;
