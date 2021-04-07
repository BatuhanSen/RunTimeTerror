const express = require("express");
const floodController = require("../controllers/flood");
const isAuth = require("../middleware/is-auth");


const router = express.Router();

router.get("/", isAuth, floodController.getFloodRecords);

router.post("/", isAuth, floodController.addFloodRecord);

router.put("/", isAuth, floodController.addFloodRecords);

router.get("/:floodId", isAuth, floodController.getFloodRecord);

router.put("/:floodId", isAuth, floodController.updateFloodRecord);

router.delete("/:floodId", isAuth, floodController.deleteFloodRecord);

module.exports = router;
