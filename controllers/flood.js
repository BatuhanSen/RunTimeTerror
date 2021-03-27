const Flood = require("../models/flood");

exports.getFloodRecords = (req, res, next) => {
  Flood.find()
    .then((floodRecords) => {
      if (floodRecords.length == 0) {
        const error = new Error("Could not find any flood record");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Flood record fetched successfully",
        data: {
          floodRecords: floodRecords,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getFloodRecord = (req, res, next) => {
  const floodId = req.params.floodId;

  Flood.find(floodId)
    .then((floodRecord) => {
      if (!floodRecord) {
        const error = new Error("Could not find the flood record");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Flood record fetched successfully",
        data: {
          floodRecord: floodRecord,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addFloodRecord = (req, res, next) => {
  const locationId = req.body.locationId;
  const quantity = req.body.quantity;
  const intensity = req.body.intensity;
  const duration = req.body.duration;

  const flood = new Flood({
    locationId: locationId,
    quantity: quantity,
    intensity: intensity,
    duration: duration,
  });

  flood
    .save()
    .then((floodRecord) => {
      res.status(201).json({
        message: "Flood record created successfully",
        data: {
          floodRecord: floodRecord,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addFloodRecords = (req, res, next) => {
  Flood.insertMany(req.body)
    .then((result) => {
      res.status(201).json({
        message: "Flood records created successfully!",
        data: {
          floodRecords: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.updateFloodRecord = (req, res, next) => {
  const flood = req.params.floodId;

  const locationId = req.body.locationId;
  const quantity = req.body.quantity;
  const intensity = req.body.intensity;
  const duration = req.body.duration;

  Flood.findById(floodId)
    .then((floodRecord) => {
      if (!floodRecord) {
        const error = new Error("Could not find the flood record");
        error.statusCode = 404;
        throw error;
      }

      floodRecord.locationId = locationId || floodRecord.locationId;
      floodRecord.quantity = quantity || floodRecord.quantity;
      floodRecord.intensity = intensity || floodRecord.intensity;
      floodRecord.duration = duration || floodRecord.duration;

      return floodRecord.save();
    })
    .then((result) => {
      res.status(201).json({
        message: "Flood record updated successfully",
        data: {
          floodRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.deleteFloodRecord = (req, res, next) => {
  const floodId = req.params.floodId;

  Flood.findById(floodId)
    .then((floodRecord) => {
      if (!floodRecord) {
        const error = new Error("Could not find the flood record");
        error.statusCode = 404;
        throw error;
      }

      return Flood.findByIdAndRemove(floodId);
    })
    .then((result) => {
      res.status(201).json({
        message: "Flood record deleted successfully",
        data: {
          floodRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};
