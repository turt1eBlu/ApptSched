
package utils;

import java.time.ZonedDateTime;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for converting between time data types and zones.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class TimeHandler {
    
    private static final ZoneId DB_TZ = ZoneId.of("UTC");           //database time zone (UTC)
    private static final ZoneId LOC_TZ = ZoneId.systemDefault();    //local user time zone
    public static final ZoneId OFC_TZ = ZoneOffset.of("-0500");           //office time zone (EST)
    public static final LocalTime StartOfficeHour = LocalTime.of(8, 00);
    public static final LocalTime EndOfficeHour = LocalTime.of(20, 00);
    
    /**
     * Converts SQL database Timestamp data type to ZonedDateTime data type.
     * @param dbTime time value retrieved from database
     * @return time value converted to ZonedDateTime
     */
    public static ZonedDateTime dt2zdt(Timestamp dbTime){
        return dbTime.toLocalDateTime().atZone(LOC_TZ);
    }
    
    /**
     * Formats a ZonedDateTime as a string of pattern MM/dd/yyyy - HH:mm:ss and 
     * the time zone.
     * @param zdt ZonedDateTime to format
     * @return string representation in format MM/dd/yyyy - HH:mm:ss z
     */
    public static String zdt2String(ZonedDateTime zdt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        String formattedString = zdt.format(formatter);
        return formattedString;
    }
    
    /**
     * Converts ZonedDateTime data type to SQL database Timestamp data type.
     * @param zdt ZonedDateTime value
     * @return ZonedDateTime value converted to SQL Timestamp
     */
    public static Timestamp zdt2ts(ZonedDateTime zdt){
        return Timestamp.valueOf(zdt.toLocalDateTime());
    }
    
    /**
     * Determines whether a proposed appointment time is within the set office
     * hour time.
     * @param start the appointment starting time
     * @param end the appointment ending time
     * @return is the proposed appointment time within office hours
     */
    public static boolean withinOfficeHours(ZonedDateTime start, ZonedDateTime end){
        
        boolean validApptTime = false;
 
        //convert start and end to office time zone
        ZonedDateTime apptStart = start.withZoneSameInstant(OFC_TZ);
        ZonedDateTime apptEnd = end.withZoneSameInstant(OFC_TZ);
        
        //use date from start and time from StartOfficeHour to make ZonedDateTime officeOpen
        LocalDate apptDate = apptStart.toLocalDate();
        ZonedDateTime officeOpen = ZonedDateTime.of(apptDate, StartOfficeHour,OFC_TZ);
        
        //use date from start and time from EndOfficeHour to make ZonedDateTime officeClose
        ZonedDateTime officeClose = ZonedDateTime.of(apptDate, EndOfficeHour,OFC_TZ);
        
        //check officeOpen <= appointment start <= officeClose
        boolean startAfterOpen = (apptStart.isAfter(officeOpen) || apptStart.isEqual(officeOpen));
        boolean startBeforeClose = apptStart.isBefore(officeClose);
        if (startAfterOpen && startBeforeClose){
            //check appointment end <= officeClose
            boolean endWhileOpen = apptEnd.isBefore(officeClose) || apptEnd.isEqual(officeClose);
            if (endWhileOpen)
                validApptTime = true;
        }
        return validApptTime;
    }

}
