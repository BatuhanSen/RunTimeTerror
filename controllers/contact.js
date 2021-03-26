const Contact = require("../models/contact");

exports.addContact = (req, res, next) => {
  const name = req.body.name;
  const surname = req.body.surname;
  const email = req.body.email;
  const phoneNumber = req.body.phoneNumber;
  const message = req.body.message;

  const contact = new Contact({
    username: name,
    surname: surname,
    email: email,
    phoneNumber: phoneNumber,
    message: message,
  });

  contact
    .save()
    .then(() => {
      res.status(201).json({
        message: "Your request received successfully",
      });
    })
    .catch((err) => next(err));
};
