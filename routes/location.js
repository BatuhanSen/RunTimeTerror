const express = require("express");
const { body } = require("express-validator");

const Location = require("../models/location");
const locationController = require("../controllers/location");

const router = express.Router();

router.get("/", locationController.getLocationRecords);

router.post("/", locationController.addLocationRecord);

router.get("/:locationId", locationController.getLocationRecord);

router.put("/:locationId", locationController.updateLocationRecord);

router.delete("/:locationId", locationController.deleteLocationRecord);

module.exports = router;
