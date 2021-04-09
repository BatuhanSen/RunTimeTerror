var http = require('http');
var searchUrl = require('url');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var fs = require('fs');
const express = require("express");
const app = express();

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
jsonStrFromSite1 = jsonStr.substring(0,jsonStr.length-2) + "]";
var jsonDataFromSite1 = JSON.parse(jsonStrFromSite1);
fs.readFile('./earthquake.json',  function(err, jsonStrFromDb1) {
    if (err) throw err;

    var jsonDataFromDb1 = JSON.parse(jsonStrFromDb1);
    var tempJsonStrFromDb1 = "["+JSON.stringify(jsonStrFromDb1).substring(35)+"]";
    for (j = 0; j < jsonDataFromSite1.length; j++) {
        findIt = false;
        ind = 1
        do {
            if (JSON.stringify(jsonDataFromSite1[j]) == JSON.stringify(jsonDataFromDb1[ind++])){
                findIt = true;
            }
        } while (findIt);
        if (!findIt){
            newData = JSON.stringify(jsonDataFromSite1[j]);
            tempJsonStrFromDb1 = tempJsonStrFromDb1.substring(0,tempJsonStrFromDb1.length-1);
            if (tempJsonStrFromDb1.length < 10){
                tempJsonStrFromDb1 = tempJsonStrFromDb1 + newData + "]";
            }else{
                tempJsonStrFromDb1 = tempJsonStrFromDb1 + ",\n" + newData + "]";
            }
        }
    }
    fs.writeFile('./earthquake.json', tempJsonStrFromDb1, function (err) {
        if (err) throw err;
    });
});

var xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", "https://www.ogm.gov.tr/tr/orman-yanginlari", false ); // false for synchronous request
xmlHttp.send( null );
var pageSource = xmlHttp.responseText;
var jsonStrFromSite2 = pageSource.slice((pageSource.indexOf("{items:[{\"data\":")+16),(pageSource.indexOf("}]}]}}),")+2));
var jsonDataFromSite2 = JSON.parse(jsonStrFromSite2);
fs.readFile('./fire.json',  function(err, jsonStrFromDb2) {
    if (err) throw err;

    var jsonDataFromDb2 = JSON.parse(jsonStrFromDb2);
    var tempJsonStrFromDb2 = "["+JSON.stringify(jsonStrFromDb2).substring(35)+"]";
    for (j = 0; j < jsonDataFromSite2.length; j++) {
        findIt = false;
        ind = 1
        do {
            if (JSON.stringify(jsonDataFromSite2[j]) == JSON.stringify(jsonDataFromDb2[ind++])){
                findIt = true;
            }
        } while (findIt);
        if (!findIt){
            newData = JSON.stringify(jsonDataFromSite2[j]);
            tempJsonStrFromDb2 = tempJsonStrFromDb2.substring(0,tempJsonStrFromDb2.length-1);
            if (tempJsonStrFromDb2.length < 10){
                tempJsonStrFromDb2 = tempJsonStrFromDb2 + newData + "]";
            }else{
                tempJsonStrFromDb2 = tempJsonStrFromDb2 + ",\n" + newData + "]";
            }
            
        }
    }
    fs.writeFile('./fire.json', tempJsonStrFromDb2, function (err) {
        if (err) throw err;
    });
});
