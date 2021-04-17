const Emergency = require("../models/emergency");

exports.addEmergencyRecord = (req, res, next) => {
  const user = req.body.user;
  const personCount = req.body.personCount;
  const message = req.body.message;
  const latitude = req.body.latitude;
  const longitude = req.body.longitude;
  const address = req.body.address;

  const emergencyRecord = new Emergency({
    personCount: personCount,
    message: message,
    latitude: latitude,
    longitude: longitude,
    address: address,
    user: user,
  });

  emergencyRecord
    .save()
    .then((result) => {
      res.status(200).json({
        message: "Emergency Record created successfully",
      });
    })
    .catch((err) => next(err));
};
