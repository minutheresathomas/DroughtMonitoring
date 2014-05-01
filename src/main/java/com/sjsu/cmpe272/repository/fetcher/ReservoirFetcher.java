package com.sjsu.cmpe272.repository.fetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sjsu.cmpe272.domain.Reservoir;

public class ReservoirFetcher {
	private ReservoirStorage reservoirStorage;
	private final String USER_AGENT = "Mozilla/5.0";
	private static String value;

//	static String stationIds[] = { "	ALM	"};
	
	static String stationIds[] = { "ALM", "ANT", "APN", "AST", "ATN",
		"BAR", "BCL", "BDP", "BER", "BHC", "BIL", "BIO",
		"BIT", "BLB", "BMP", "BOC", "BQC", "BRD", "BRT",
		"BRV", "BTH", "BTV", "BUC", "BUL", "BUR", "BUX",
		"BWN", "BWS", "CAS", "CCH", "CFW", "CGS", "CHB",
		"CHV", "CHY", "CKL", "CLA", "CLE", "CLK", "CMB",
		"CMI", "CMN", "CNV", "COY", "CPL", "CRW", "CRY",
		"CSI", "CTG", "CUY", "CVE", "CYC", "CYL", "DAV",
		"DLV", "DMV", "DNL", "DNN", "DNP", "DON", "DRE",
		"DWN", "ECO", "EDN", "ELC", "ENG", "ENR", "EPK",
		"EXC", "FLR", "FMD", "FOL", "FRD", "FRL", "FRM",
		"GBL", "GBR", "GDW", "GLK", "GLL", "GNT", "HDG",
		"HHL", "HHY", "HID", "HMT", "HNN", "HNS", "HNT",
		"HTH", "HVS", "HWE", "ICH", "INL", "INP", "INV",
		"IRC", "IRG", "ISB", "JCK", "JNC", "JNK", "JNN",
		"KES", "KLM", "KNT", "KRH", "LBS", "LEW", "LFY",
		"LGT", "LGV", "LNG", "LON", "LOT", "LPY", "LRA",
		"LSB", "LUS", "LVD", "LVQ", "LVY", "LWB", "LYS",
		"MAR", "MAT", "MCO", "MCR", "MCS", "MDO", "MEA",
		"MFF", "MHV", "MHW", "MIL", "MMR", "MMW", "MNC",
		"MOR", "MPD", "MPL", "MRR", "MRT", "NAT", "NCA",
		"NCM", "NHG", "NML", "ONF", "ORO", "OWN", "PAR",
		"PLL", "PNF", "PRA", "PRR", "PRS", "PRU", "PT6",
		"PT7", "PWL", "PYM", "QUL", "RBL", "RDN", "RLC",
		"RLF", "RLL", "RTD", "SAT", "SCC", "SCD", "SDB",
		"SFL", "SGB", "SGC", "SHA", "SHV", "SIV", "SJT",
		"SKN", "SLB", "SLC", "SLF", "SLJ", "SLK", "SLN",
		"SLS", "SLW", "SNL", "SNN", "SOL", "SPB", "SPC",
		"SPG", "SPM", "STD", "STG", "STP", "SVO", "SVT",
		"SW3", "SWB", "TAB", "TAE", "TAH", "TFR", "THD",
		"TLC", "TMT", "TNM", "TRM", "TUL", "TWT", "UNV",
		"USL", "VAR", "VIL", "VLP", "WHI", "WHR", "WRS",
		"WSN "};
	
	public ReservoirFetcher() 
	{
		reservoirStorage = new ReservoirStorage();
	}

	public List<Reservoir> getAllReservoirs() throws Exception {
		System.out.println("Testing1: Sending Http GET Request.");
		List<Reservoir> reservoirList = new ArrayList<Reservoir>();

		for (int i = 0; i < stationIds.length; i++) {
			Reservoir reservoir = sendRequest(stationIds[i].trim());
			Map<String, Long> storage = reservoirStorage.getReservoirStorage(stationIds[i].trim());
			reservoir.setStorageData(storage);
			reservoirList.add(reservoir);
		}
		
		return reservoirList;
	}

