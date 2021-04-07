const express = require("express");
const fireController = require("../controllers/fire");
const isAuth = require("../middleware/is-auth");

const router = express.Router();

router.get("/", isAuth, fireController.getFireRecords);

router.post("/", isAuth, fireController.addFireRecord);

router.put("/", isAuth, fireController.addFireRecords);

router.get("/:fireId", isAuth, fireController.getFireRecord);

router.put("/:fireId", isAuth, fireController.updateFireRecord);

router.delete("/:fireId", isAuth, fireController.deleteFireRecord);

module.exports = router;
