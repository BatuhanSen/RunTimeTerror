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
			ArrayList<Deprem> depremList = getDepremData();
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
				System.out.println("---PAGE--->>> " + i);
				parseDepremHttpSource(depremHttpSource,depremArray);
				
			} catch (IOException e) {
				i=101;
			}
		}
		return depremArray;
	}
	
    private static void parseDepremHttpSource(String depremHttpSource, ArrayList<Deprem> depremArray) {
		String tableStr = depremHttpSource.substring(depremHttpSource.indexOf("<table class=\"index\">"),depremHttpSource.indexOf("<table class=\"links\">"));
		System.out.println("---TABLE--->>> " + tableStr);
		String[] rowArray = tableStr.split("<tr");
		for (int i = 1; i < rowArray.length; i++) {
			System.out.println("---ROW--->>> " + rowArray[i]);
			String[] columnArray = rowArray[i].split("<td>");
			for (int j = 1; j < columnArray.length; j++) {
				if (j%10!=0){
				System.out.println("---COLUMN--->>> " + columnArray[j].substring(0,columnArray[j].indexOf("</td>")));
				}
			}
			
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


