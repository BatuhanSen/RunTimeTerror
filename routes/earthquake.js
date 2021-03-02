const express = require("express");
const earthquakeController = require("../controllers/earthquake");

const router = express.Router();

// GET api/earthquake
router.get("/", earthquakeController.getEarthquakeRecords);

router.post("/", earthquakeController.addEarthquakeRecord);

router.get("/:earthquakeId", earthquakeController.getEarthquakeRecord);

router.put("/:earthquakeId", earthquakeController.updateEarthquakeRecord);

router.delete("/:earthquakeId", earthquakeController.deleteEarthquakeRecord);

module.exports = router;
