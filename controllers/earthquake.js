const Earthquake = require("../models/earthquake");

exports.getEarthquakeRecords = (req, res, next) => {
  Earthquake.find()
    .sort({ updated_at: -1 })
    .then((earthquakeRecords) => {
      if (earthquakeRecords.length == 0) {
        const error = new Error("Could not find any earthquake records");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Earthquake records fetched successfully",
        data: {
          earthquakeRecords: earthquakeRecords,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getEarthquakeRecord = (req, res, next) => {
  const earthquakeId = req.params.earthquakeId;

  Earthquake.findById(earthquakeId)
    .then((earthquakeRecord) => {
      if (!earthquakeRecord) {
        const error = new Error("Could not find earthquake record");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Earthquake record fetched successfully",
        data: {
          earthquakeRecord: earthquakeRecord,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addEarthquakeRecord = (req, res, next) => {
  const magnitude = req.body.magnitude;
  const depth = req.body.depth;
  const occured_at = req.body.occured_at;
  const updated_at = req.body.updated_at;
  const location = req.body.location;
  const latitude = req.body.latitude;
  const longitude = req.body.longitude;

  console.log("EARTHQUEAKE BODY", req.body);
  const earthquakeRecord = new Earthquake({
    magnitude: magnitude,
    depth: depth,
    occured_at: occured_at,
    updated_at: updated_at,
    latitude: latitude,
    longitude: longitude,
    location: location,
  });

  earthquakeRecord
    .save()
    .then((result) => {
      res.status(201).json({
        message: "Earthquake record created successfully",
        data: {
          earthquakeRecord: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.updateEarthquakeRecord = (req, res, next) => {
  const earthquakeId = req.params.earthquakeId;

  const magnitude = req.body.magnitude;
  const depth = req.body.depth;
  const occured_at = req.body.occuredAt;
  const updated_at = req.body.updatedAt;

  Earthquake.findById(earthquakeId)
    .then((earthquakeRecord) => {
      if (!earthquakeRecord) {
        const error = new Error("Could not find earthquake record");
        error.statusCode = 404;
        throw error;
      }
      earthquakeRecord.magnitude = magnitude || earthquakeRecord.magnitude;
      earthquakeRecord.depth = depth || earthquakeRecord.depth;
      earthquakeRecord.occured_at = occured_at || earthquakeRecord.occured_at;
      earthquakeRecord.updated_at = updated_at || earthquakeRecord.updated_at;

      return earthquakeRecord.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "Earthquake record updated successfully",
        data: {
          earthquakeRecord: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.deleteEarthquakeRecord = (req, res, next) => {
  const earthquakeId = req.params.earthquakeId;

  Earthquake.findById(earthquakeId)
    .then((earthquakeRecord) => {
      if (!earthquakeRecord) {
        const error = new Error("Could not find earthquake record");
        error.statusCode = 404;
        throw error;
      }
      return Earthquake.findByIdAndRemove(earthquakeId);
    })
    .then((result) => {
      res.status(200).json({
        message: "Earthquake record deleted successfully",
        data: {
          earthquakeRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addEarthquakeRecords = (req, res, next) => {
  Earthquake.insertMany(req.body)
    .then((result) => {
      res.status(201).json({
        message: "Earthquake records created successfully!",
        data: {
          earthquakeRecords: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};
