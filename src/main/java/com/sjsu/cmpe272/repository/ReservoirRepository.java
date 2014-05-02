package com.sjsu.cmpe272.repository;


import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.plaf.basic.BasicViewportUI;

import org.joda.time.DateTime;
import org.joda.time.Days;

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
	ConcurrentHashMap<String, Long> pastThirtyDays = new ConcurrentHashMap<String, Long>();
	
	public ReservoirRepository() throws UnknownHostException
	{
		mongoClient = null;
		db = null;
		collection = null;
	}

	public void insertReservoir(Reservoir reservoir) {
		// TODO Auto-generated method stub
		
		
	}

	public List<Reservoir> getReservoirs() {
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("mydb");
			collection = db.getCollection("reservoirs");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
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
	
//	public void insertAverage()
//	{
//		try{
//			mongoClient=new MongoClient("localhost",27017);
//			db=mongoClient.getDB("mydb");
//			}catch(Exception e){
//			e.printStackTrace();
//			}
//		collection = 
//	}
	
	public ConcurrentHashMap<String, Long> caPastThirtyDays() throws ParseException
	{
		try {
			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("mydb");
			collection = db.getCollection("reservoirs");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Calling past 30");
//		long num_reservoirs = 0;
//		HashMap<String, Long> returnPastThirtyDays = new HashMap<String, Long> ();
		
		BasicDBObject query = new BasicDBObject();
		query.put("storageData", new BasicDBObject("$ne", null));
		DBCursor cur = collection.find();
//		num_reservoirs=cur.count();
		
		int num_reservoirs= 0;
		try {
			while(cur.hasNext())
			{
				long ca = 0;
				DBObject obj = cur.next();
				num_reservoirs++;
				storageData = (Map<String, Long>) (obj.get("storageData"));
				Iterator it = storageData.keySet().iterator();
				String station = (String) obj.get("stationId");
				while(it.hasNext())
				{
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date sysdate = new Date();
					String stringdate = dateFormat.format(sysdate);
					Date currentDate = dateFormat.parse(stringdate);
					System.out.println("Current date is : "+currentDate);
					
					String key = (String) it.next();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date;
					date = format.parse(key);
					
					DateTime currentDateTime = new DateTime(currentDate.getTime());
					DateTime dateDateTime = new DateTime(date.getTime());
					
					Days d = Days.daysBetween(currentDateTime, dateDateTime);
					int days = d.getDays();
					if(days <= 30)
					{
						ca++;
						System.out.println("record date is : "+date);
						String dayKey = station+":"+key;
						pastThirtyDays.put(dayKey, storageData.get(key));
					}
				}
				System.out.println("number of dates : "+ca + " from station :"+station);
//				Iterator itDays = pastThirtyDays.keySet().iterator();
//				Iterator itDays1 = pastThirtyDays.keySet().iterator();
				long vlue;
				for(Iterator<String> itDays = pastThirtyDays.keySet().iterator() ; itDays.hasNext();)
				{
					for(Iterator<String> itDays1 = pastThirtyDays.keySet().iterator();itDays1.hasNext();)
					{
						String keyDays = (String) itDays.next();
						String[] parts = keyDays.split(":");
						String keyDays1 = (String) itDays1.next();
						System.out.println("Inside the iterator");
						String[] parts1 = keyDays1.split(":");
						System.out.println("parts[1] ===== :" +parts[1]+" from Station -- "+parts[0]);
						System.out.println("parts1[1] ====== : "+parts1[1]+" from Station -- "+parts1[0]);
						if(( parts[0] != parts1[0]) && (parts[1] == parts1[1]))
						{
							vlue = (pastThirtyDays.get(keyDays) + pastThirtyDays.get(keyDays1))/num_reservoirs;
							pastThirtyDays.put(keyDays, vlue);
							System.out.println("Inside the iterator- removing");
							pastThirtyDays.remove(keyDays1);
							break;
						}
					}
				}
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(date);
//					int currentMonth = cal.get(Calendar.MONTH);
//					int currentYear = cal.get(Calendar.YEAR);	
				}
			}
		finally
		{
			cur.close();
		}
		mongoClient.close();
		return pastThirtyDays;
	}
	
	public List<Long> AvgMonth(String station_id, int year, int month) throws ParseException
	{
		List<Long> avgMonths = new ArrayList<Long>();
		List<Long> avgYrs = new ArrayList<Long>();
		if(year != 2014 && month != 0) {
//			List<Reservoir> reservoirs = new ArrayList<Reservoir>();
			BasicDBObject query = new BasicDBObject("storagedata", new BasicDBObject("$ne", null));
			DBCursor cur = collection.find(query);
			long caAvg = 0;
			int num_reservoirs= 0;
			try {
				while(cur.hasNext())
				{
					DBObject obj = cur.next();
					Reservoir reservoir = new Reservoir();
					storageData = (Map<String, Long>) (obj.get("storageData"));
					Iterator it = storageData.keySet().iterator();
					int num_days =0;
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
							if((currentMonth == month && currentYear == year)) {
								avgMonths.add(storageData.get(key));
//								value = value+getMonthAvgValue(currentMonth, key, value);
								num_days++;
							}
					}
					caAvg = caAvg+value;
					avgMonths.add(value/num_days);
				}
				return avgMonths;
			}
			finally
			{
				cur.close();
			}
		}
		else if(month == 0 && year != 2014)
		{
			BasicDBObject query = new BasicDBObject("storagedata", new BasicDBObject("$ne", null));
			DBCursor cur = collection.find(query);
			try {
				while(cur.hasNext())
				{
					DBObject obj = cur.next();
					Reservoir reservoir = new Reservoir();
					storageData = (Map<String, Long>) (obj.get("storageData"));
					Iterator it = storageData.keySet().iterator();
					int num_months =0;
					long value = 0;
					while(it.hasNext())
					{
						String key = (String) it.next();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						Date date;
						date = format.parse(key);
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						int currentYear = cal.get(Calendar.YEAR);
							if((currentYear == year)) {
								value = value+getYearAvgValue(currentYear, key, value);
								num_months++;
							}
					}
					avgYrs.add(value/num_months);
				}
				
			}
			finally
			{
				cur.close();
			}
			return avgYrs;
		}
		return null;
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
			
			public long getYearAvgValue(int currentYear, String key, long value)
			{
				{
					switch(currentYear)
					{
						case 2010 :  value = value+storageData.get(key); break;
						case 2011 : value = value+storageData.get(key); break;
						case 2012 : value = value+storageData.get(key); break;
						case 2013 : value = value+storageData.get(key); break;
						case 2014 : value = value+storageData.get(key); break;
					}
					return value;
				}
			}
}
