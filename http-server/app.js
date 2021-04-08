var http = require('http');
var searchUrl = require('url');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var fs = require('fs');
const express = require("express");
const app = express();
const importdata = require("./earthquake.json");



//http.createServer(function (req, res) {
 
  //res.writeHead(200, {'Content-Type': 'text/html'});
 // var q = url.parse(req.url, true).query;
 // var txt = q.prm1 + " " + q.prm2;
 // res.end(txt);

var searchUrl ="";
var jsonStr="[";
var xmlHttp = new XMLHttpRequest();
for (pageIndex = 0; pageIndex < 100; pageIndex++) {
    if (pageIndex==0)
        searchUrl = "http://sc3.koeri.boun.edu.tr/eqevents/events.html";
    else
        searchUrl = "http://sc3.koeri.boun.edu.tr/eqevents/events"+ pageIndex + ".html";

    xmlHttp.open( "GET", searchUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    if (xmlHttp.readyState==4 && xmlHttp.status==200){
        var pageSource = xmlHttp.responseText;
        var tableStr = pageSource.slice(pageSource.indexOf("<table class=\"index\">"),pageSource.indexOf("<table class=\"links\">"));
        var rowArray = tableStr.split("<tr");
        for (i = 2; i < rowArray.length; i++) {
            columnArray = rowArray[i].split("<td>");
            jsonStr = jsonStr + "{\"Zaman\":\"" + columnArray[1].substring(0,columnArray[1].indexOf("</td>")) + "\","
            + "\"Mag\":\"" + columnArray[2].substring(0,columnArray[2].indexOf("</td>")) + "\","
            + "\"MagTipi\":\"" + columnArray[3].substring(0,columnArray[3].indexOf("</td>")) + "\","
            + "\"Enlem\":\"" + columnArray[4].substring(0,columnArray[4].indexOf("</td>")) + "\","
            + "\"Boylam\":\"" + columnArray[5].substring(0,columnArray[5].indexOf("</td>")) + "\","
            + "\"Derinlik\":\"" + columnArray[6].substring(0,columnArray[6].indexOf("</td>")) + "\","
            + "\"Yer\":\"" + columnArray[7].substring(0,columnArray[7].indexOf("</td>")) + "\","
            + "\"Tip\":\"" + columnArray[8].substring(0,columnArray[8].indexOf("</td>")) + "\","
            + "\"SonGuncelleme\":\"" + columnArray[9].substring(0,columnArray[9].indexOf("</td>")) + "\"},"
            + "\n"
        };
    }else{
        pageIndex = 101;
    }
}
jsonStr = jsonStr.substring(0,jsonStr.length-2) + "]";
var jsonData = JSON.parse(jsonStr);
fs.writeFile('earthquake.json', jsonStr, function (err) {
    if (err) throw err;
    console.log('Saved!');
});

//var j;
//for (j = 0; j < jsonArr.length; j++) {
//    res.write(jsonArr[j].Enlem);
  //console.log(jsonArr[j]);
//};
// res.end();
//}).listen(8080);


