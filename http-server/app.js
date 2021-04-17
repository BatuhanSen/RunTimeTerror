var disaster = require('./getDisasters'); 
const express = require("express");
const app = express();


disaster.earthquake();
disaster.fire();
disaster.flood();
