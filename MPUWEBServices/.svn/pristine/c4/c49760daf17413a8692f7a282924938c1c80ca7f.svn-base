package com.searshc.mpuwebservice.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.bean.ShopInReportDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.processor.impl.MPUWebServiceProcessorImpl;

public class SHCDateUtils {
	private static transient DJLogger logger = DJLoggerFactory.getLogger(SHCDateUtils.class);
	
	public static String calculateEscalationTime(String dateArg,
			HashMap<String, Object> storeInfo) throws ParseException {
		//Any order that comes on or after one hour before closing will be scheduled for next day opening
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone zone = TimeZone
				.getTimeZone((String) storeInfo.get("timeZone"));
		// date.setTimeZone(TimeZone.getTimeZone("America/New_York"));

		// String dateString="2014-01-31 22:03:45";
		date.setTimeZone(zone);
		// SimpleDateFormat date1=new SimpleDateFormat("yyyy-MM-dd");
		// date.
		// Date createime= date.parse(dateString);
		
		Date createTimeLocal=dateLocal.parse(dateArg);
		String dateString = date.format(createTimeLocal).toString();
		int yearField = Integer.parseInt(dateString.substring(0, 4));
		int monthField = Integer.parseInt(dateString.substring(5, 7));
		int dateField = Integer.parseInt(dateString.substring(8, 10));
		
		Calendar calStoreOpen = new GregorianCalendar(yearField,
				monthField - 1, dateField);// ,10,00,00);
		calStoreOpen.setTimeZone(zone);
		int openSeconds=0;
		//logger.info("==storeInfo.get(MpuWebConstants.OPEN)==", storeInfo.get(MpuWebConstants.OPEN));
		if(null!=storeInfo.get(MpuWebConstants.OPEN)){
		 openSeconds = Integer.parseInt((String) ((HashMap<String, Object>) storeInfo
						.get(MpuWebConstants.OPEN)).get(new Integer(calStoreOpen.get(Calendar.DAY_OF_WEEK)).toString()));
		}
		//logger.info("openSeconds==", openSeconds);
		int openHour = openSeconds / 3600;
		int openMinutes = (openSeconds % 3600) / 60;
		calStoreOpen.set(yearField, monthField - 1, dateField, openHour,
				openMinutes, 00);
		Calendar calMidNight = new GregorianCalendar(yearField, monthField - 1,
				dateField + 1, 00, 00, 00);
		calMidNight.setTimeZone(zone);
		Calendar calStoreClose = new GregorianCalendar(yearField,
				monthField - 1, dateField, 20, 00, 00);
		calStoreClose.setTimeZone(zone);
		int closeSeconds=0;
		//logger.info("==storeInfo.get(MpuWebConstants.CLOSE)==", storeInfo.get(MpuWebConstants.CLOSE));
		if(null!=storeInfo.get(MpuWebConstants.CLOSE)){
			closeSeconds = Integer
				.parseInt((String) ((HashMap<String, Object>) storeInfo
						.get(MpuWebConstants.CLOSE)).get(new Integer(calStoreOpen
						.get(Calendar.DAY_OF_WEEK)).toString()));
		}
		//logger.info("closeSeconds==", closeSeconds);
		int closeHour = closeSeconds / 3600;
		int closeMinutes = (closeSeconds % 3600) / 60;
		calStoreClose.set(yearField, monthField - 1, dateField, closeHour-1,
				closeMinutes, 00);
		Calendar calNextStoreOpen = new GregorianCalendar(yearField,
				monthField - 1, dateField + 1, 10, 00, 00);
		calNextStoreOpen.setTimeZone(zone);

		/*
		 * cal.setTimeZone(TimeZone.getTimeZone(ZONE));
		 * 
		 * 
		 * cal.getTimeZone();
		 */
		Date escalationStartTime = null;
		if (createTimeLocal.before(calStoreClose.getTime())
				&& createTimeLocal.after(calStoreOpen.getTime())) {
			escalationStartTime = createTimeLocal;
		} else if (createTimeLocal.after(calStoreClose.getTime())
				&& (createTimeLocal.before(calMidNight.getTime()))) {
			Calendar esCalendar = new GregorianCalendar(yearField,
					monthField - 1, dateField + 1);// ,10,00,00);
			esCalendar.setTimeZone(zone);
			//To reflect proper timings for orders created between midnight and store close
			int openNextSeconds = Integer.parseInt((String)((HashMap<String, Object>)storeInfo .get(MpuWebConstants.OPEN)).get(new Integer(calNextStoreOpen .get(Calendar.DAY_OF_WEEK)).toString()));
			int openHourNext = openNextSeconds / 3600;
			int OpenMinutesNext = (openNextSeconds % 3600) / 60;
			esCalendar.set(yearField, monthField - 1, dateField + 1,
					openHourNext, OpenMinutesNext, 00);
			escalationStartTime = esCalendar.getTime();
		} else if (createTimeLocal.before(calStoreClose.getTime())
				&& createTimeLocal.before(calStoreOpen.getTime())) {
			Calendar esCalendar = new GregorianCalendar(yearField,
					monthField - 1, dateField);
			esCalendar.setTimeZone(zone);

		//	int openSameDaySeconds = Integer.parseInt((String) ((HashMap<String, Object>) storeInfo.get("open")).get(new Integer(calStoreOpen.get(Calendar.DAY_OF_WEEK)).toString()));
			int openSameDayHourNext = openSeconds / 3600;
			int openSameDayMinutesNext = (openSeconds % 3600) / 60;
		
			esCalendar.set(yearField, monthField - 1, dateField,
					openSameDayHourNext, openSameDayMinutesNext, 0);
			escalationStartTime = esCalendar.getTime();
		}

		
	//	date.setTimeZone(TimeZone.getDefault());
		String escalationStartDate = dateLocal.format(escalationStartTime)
				.toString();
		return escalationStartDate;
		/*
		 * cal.get(Calendar.DAY_OF_WEEK); cal.get(5); cal.add(Calendar.DATE,
		 * -1); cal.getTime();
		 */

	}
	public static String convertFromUTCtoLocal(String uTCTime) throws ParseException{
		
		SimpleDateFormat dateUTC=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		dateLocal.setTimeZone(TimeZone.getDefault());
		Date utc=dateUTC.parse(uTCTime);
		return dateLocal.format(utc);
			
			
			
		}
	
public static String convertFromStoreToLocal(long time,String timeZone) throws ParseException{
		
		
		SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		dateLocal.setTimeZone(TimeZone.getDefault());
		
		Date storeTime=new Date(time);	
		
		
		return dateLocal.format(storeTime);
			
			
			
		}
public static String convertFromLocalToStore(String localTime,String timeZone) throws ParseException{
	
	SimpleDateFormat dateStore=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateStore.setTimeZone(TimeZone.getTimeZone(timeZone));
	SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	dateLocal.setTimeZone(TimeZone.getDefault());
	
	
	//String storeTimeString=	dateLocal.format(storeTime);
		Date local=dateLocal.parse(localTime);
	
	return dateStore.format(local);
		
		
		
	}
public static String getDateSubstractFromString(String dateString,int min){
	SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//supports yyyy-mm-dd hh-mm-ss
	int yearField = Integer.parseInt(dateString.substring(0, 4));
	int monthField = Integer.parseInt(dateString.substring(5, 7));
	int dateField = Integer.parseInt(dateString.substring(8, 10));
	int hourField = Integer.parseInt(dateString.substring(11, 13));
	int minutFiled = Integer.parseInt(dateString.substring(14, 16));
	int secondField = Integer.parseInt(dateString.substring(17, 19));

	Calendar cal = new GregorianCalendar(yearField,
			monthField - 1, dateField,hourField,minutFiled-min,secondField);
	
	return date.format(cal.getTime());
}
public static Date getLastStoreClose(HashMap<String,Object> storeInfo){
	
	TimeZone zone = TimeZone
			.getTimeZone((String) storeInfo.get("timeZone"));
	
	
	SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	date.setTimeZone(zone);
	Date currentTime= Calendar.getInstance().getTime();
	String dateString=date.format(currentTime);
	int yearField = Integer.parseInt(dateString.substring(0, 4));
	int monthField = Integer.parseInt(dateString.substring(5, 7));
	int dateField = Integer.parseInt(dateString.substring(8, 10));
//	int hourField = Integer.parseInt(dateString.substring(11, 13));
//	int minutFiled = Integer.parseInt(dateString.substring(14, 16));
//	int secondField = Integer.parseInt(dateString.substring(17, 19));

	Calendar calStoreclose = new GregorianCalendar(yearField,
			monthField - 1, dateField-1);// ,10,00,00);
	calStoreclose.setTimeZone(zone);
	int closeSeconds = Integer
			.parseInt((String) ((HashMap<String, Object>) storeInfo.get(MpuWebConstants.CLOSE)).get(new Integer(calStoreclose.get(Calendar.DAY_OF_WEEK)).toString()));
	int closeHour = closeSeconds / 3600;
	int closeMinutes = (closeSeconds % 3600) / 60;
	calStoreclose.set(yearField, monthField - 1, dateField-1, closeHour,
			closeMinutes, 00);
	//logger.info("the getLastStoreClose time is ", calStoreclose);
	return calStoreclose.getTime();
	
}
public static Date  getStoreCloseHour(HashMap<String,Object> storeInfo){
	//This time reflects the order receiving time	
		TimeZone zone = TimeZone
				.getTimeZone((String) storeInfo.get("timeZone"));
		
		
		SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//	SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date.setTimeZone(zone);
		Date currentTime= Calendar.getInstance().getTime();
		String dateString=date.format(currentTime);
		int yearField = Integer.parseInt(dateString.substring(0, 4));
		int monthField = Integer.parseInt(dateString.substring(5, 7));
		int dateField = Integer.parseInt(dateString.substring(8, 10));
	//	int hourField = Integer.parseInt(dateString.substring(11, 13));
	//	int minutFiled = Integer.parseInt(dateString.substring(14, 16));
	//	int secondField = Integer.parseInt(dateString.substring(17, 19));

		Calendar calStoreclose = new GregorianCalendar(yearField,
				monthField - 1, dateField);// ,10,070,00);
		calStoreclose.setTimeZone(zone);
		int closeSeconds = Integer
				.parseInt((String) ((HashMap<String, Object>) storeInfo
						.get(MpuWebConstants.CLOSE)).get(new Integer(calStoreclose
						.get(Calendar.DAY_OF_WEEK)).toString()));
		int closeHour = closeSeconds / 3600;
		int closeMinutes = (closeSeconds % 3600) / 60;
		calStoreclose.set(yearField, monthField - 1, dateField, closeHour,
				closeMinutes, 00);
		//logger.info("the store close hopur is ", calStoreclose);
		return calStoreclose.getTime();

}
public static Date  getStoreOpenHour(HashMap<String,Object> storeInfo){
	
	TimeZone zone = TimeZone
			.getTimeZone((String) storeInfo.get("timeZone"));
	
	
	SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//SimpleDateFormat dateLocal=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	date.setTimeZone(zone);
	
	Date currentTime= Calendar.getInstance().getTime();
	String dateString=date.format(currentTime);
	int yearField = Integer.parseInt(dateString.substring(0, 4));
	int monthField = Integer.parseInt(dateString.substring(5, 7));
	int dateField = Integer.parseInt(dateString.substring(8, 10));
	//int hourField = Integer.parseInt(dateString.substring(11, 13));
//	int minutFiled = Integer.parseInt(dateString.substring(14, 16));
//	int secondField = Integer.parseInt(dateString.substring(17, 19));

	Calendar calStoreOpen = new GregorianCalendar(yearField,
			monthField - 1, dateField);// ,10,00,00);
	calStoreOpen.setTimeZone(zone);
	
	int openSeconds = Integer
			.parseInt((String) ((HashMap<String, Object>) storeInfo
					.get(MpuWebConstants.OPEN)).get(new Integer(calStoreOpen
					.get(Calendar.DAY_OF_WEEK)).toString()));
	int openHour = openSeconds / 3600;
	int openMinutes = (openSeconds % 3600) / 60;
	calStoreOpen.set(yearField, monthField - 1, dateField, openHour,
			openMinutes, 00);
	return calStoreOpen.getTime();

}

