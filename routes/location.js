const express = require("express");
const { body } = require("express-validator");

const Location = require("../models/location");
const locationController = require("../controllers/location");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

router.get("/", isAuth, locationController.getLocationRecords);

router.post("/", isAuth, locationController.addLocationRecord);

router.get("/:locationId", isAuth, locationController.getLocationRecord);

router.put("/:locationId", isAuth, locationController.updateLocationRecord);

router.delete("/:locationId", isAuth, locationController.deleteLocationRecord);

module.exports = router;
