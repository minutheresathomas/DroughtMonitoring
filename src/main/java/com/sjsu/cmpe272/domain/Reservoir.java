package com.sjsu.cmpe272.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Reservoir {
	@JsonProperty
	private String stationId;
	@JsonProperty
	private String stationName;
	@JsonProperty
	private String county;
	@JsonProperty
	private String dataCollection;
	@JsonProperty
	private String operator;
	@JsonProperty
	private String longitude;
	@JsonProperty
	private String latitude;
	@JsonProperty
	private String nearbyCity;
	@JsonProperty
	private String hydrologicArea;
	@JsonProperty
	private Object riverBasin;
	@JsonProperty
	private String elevation;
	@JsonProperty
	private Map<String, Long> storageData;
	
//	private long avgDay;
//	private long avgMonth;
//	private long avgYear;
	
	public long getAvgDay() {
		return avgDay;
	}
	public void setAvgDay() {
		this.avgDay = avgDay;
	}
	public long getAvgMonth() {
		return avgMonth;
	}
	public void setAvgMonth(int month) throws ParseException {
		Iterator it = storageData.keySet().iterator();
		int mon =0 ;
		long value = 0;
		while(it.hasNext())
		{
			String key = (String) it.next();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			date = format.parse(key);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);
			if(month == mon || mon == 0) {
				switch(month)
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
			}
			mon = month;
		}
		this.avgMonth = value;
	}
	public long getAvgYear() {
		return avgYear;
	}
	public void setAvgYear(long avgYear) {
		this.avgYear = avgYear;
	}
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getDataCollection() {
		return dataCollection;
	}
	public void setDataCollection(String dataCollection) {
		this.dataCollection = dataCollection;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getNearbyCity() {
		return nearbyCity;
	}
	public void setNearbyCity(String nearbyCity) {
		this.nearbyCity = nearbyCity;
	}
	public String getHydrologicArea() {
		return hydrologicArea;
	}
	public void setHydrologicArea(String hydrologicArea) {
		this.hydrologicArea = hydrologicArea;
	}
	public Object getRiverBasin() {
		return riverBasin;
	}
	public void setRiverBasin(Object riverBasin) {
		this.riverBasin = riverBasin;
	}
	public String getElevation() {
		return elevation;
	}
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
//	public Map<Long, Long> getStorageData() {
//		return storageData;
//	}
//	public void setStorageData(Map<Long, Long> storageData) {
//		this.storageData = storageData;
//	}
	
	public Map<String, Long> getStorageData() {
		return storageData;
	}
	public void setStorageData(Map<String, Long> storageData) {
		this.storageData = storageData;
	}
	
	
}