	// Http GET Request.
	public Reservoir sendRequest(String allStations) throws Exception {

		String url = "http://cdec.water.ca.gov/cgi-progs/staMeta?station_id="
				+ allStations;
		URL obj = new URL(url);
		HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
		connect.setRequestMethod("GET");
		connect.setRequestProperty("User-AGent", USER_AGENT);
		int responseCode = connect.getResponseCode();
		System.out.println("\n Sending GET Request to URL :" + url);
		System.out.println("Respond Code :" + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connect.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		// New changes for string seach start here.
		while ((inputLine = in.readLine()) != null) {
			if (inputLine.endsWith("<DIV class=content_left_column>")) {
				List<String> s = new ArrayList<String>();
				for (int i = 0; i < 10; i++) {
					s.add(in.readLine());
				}
				return parseTable(s);
			}
			// response.append(inputLine).append("\n");
		}
		// New changes for string seach end here.

		in.close();
		value = response.toString();

		// System.out.println("Here is the output: " +value);
		// //response.toString()
		return null;
	}

	private Reservoir parseTable(List<String> s) {
		Reservoir r = new Reservoir();

		for (String singleLine : s) {

			String stationName = getStringInBetween(singleLine, "<h2>",
					"</h2>");
			if (stationName != null) {
				r.setStationName(stationName);
			}
			String stationId = getStringInBetween(singleLine,
					"Station ID</b></td><td>", "</td><td><b>");
			if (stationId != null) {
				r.setStationId(stationId);
			}
			String elevation = getStringInBetween(singleLine,
					"Elevation</b></td><td>", "</td></tr>");
			if (elevation != null) {
				r.setElevation(elevation);
			}
			String riverBasin = getStringInBetween(singleLine,
					"River Basin</b></td><td>", "</td><td><b>");
			if (riverBasin != null) {
				r.setRiverBasin(riverBasin);
			}
			String county = getStringInBetween(singleLine,
					"County</b></td><td>", "</td></tr>");
			if (county != null) {
				r.setCounty(county);
			}
			String hydrologicArea = getStringInBetween(singleLine,
					"Hydrologic Area</b></td><td>", "</td><td><b>");
			if (hydrologicArea != null) {
				r.setHydrologicArea(hydrologicArea);
			}
			String nearbyCity = getStringInBetween(singleLine,
					"Nearby City</b></td><td>", "</td></tr>");
			if (nearbyCity != null) {
				r.setNearbyCity(nearbyCity);
			}
			String latitude = getStringInBetween(singleLine,
					"Latitude</b></td><td>", "</td><td><b>");
			if (latitude != null) {
				r.setLatitude(latitude);
			}
			String longitude = getStringInBetween(singleLine,
					"Longitude</b></td><td>", "</td></tr>");
			if (longitude != null) {
				r.setLongitude(longitude);
			}
			String operator = getStringInBetween(singleLine,
					"Operator</b></td><td>", "</td><td><b>");
			if (operator != null) {
				r.setOperator(operator);
			}
			String dataCollection = getStringInBetween(singleLine,
					"Data Collection</b></td><td>", "</td></tr>");
			if (dataCollection != null) {
				r.setDataCollection(dataCollection);
			}

		}
		return r;
	}

	private String getStringInBetween(String line, String prefix, String suffix) {
		int firstIndexVal;
		int lstIndexVal;
		firstIndexVal = line.indexOf(prefix);
		lstIndexVal = line.indexOf(suffix, firstIndexVal);
		if (firstIndexVal >= 0) {
			firstIndexVal += prefix.length();
			String value = line.substring(firstIndexVal, lstIndexVal);
			return value;
		}
		return null;
	}

	public String getResponse() {
		return value;
	}

}
