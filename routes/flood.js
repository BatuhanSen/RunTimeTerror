const express = require("express");
const floodController = require("../controllers/flood");

const router = express.Router();

router.get("/", floodController.getFloodRecords);

router.post("/", floodController.addFloodRecord);

router.put("/", floodController.addFloodRecords);

router.get("/:floodId", floodController.getFloodRecord);

router.put("/:floodId", floodController.updateFloodRecord);

router.delete("/:floodId", floodController.deleteFloodRecord);

module.exports = router;
