const express = require("express");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const dotenv = require("dotenv");

const authRoutes = require("./routes/auth");

const app = express();
dotenv.config();

app.use(bodyParser.json());

app.use((req, res, next) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader(
    "Access-Control-Allow-Methods",
    "OPTIONS, GET, POST, PUT, PATCH, DELETE"
  );
  res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
  next();
});

app.use("/api/auth", authRoutes);

app.use((error, req, res, next) => {
  console.log("error middleware :(", error);
  const status = error.statusCode || 500;
  const message = error.message;
  const data = error.data;
  res.status(status).json({ message: message, data: data });
});

mongoose
  .connect(
    `mongodb+srv://${process.env.USER}:${process.env.PASSWORD}@mycluster.i3aby.mongodb.net/${process.env.DBNAME}?retryWrites=true&w=majority`,
    { useNewUrlParser: true, useUnifiedTopology: true }
  )
  .then((res) => {
    console.log("CONNECTED TO DATABASE");
    app.listen(process.env.PORT);
  })
  .catch((err) => {
    console.log("DBConnection Error", err);
  });
