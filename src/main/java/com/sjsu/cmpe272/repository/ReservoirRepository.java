package com.sjsu.cmpe272.repository;


import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicViewportUI;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sjsu.cmpe272.domain.Reservoir;

public class ReservoirRepository implements ReservoirRepositoryInterface{
	MongoClient mongoClient;
	DB db;
	DBCollection collection;
	Map<String, Long> storageData = new HashMap<String, Long>();
	
	public ReservoirRepository() throws UnknownHostException
	{
		mongoClient = new MongoClient();
		db = mongoClient.getDB("mydb");
		collection = db.getCollection("reservoirs");
	}

	public void insertReservoir(Reservoir reservoir) {
		// TODO Auto-generated method stub
		
		
	}

	public List<Reservoir> getReservoirs() {
		List<Reservoir> reservoirs = new ArrayList<Reservoir>();
		BasicDBObject query = new BasicDBObject("storagedata", new BasicDBObject("$ne", null));
		DBCursor cur = collection.find(query);
		try {
		while(cur.hasNext())
		{
			DBObject obj = cur.next();
			Reservoir reservoir = new Reservoir();
			reservoir.setCounty((String) obj.get("county"));
			reservoir.setStationId((String) obj.get("stationId"));
			reservoir.setElevation((String) obj.get("elevation"));
			reservoir.setRiverBasin((String) obj.get("riverBasin"));
			reservoir.setCounty((String) obj.get("hydrologicArea"));
			reservoir.setCounty((String) obj.get("nearbyCity"));
			reservoir.setCounty((String) obj.get("latitude"));
			reservoir.setCounty((String) obj.get("longitude"));
			reservoir.setCounty((String) obj.get("operator"));
			reservoir.setCounty((String) obj.get("stationName"));
			reservoir.setCounty((String) obj.get("dataCollection"));
			reservoir.setStorageData((Map<String, Long>) obj.get("storageData"));
			
			
//			reservoirs.add(obj);
		}
		} finally {
			cur.close();
		}
		return null;
	}
	
	public List<Reservoir> AvgMonth(int year, int month)
	{
		if(year != 0) {
			List<Reservoir> reservoirs = new ArrayList<Reservoir>();
			BasicDBObject query = new BasicDBObject("storagedata", new BasicDBObject("$ne", null));
			DBCursor cur = collection.find(query);
			try {
			while(cur.hasNext())
			{
				DBObject obj = cur.next();
				Reservoir reservoir = new Reservoir();
				storageData = (Map<String, Long>) (obj.get("storageData"));
				Iterator it = storageData.keySet().iterator();
//				int mon =0;
				long value = 0;
				while(it.hasNext())
				{
					String key = (String) it.next();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date;
					date = format.parse(key);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					int currentMonth = cal.get(Calendar.MONTH);
					int currentYear = cal.get(Calendar.YEAR);
					if(month != 0)
					{
						if((currentMonth == month && currentYear == year)) {
							getMonthAvgValue(currentMonth, key, value);
						}
					}
//					mon = currentMonth;
				}
			return 
	}
	
			public long getMonthAvgValue(int currentMonth, String key, long value)
			{
				switch(currentMonth)
				{
				case 01 :  value = value+storageData.get(key); break;
				case 02 : value = value+storageData.get(key); break;
				case 03 : value = value+storageData.get(key); break;
				case 04 : value = value+storageData.get(key); break;
				case 05 : value = value+storageData.get(key); break;
				case 06 : value = value+storageData.get(key); break;
				case 07 : value = value+storageData.get(key); break;
				case 010 : value = value+storageData.get(key); break;
				case 011 : value = value+storageData.get(key); break;
				case 10 : value = value+storageData.get(key); break;
				case 11 : value = value+storageData.get(key); break;
				case 12 : value = value+storageData.get(key); break;
				}
				return value;
			}
			
}
