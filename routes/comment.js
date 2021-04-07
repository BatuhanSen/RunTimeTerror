const express = require("express");
const { body } = require("express-validator");

const commentController = require("../controllers/comment");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

router.get("/:postId", isAuth, commentController.getComments);

router.post("/", isAuth, commentController.addComment);

router.get("/:commentId", isAuth, commentController.getComment);

router.put("/:commentId", isAuth, commentController.updateComment);

router.delete("/:commentId", isAuth, commentController.deleteComment);

module.exports = router;
