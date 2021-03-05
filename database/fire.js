var xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", "https://www.ogm.gov.tr/tr/orman-yanginlari", false ); // false for synchronous request
xmlHttp.send( null );
var pageSource = xmlHttp.responseText;
var jsonDataStr = pageSource.slice((pageSource.indexOf("{items:[{\"data\":")+16),(pageSource.indexOf("}]}]}}),")+2));
var jsonArr = JSON.parse(jsonDataStr);

//var j;
//for (j = 0; j < jsonArr.length; j++) {
//  console.log(jsonArr[i]);
//};