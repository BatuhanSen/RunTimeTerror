const express = require("express");
const emergencyController = require("../controllers/emergency");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

router.post("/", isAuth, emergencyController.addEmergencyRecord);

module.exports = router;
