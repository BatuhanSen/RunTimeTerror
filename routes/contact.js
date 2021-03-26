const express = require("express");
const { body } = require("express-validator");

const Contact = require("../models/user");
const contactController = require("../controllers/contact");

const router = express.Router();

router.put("/", contactController.addContact);

module.exports = router;
