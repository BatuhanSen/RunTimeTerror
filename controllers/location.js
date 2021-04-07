const Location = require("../models/location");

exports.getLocationRecords = (req, res, next) => {
  Location.find()
    .then((locationRecords) => {
      if (locationRecords.length == 0) {
        const error = new Error("Could not find any location records");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Location records fetched successfully",
        data: {
          locationRecords: locationRecords,
        },
      });
    })
    .catch((err) => next(err));
};

exports.getLocationRecord = (req, res, next) => {
  const locationId = req.params.locationId;
  Location.findById(locationId)
    .then((locationRecord) => {
      if (!locationRecord) {
        const error = new Error("Could not find location record");
        error.statusCode = 404;
        throw error;
      }
      res.status(200).json({
        message: "Location records fetched successfully",
        data: {
          locationRecord: locationRecord,
        },
      });
    })
    .catch((err) => next(err));
};

exports.addLocationRecord = (req, res, next) => {
  const city = req.body.city;
  const town = req.body.town;
  const village = req.body.village;
  const latitude = req.body.latitude;
  const longitude = req.body.longitude;
  const userId = req.body.userId;

  const location = new Location({
    city: city,
    town: town,
    village: village,
    latitude: latitude,
    longitude: longitude,
    user: userId,
  });

  location
    .save()
    .then((result) => {
      res.status(201).json({
        message: "Location record created successfully",
        data: {
          locationRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.updateLocationRecord = (req, res, next) => {
  const locationId = req.params.locationId;

  const city = req.body.city;
  const town = req.body.town;
  const village = req.body.village;
  const latitude = req.body.latitude;
  const longitude = req.body.longitude;

  Location.findById(locationId)
    .then((locationRecord) => {
      if (!locationRecord) {
        const error = new Error("Could not find location record");
        error.statusCode = 404;
        throw error;
      }

      locationRecord.city = city || locationRecord.city;
      locationRecord.town = town || locationRecord.town;
      locationRecord.village = village || locationRecord.village;
      locationRecord.latitude = latitude || locationRecord.latitude;
      locationRecord.longitude = longitude || locationRecord.longitude;

      return locationRecord.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "Location record updated successfully",
        data: {
          locationRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};

exports.deleteLocationRecord = (req, res, next) => {
  const locationId = req.params.locationId;

  Location.findById(locationId)
    .then((locationRecord) => {
      if (!locationRecord) {
        const error = new Error("Could not find location record");
        error.statusCode = 404;
        throw error;
      }

      return Location.findByIdAndRemove(locationId);
    })
    .then((result) => {
      res.status(200).json({
        message: "Location record removed successfully",
        data: {
          locationRecord: result,
        },
      });
    })
    .catch((err) => next(err));
};
