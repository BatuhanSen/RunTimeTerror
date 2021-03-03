package com.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.model.Deprem;


public class Start {

	public static void main(String[] args) {
		try {
			System.out.println("Deprem Parser Start");
			ArrayList<Deprem> depremList = getDepremData();
			System.out.println("Deprem Parser End");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<Deprem> getDepremData() throws IOException {
		ArrayList<Deprem> depremArray = new ArrayList<Deprem>();
		String depremHttpSource ="";
		for (int i = 0; i < 100; i++) {
			try {
				if (i==0){
					depremHttpSource = getURLSource("http://sc3.koeri.boun.edu.tr/eqevents/events.html");
				}else{
					depremHttpSource = getURLSource("http://sc3.koeri.boun.edu.tr/eqevents/events"+ i + ".html");
				}
				parseDepremHttpSource(depremHttpSource,depremArray);
				
			} catch (IOException e) {
				i=101;
			}
		}
		return depremArray;
	}
	
    private static void parseDepremHttpSource(String depremHttpSource, ArrayList<Deprem> depremArray) {
		String tableStr = depremHttpSource.substring(depremHttpSource.indexOf("<table class=\"index\">"),depremHttpSource.indexOf("<table class=\"links\">"));
		String[] rowArray = tableStr.split("<tr");
		for (int i = 2; i < rowArray.length; i++) {
			String[] columnArray = rowArray[i].split("<td>");
			
			Deprem deprem = new Deprem();
			deprem.setZaman(columnArray[1].substring(0,columnArray[1].indexOf("</td>")));
			deprem.setMag(columnArray[2].substring(0,columnArray[2].indexOf("</td>")));
			deprem.setMagTipi(columnArray[3].substring(0,columnArray[3].indexOf("</td>")));
			deprem.setEnlem(columnArray[4].substring(0,columnArray[4].indexOf("</td>")));
			deprem.setBoylam(columnArray[5].substring(0,columnArray[5].indexOf("</td>")));
			deprem.setDerinlik(columnArray[6].substring(0,columnArray[6].indexOf("</td>")));
			deprem.setYer(columnArray[7].substring(0,columnArray[7].indexOf("</td>")));
			deprem.setTip(columnArray[8].substring(0,columnArray[8].indexOf("</td>")));
			deprem.setSonGuncelleme(columnArray[9].substring(0,columnArray[9].indexOf("</td>")));
			depremArray.add(deprem);
		}
	}

	public static String getURLSource(String url) throws IOException {
        URL urlObject = new URL(url);
        URLConnection urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        return toString(urlConnection.getInputStream());
    }

    private static String toString(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            return stringBuilder.toString();
        }
    }
}


