const express = require("express");
const { body } = require("express-validator");

const User = require("../models/user");
const contactController = require("../controllers/contact");

const router = express.Router();

router.put(
  "/",
  [
    body("email").isEmail().withMessage("Please enter a valid email"),
    body("message")
      .trim()
      .isLength({ min: 5 })
      .withMessage("Message must be at least 5 characters"),
  ],
  contactController.addContact
);

module.exports = router;
