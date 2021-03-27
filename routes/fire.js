const express = require("express");
const fireController = require("../controllers/fire");

const router = express.Router();

router.get("/", fireController.getFireRecords);

router.post("/", fireController.addFireRecord);

router.put("/", fireController.addFireRecords);

router.get("/:fireId", fireController.getFireRecord);

router.put("/:fireId", fireController.updateFireRecord);

router.delete("/:fireId", fireController.deleteFireRecord);

module.exports = router;
