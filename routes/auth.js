const express = require("express");
const { body } = require("express-validator");

const User = require("../models/user");
const authController = require("../controllers/auth");

const router = express.Router();

router.put(
  "/signup",
  [
    body("email").custom((value) => {
      return User.findOne({ email: value }).then((user) => {
        if (user) {
          return Promise.reject("The user already exists!");
        }
      });
    }),
  ],
  authController.signup
);

router.post("/login", authController.login);

module.exports = router;
