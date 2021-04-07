const express = require("express");
const { body } = require("express-validator");

const User = require("../models/user");
const authController = require("../controllers/auth");

const router = express.Router();

// PUT api/auth/signup
router.put(
  "/signup",
  [
    body("email")
      .isEmail()
      .withMessage("Please enter a valid email")
      .custom((value) => {
        return User.findOne({ email: value }).then((user) => {
          if (user) return Promise.reject("Email is already used!");
        });
      })
      .normalizeEmail(),
    body("password")
      .trim()
      .isLength({ min: 5, max: 30 })
      .withMessage("Password must be at least 5 characters")
      .matches(/\d/)
      .withMessage("Password must contain a number"),
    body("username")
      .trim()
      .isLength({ min: 5, max: 30 })
      .withMessage("Username must be at least 5 characters")
      .custom((value) => {
        return User.findOne({ username: value }).then((user) => {
          if (user) return Promise.reject("Username is already used!");
        });
      }),
  ],
  authController.signup
);

// POST api/auth/login
router.post("/login", authController.login);

module.exports = router;
