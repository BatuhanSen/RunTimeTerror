const express = require("express");
const earthquakeController = require("../controllers/earthquake");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

// GET api/earthquake
router.get("/", isAuth, earthquakeController.getEarthquakeRecords);

router.post("/", isAuth, earthquakeController.addEarthquakeRecord);

router.put("/", isAuth, earthquakeController.addEarthquakeRecords);

router.get("/:earthquakeId", isAuth, earthquakeController.getEarthquakeRecord);

router.put(
  "/:earthquakeId",
  isAuth,
  earthquakeController.updateEarthquakeRecord
);

router.delete(
  "/:earthquakeId",
  isAuth,
  earthquakeController.deleteEarthquakeRecord
);

module.exports = router;
