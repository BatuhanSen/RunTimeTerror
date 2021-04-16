const { validationResult } = require("express-validator");
const User = require("../models/user");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

exports.signup = (req, res, next) => {
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    const error = new Error("Validation failed.");
    error.statusCode = 422;
    error.data = errors.array();
    throw error;
  }

  const username = req.body.username;
  const password = req.body.password;
  const email = req.body.email;
  const name = req.body.name;
  const gender = req.body.gender;
  const phone = req.body.phone;

  bcrypt
    .hash(password, 12)
    .then((hashedPassword) => {
      const user = new User({
        username: username,
        password: hashedPassword,
        email: email,
        name: name,
        gender: gender,
        phone: phone,
      });
      return user.save();
    })
    .then((result) => {
      res.status(201).json({
        message: "User created successfully",
        data: {
          user: result,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.login = (req, res, next) => {
  const username = req.body.username;
  const password = req.body.password;
  let loadedUser;

  User.findOne({ username: username })
    .then((user) => {
      if (!user) {
        const error = new Error("A user not found with this username");
        error.statusCode = 401;
        throw error;
      }
      loadedUser = user;
      return bcrypt.compare(password, user.password);
    })
    .then((isEqual) => {
      if (!isEqual) {
        const error = new Error("Wrong password");
        error.statusCode = 401;
        throw error;
      }
      const token = jwt.sign(
        {
          email: loadedUser.email,
          userId: loadedUser._id.toString(),
        },
        "somesecretkey",
        { expiresIn: "6h" }
      );

      res.status(200).json({
        message: "Logged in successfully.",
        data: {
          userId: loadedUser._id.toString(),
          user: loadedUser,
          token: token,
        },
      });
    })
    .catch((err) => {
      next(err);
    });
};

exports.updateUser = (req, res, next) => {
  const userId = req.params.userId;

  const password = req.body.password;
  const name = req.body.name;
  const gender = req.body.gender;
  const phone = req.body.phone;

  User.findById(userId)
    .then((user) => {
      if (!user) {
        const error = new Error("Could not find the user!");
        error.statusCode = 404;
        throw error;
      }

      user.password = password || user.password;
      user.name = name || user.name;
      user.gender = gender || user.gender;
      user.phone = phone || user.phone;

      return user.save();
    })
    .then((result) => {
      res.status(200).json({
        message: "User updated successfully",
      });
    })
    .catch((err) => next(err));
};
