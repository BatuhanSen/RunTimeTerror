var http = require('http');
var searchUrl = require('url');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var fs = require('fs');
const express = require("express");
const app = express();



//E A R T H Q U A K E//////////////////////////////////////////////////////////////////////////////
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
let eqloc = {
    table: []
};
let eqdata = {
    table: []
};
for (i = 0; i < jsonDataFromSite1.length; i++) {
    eqloc.table.push({
        id: i,
        Enlem: jsonDataFromSite1[i].Enlem.substring(0,jsonDataFromSite1[i].Enlem.indexOf("&")),
        Boylam: jsonDataFromSite1[i].Boylam.substring(0,jsonDataFromSite1[i].Boylam.indexOf("&")),
        Yer: jsonDataFromSite1[i].Yer
    });
    eqdata.table.push({
        id: i,
        Zaman: jsonDataFromSite1[i].Zaman,
        Mag: jsonDataFromSite1[i].Mag,
        MagTipi: jsonDataFromSite1[i].MagTipi,
        Derinlik: jsonDataFromSite1[i].Derinlik,
        SonGuncelleme: jsonDataFromSite1[i].SonGuncelleme
    });
}
fs.writeFile('./earthquakeloc.json', JSON.stringify(eqloc), function (err) {
    if (err) throw err;
});
fs.writeFile('./earthquake.json', JSON.stringify(eqdata), function (err) {
    if (err) throw err;
});

//F I R E//////////////////////////////////////////////////////////////////////////////
var xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", "https://www.ogm.gov.tr/tr/orman-yanginlari", false ); // false for synchronous request
xmlHttp.send( null );
var pageSource = xmlHttp.responseText;
var jsonStrFromSite2 = pageSource.slice((pageSource.indexOf("{items:[{\"data\":")+16),(pageSource.indexOf("}]}]}}),")+2));
var jsonDataFromSite2 = JSON.parse(jsonStrFromSite2);

let floc = {
    table: []
};
let fdata = {
    table: []
};
for (i = 0; i < jsonDataFromSite2.length; i++) {
    floc.table.push({
        id: i,
        IlAdi: jsonDataFromSite2[i].IlAdi,
        IlceAdi: jsonDataFromSite2[i].IlceAdi,
        Enlem: jsonDataFromSite2[i].XKoordinati,
        Boylam: jsonDataFromSite2[i].YKoordinati

    });
    var str = jsonDataFromSite2[i].YanginBaslamaZamani;
    var x = str.substring(6,str.indexOf(")"));
    ybz = new Date(parseInt(x)).toISOString().split('T')[0] + " " + jsonDataFromSite2[i].YanginBaslamaSaati + ":00";

    fdata.table.push({
        id: i,
        YanginBaslamaZamani: ybz,
        YanginDurumu: jsonDataFromSite2[i].YanginDurumu,
        RiskDurumu: jsonDataFromSite2[i].RiskDurumu
    });
}
fs.writeFile('./fireloc.json', JSON.stringify(floc), function (err) {
    if (err) throw err;
});
fs.writeFile('./fire.json', JSON.stringify(fdata), function (err) {
    if (err) throw err;
});


