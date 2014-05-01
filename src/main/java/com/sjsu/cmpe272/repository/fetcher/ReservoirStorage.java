package com.sjsu.cmpe272.repository.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ReservoirStorage {
	private final String USER_AGENT = "Mozilla/5.0";
	private static String value;

//	public ReservoirStorage() throws IOException {
//		ReservoirStorage objReservoir = new ReservoirStorage();
//		Map<Long, Long> reservoirStorage = objReservoir.getReservoirStorage("SHA");
//	}

	// Http GET Request.
	public Map<String, Long> getReservoirStorage(String station) throws IOException {
		String inputLine;
		String url = "http://cdec.water.ca.gov/cgi-progs/queryCSV?station_id="
				+ station
				+ "&sensor_num=15&dur_code=D&start_date=2010-01-01&end_date=2014-04-25&data_wish=View+CSV+Data";

		URL obj = new URL(url);
		HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
		connect.setRequestMethod("GET");
		connect.setRequestProperty("User-AGent", USER_AGENT);
		int responseCode = connect.getResponseCode();
		System.out.println("\n Sending GET Request to URL :" + url);
		System.out.println("Respond Code :" + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connect.getInputStream()));

		StringBuffer response = new StringBuffer();
		Map<String, Long> map = new TreeMap<String, Long>();
		// New changes for string search start here.
		in.readLine();
		in.readLine();
		while ((inputLine = in.readLine()) != null) {
			try {
				String[] split = inputLine.split(",");
				System.out.println("what????");
				if (split.length == 3) {
					System.out.println("yy problem???");
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					Date date;
					date = format.parse(split[0]);
					System.out.println("Date is w/o formatting :"+date);
					SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
					String dates = outputFormatter.format(date);
					System.out.println("Date is :"+dates);
					map.put(dates, Long.parseLong(split[2]));
				}
			} catch (Exception e) {
				//ignore errors
			}
			
		}
		// New changes for string seach end here.

		in.close();
		value = response.toString();

		// System.out.println("Here is the output: " +value);
		// //response.toString()
		return map;
	}

	public String getResponse() {
		return value;
	}

}
