const Fire = require("../models/fire");

exports.getFireRecords = (req, res, next) => {
  Fire.find()
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
  const locationId = req.body.locationId;
  const status = req.body.status;
  const duration = req.body.duration;

  const fireRecord = new Fire({
    locationId: locationId,
    status: status,
    duration: duration,
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
