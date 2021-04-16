const Fire = require("../models/fire");

exports.getFireRecords = (req, res, next) => {
  Fire.find()
    .sort({ occured_at: -1 })
    .then((fireRecords) => {
      if (fireRecords.length == 0) {
        const error = new Error("Could not find any fire records");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Fire records fetched successfully",
        data: {
          fireRecords: fireRecords,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getFireRecord = (req, res, next) => {
  const fireId = req.params.fireId;

  Fire.findById(fireId)
    .then((fireRecord) => {
      if (!fireRecord) {
        const error = new Error("Could not find the fire record");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Earthquake record fetched successfully",
        data: {
          fireRecord: fireRecord,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addFireRecord = (req, res, next) => {
  const status = req.body.status;
  const occured_at = req.body.occured_at;
  const riskStatus = req.body.riskStatus;
  const latitude = req.body.latitude;
  const longitude = req.body.longitude;
  const city = req.body.city;
  const town = req.body.town;
  const village = req.body.village;

  const fireRecord = new Fire({
    status: status,
    riskStatus: riskStatus,
    occured_at: occured_at,
    latitude: latitude,
    longitude: longitude,
    city: city,
    town: town,
    village: village,
  });

  fireRecord
    .save()
    .then((result) => {
      res.status(201).json({
        message: "Earthquake record created successfully",
        data: {
          earthquakeRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addFireRecords = (req, res, next) => {
  Fire.insertMany(req.body)
    .then((result) => {
      res.status(201).json({
        message: "Fire records created successfully!",
        data: {
          fireRecords: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.updateFireRecord = (req, res, next) => {
  const fireId = req.params.fireId;

  const locationId = req.body.locationId;
  const status = req.body.status;
  const duration = req.body.duration;

  Fire.findById(fireId)
    .then((fireRecord) => {
      if (!fireRecord) {
        const error = new Error("Could not find the fire record");
        error.statusCode = 404;
        throw error;
      }

      fireRecord.locationId = locationId || fireRecord.locationId;
      fireRecord.status = status || fireRecord.status;
      fireRecord.duration = duration || fireRecord.duration;

      return fireRecord.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "Fire record updated successfully",
        data: {
          fireRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.deleteFireRecord = (req, res, next) => {
  const fireId = req.params.fireId;

  Fire.findById(fireId)
    .then((fireRecord) => {
      if (!fireRecord) {
        const error = new Error("Could not find the fire record");
        error.statusCode = 404;
        throw error;
      }

      return findByIdAndRemove(fireId);
    })
    .then((result) => {
      res.status(200).json({
        message: "Fire record deleted successfully",
        data: {
          fireRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};