	public static boolean isStoreOpen(HashMap<String,Object> storeInfo){
		
		
		if(SHCDateUtils.getStoreCloseHour(storeInfo).after(Calendar.getInstance().getTime())&&getStoreOpenHour(storeInfo).before(Calendar.getInstance().getTime())){
			return true;	
		}
		else{
			return false;
		}
	}
	
	public static int getDateDiff(Date end,Date start) throws ParseException{
		
		return Days.daysBetween(new DateTime(end).withTimeAtStartOfDay(), new DateTime(start).withTimeAtStartOfDay()).getDays();	
		
	}

	public static String getElapsedTimeHoursMinutesSecondsString(long elapsedTime) {       
	    String format = String.format("%%0%dd", 2);  
	    elapsedTime = elapsedTime / 1000;  
	    String seconds = String.format(format, elapsedTime % 60);  
	    String minutes = String.format(format, (elapsedTime % 3600) / 60);  
	    String hours = String.format(format, elapsedTime / 3600);  
	    String time =  hours + ":" + minutes + ":" + seconds;  
	    return time;  
	}
	
	public static String getElapsedTimeHoursMinutesSeconds(long elapsedTime) { 
	    String format = String.format("%%0%dd", 2);  
	    elapsedTime = elapsedTime / 1000;  
	    String seconds = String.format(format, elapsedTime % 60);  
	    String minutes = String.format(format, (elapsedTime % 3600) / 60);  
	    String hours = String.format(format, elapsedTime / 3600);  
	    return (hours + ":" + minutes + ":" + seconds);  
	}
    
