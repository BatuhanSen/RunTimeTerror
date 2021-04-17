const Earthquake = require("../models/earthquake");
const Fire = require("../models/fire");

const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

exports.getEarthquakeRecords = async () => {
  console.log("Earthquake records is fetching...");

  const earthquakes = await Earthquake.find().sort({ updated_at: -1 });
  const lastEarthquake = earthquakes[0];

  var searchUrl = "";
  var jsonStr = "[";
  var xmlHttp = new XMLHttpRequest();
  searchUrl = "http://sc3.koeri.boun.edu.tr/eqevents/events.html";

  xmlHttp.open("GET", searchUrl, false); // false for synchronous request
  xmlHttp.send(null);
  if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
    var pageSource = xmlHttp.responseText;
    var tableStr = pageSource.slice(
      pageSource.indexOf('<table class="index">'),
      pageSource.indexOf('<table class="links">')
    );
    var rowArray = tableStr.split("<tr");
    for (i = 2; i < rowArray.length; i++) {
      columnArray = rowArray[i].split("<td>");
      jsonStr =
        jsonStr +
        '{"Zaman":"' +
        columnArray[1].substring(0, columnArray[1].indexOf("</td>")) +
        '",' +
        '"Mag":"' +
        columnArray[2].substring(0, columnArray[2].indexOf("</td>")) +
        '",' +
        '"MagTipi":"' +
        columnArray[3].substring(0, columnArray[3].indexOf("</td>")) +
        '",' +
        '"Enlem":"' +
        columnArray[4].substring(0, columnArray[4].indexOf("</td>")) +
        '",' +
        '"Boylam":"' +
        columnArray[5].substring(0, columnArray[5].indexOf("</td>")) +
        '",' +
        '"Derinlik":"' +
        columnArray[6].substring(0, columnArray[6].indexOf("</td>")) +
        '",' +
        '"Yer":"' +
        columnArray[7].substring(0, columnArray[7].indexOf("</td>")) +
        '",' +
        '"Tip":"' +
        columnArray[8].substring(0, columnArray[8].indexOf("</td>")) +
        '",' +
        '"SonGuncelleme":"' +
        columnArray[9].substring(0, columnArray[9].indexOf("</td>")) +
        '"},' +
        "\n";
    }

    jsonStrFromSite1 = jsonStr.substring(0, jsonStr.length - 2) + "]";
    var jsonDataFromSite1 = JSON.parse(jsonStrFromSite1);

    let eqdata = [];
    for (i = jsonDataFromSite1.length - 1; i >= 0; i--) {
      const updateDate = new Date(jsonDataFromSite1[i].SonGuncelleme);
      if (lastEarthquake == undefined || lastEarthquake.updated_at < updateDate)
        eqdata.push({
          magnitude: jsonDataFromSite1[i].Mag,
          depth: jsonDataFromSite1[i].Derinlik,
          latitude: jsonDataFromSite1[i].Enlem.substring(
            0,
            jsonDataFromSite1[i].Enlem.indexOf("&")
          ),
          longitude: jsonDataFromSite1[i].Boylam.substring(
            0,
            jsonDataFromSite1[i].Boylam.indexOf("&")
          ),
          location: jsonDataFromSite1[i].Yer,
          occured_at: jsonDataFromSite1[i].Zaman,
          updated_at: jsonDataFromSite1[i].SonGuncelleme,
        });
    }

    if (eqdata.length > 0) await Earthquake.insertMany(eqdata);
  }
};

exports.getFireRecords = async () => {
  console.log("Fire records is fetching...");

  const fires = await Fire.find().sort({ occured_at: -1 });
  const lastFire = fires[0];

  var xmlHttp = new XMLHttpRequest();
  xmlHttp.open("GET", "https://www.ogm.gov.tr/tr/orman-yanginlari", false); // false for synchronous request
  xmlHttp.send(null);
  var pageSource = xmlHttp.responseText;
  var jsonStrFromSite2 = pageSource.slice(
    pageSource.indexOf('{items:[{"data":') + 16,
    pageSource.indexOf("}]}]}}),") + 2
  );
  var jsonDataFromSite2 = JSON.parse(jsonStrFromSite2);

  let fdata = [];

  for (i = jsonDataFromSite2.length - 1; i >= 0; i--) {
    var str = jsonDataFromSite2[i].YanginBaslamaZamani;
    var x = str.substring(6, str.indexOf(")"));
    ybz =
      new Date(parseInt(x)).toISOString().split("T")[0] +
      " " +
      jsonDataFromSite2[i].YanginBaslamaSaati +
      ":00";

    ybz = new Date(ybz);
    if (lastFire.occured_at < ybz)
      fdata.push({
        status: jsonDataFromSite2[i].YanginDurumu,
        occured_at: ybz,
        riskStatus: jsonDataFromSite2[i].RiskDurumu,
        latitude: jsonDataFromSite2[i].XKoordinati,
        longitude: jsonDataFromSite2[i].YKoordinati,
        city: jsonDataFromSite2[i].IlAdi,
        town: jsonDataFromSite2[i].IlceAdi,
        village: "",
      });
  }
  if (fdata.length > 0) await Fire.insertMany(fdata);
};
