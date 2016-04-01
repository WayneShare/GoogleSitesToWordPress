package com.wayneshare.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

	public static String getTime_MMddHHMMss() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd_HH:mm:ss_");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getTime_MMdd_HHMM_ssSSS() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd_HHmm_ssSSS");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getTime_MMddYYYY() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/YYYY");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static Date getDate_MMddYYYY(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, YYYY"); 
		// Sep 4, 2013
		Date now = new Date();
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTime_MMdd_HHMM_ss() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("MMdd_HHmmss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

}