    public static Date getDateFromString(String dateString){
		
		int yearField = Integer.parseInt(dateString.substring(0, 4));
		int monthField = Integer.parseInt(dateString.substring(5, 7));
		int dateField = Integer.parseInt(dateString.substring(8, 10));
		int hourField = Integer.parseInt(dateString.substring(11, 13));
		int minutFiled = Integer.parseInt(dateString.substring(14, 16));
		int secondField = Integer.parseInt(dateString.substring(17, 19));

		Calendar cal = new GregorianCalendar(yearField,
				monthField - 1, dateField,hourField,minutFiled,secondField);
		
		
		return cal.getTime();
		
	}
	
	public static String getFormatedDate(String dateStr,String formFormat,String toFormat){
		SimpleDateFormat readFormat=new SimpleDateFormat(formFormat);
		SimpleDateFormat writeFormat = new SimpleDateFormat(toFormat);
	    String formattedDate = "";
		try {
			Date date1 = readFormat.parse(dateStr);
			if( date1 != null ) {
				formattedDate = writeFormat.format( date1 );
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}
	
	public static Timestamp  convertToDate(String date){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		Date parsedDate = null;
		try {
			parsedDate = dateFormat.parse(date);
		} catch (ParseException e) {		
			e.printStackTrace();
		}		
		Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		return  timestamp;
	}  
	
	public static String notDlvrReportDateFormat(String date){
		String formattedDate="";
		try{
			SimpleDateFormat oldFormatter =new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat sdf =new SimpleDateFormat("dd-MM-yyyy");
			Date nDate=oldFormatter.parse(date);
			formattedDate=sdf.format(nDate);
		}catch(Exception exception){}		
		return formattedDate;		
	}
	
	public static String shopinReportDateFormat(String date){
		String formattedDate="";
		try{
			SimpleDateFormat oldFormatter =new SimpleDateFormat("MM-dd-yyyy");
			Date nDate=oldFormatter.parse(date);
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			formattedDate=sdf.format(nDate);
		}catch(Exception exception){			
		}		
		return formattedDate;		
	}	
}
