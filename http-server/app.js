var http = require('http');
var searchUrl = require('url');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var fs = require('fs');
const express = require("express");
const app = express();


earthquake();
fire();
flood();


function earthquake(){
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

    let eqdata = {
        table: []
    };
    for (i = 0; i < jsonDataFromSite1.length; i++) {
        eqdata.table.push({
            magnitude: jsonDataFromSite1[i].Mag,
            depth: jsonDataFromSite1[i].Derinlik,
            latitude: jsonDataFromSite1[i].Enlem.substring(0,jsonDataFromSite1[i].Enlem.indexOf("&")),
            longitude: jsonDataFromSite1[i].Boylam.substring(0,jsonDataFromSite1[i].Boylam.indexOf("&")),
            location: jsonDataFromSite1[i].Yer,
            occured_at: jsonDataFromSite1[i].Zaman,
            updated_at: jsonDataFromSite1[i].SonGuncelleme
        });
    }
    fs.writeFileSync('./earthquake.json', JSON.stringify(eqdata,null,2));
}

function fire(){
    //F I R E//////////////////////////////////////////////////////////////////////////////
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "https://www.ogm.gov.tr/tr/orman-yanginlari", false ); // false for synchronous request
    xmlHttp.send( null );
    var pageSource = xmlHttp.responseText;
    var jsonStrFromSite2 = pageSource.slice((pageSource.indexOf("{items:[{\"data\":")+16),(pageSource.indexOf("}]}]}}),")+2));
    var jsonDataFromSite2 = JSON.parse(jsonStrFromSite2);

    let fdata = {
        table: []
    };

    for (i = 0; i < jsonDataFromSite2.length; i++) {

        var str = jsonDataFromSite2[i].YanginBaslamaZamani;
        var x = str.substring(6,str.indexOf(")"));
        ybz = new Date(parseInt(x)).toISOString().split('T')[0] + " " + jsonDataFromSite2[i].YanginBaslamaSaati + ":00";

        fdata.table.push({
            key: i,
            status: jsonDataFromSite2[i].YanginDurumu,
            occured_at: ybz,
            riskStatus: jsonDataFromSite2[i].RiskDurumu,
            latitude: jsonDataFromSite2[i].XKoordinati,
            longitude: jsonDataFromSite2[i].YKoordinati,
            city: jsonDataFromSite2[i].IlAdi,
            town: jsonDataFromSite2[i].IlceAdi,
            village: ""
        });
    }
    fs.writeFileSync('./fire.json', JSON.stringify(fdata,null,2));
}

function flood(){
    //F L O O D//////////////////////////////////////////////////////////////////////////////
    var searchUrl ="";
    var jsonStr="[";
    var xmlHttp = new XMLHttpRequest();
    for (pageIndex = 1; pageIndex < 100; pageIndex++) {
        searchUrl = "https://akom.ibb.istanbul/Akomas/Akom_Sorgu.aspx?sp="+ pageIndex + "&au=1&as=-1&ai=-1&at=12&t1=&t2=";
        xmlHttp.open( "GET", searchUrl, false ); // false for synchronous request
        xmlHttp.send( null );
        if (xmlHttp.readyState==4 && xmlHttp.status==200){
            var pageSource = xmlHttp.responseText;
            var tableStr = pageSource.slice(pageSource.indexOf("<table class=\"table table-hover table-striped\">"),pageSource.indexOf("</table>"));
            var rowArray = tableStr.split("<tr");
            for (i = 2; i < rowArray.length; i++) {
                columnArray = rowArray[i].split("<td>");
                jsonStr = jsonStr + "{\"AfetNumarası\":\"" + columnArray[1].substring(0,columnArray[1].indexOf("</td>")) + "\","
                + "\"AfetTürü\":\"" + columnArray[2].substring(0,columnArray[2].indexOf("</td>")) + "\","
                + "\"Ülke\":\"" + columnArray[3].substring(0,columnArray[3].indexOf("</td>")) + "\","
                + "\"Şehir\":\"" + columnArray[4].substring(0,columnArray[4].indexOf("</td>")) + "\","
                + "\"İlçe\":\"" + columnArray[5].substring(0,columnArray[5].indexOf("</td>")) + "\","
                + "\"Açıklama\":\"" + columnArray[6].substring(0,columnArray[6].indexOf("</td>")) + "\"},"
                + "\n"
            };
        }else{
            pageIndex = 101;
        }
    }
    jsonStrFromSite3 = jsonStr.substring(0,jsonStr.length-2) + "]";
    var jsonDataFromSite3 = JSON.parse(jsonStrFromSite3);

    let fldata = {
        table: []
    };
    for (i = 0; i < jsonDataFromSite3.length; i++) {

        fldata.table.push({
            occured_at: jsonDataFromSite3[i].AfetNumarası,
            city: jsonDataFromSite3[i].Şehir
        });
    }
    fs.writeFileSync('./flood.json', JSON.stringify(fldata,null,2));
}
