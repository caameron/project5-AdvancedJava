package edu.pdx.cs410j.caameron.myapplication;

import edu.pdx.cs410J.AbstractAppointment;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Comparable;

/**
 * Appointment class which will represent a single appointment and some of the details that belong with it
 * This will include a start date and time, a end date and time, and a brief description about the appointment
 * This class extends AbstractAppointment. All data members of the class are represented using Strings. For now
 * the class only sets all the data in the constructor and uses methods to get the values when needed.
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment>, Serializable {
  private static final long serialVersionUID = 808L;

  //Data members for Appointment class, will have variables for
  //start, end and description
  private String startDate;
  private String endDate;
  private String endTime;
  private String startTime;
  private String description;
  private String startTimeOfDay;
  private String endTimeOfDay;
  private Date startTimeDate;
  private Date endTimeDate;
  private int yearB;
  private int monthB;
  private int dayB;
  private int hourB;
  private int minuteB;
  private int yearE;
  private int monthE;
  private int dayE;
  private int hourE;
  private int minuteE;

  //Constructor for Appointment Class. The class should take in three arguments
  //A beginning time, ending time, and a description.

  /**
   * Creates an instance of the Appointment class with a description, start time and end time. Constructor
   * also checks for errors in the start and end time by making sure they are in the correct format. A
   * regular expression is used to check for incorrect formatting. The description is also checked to make
   * sure that a null or empty String has been passed in.
   * Also takes in the dates and times and converts them to Date objects
   * @param startDate
   *        The date at which the appointment is scheduled to begin. Format: mm/dd/yyyy
   * @param startTime
   *        The time of day at which the appointment is scheduled to begin. Format: hh:mm
   * @param endDate
   *        The date at which the appointment is scheduled to end. Format: mm/dd/yyyy
   * @param endTime
   *        The time of day at which the appointment is scheduled to end. Format: hh:mm
   * @param description
   *        Description about the appointment being made.
   * @param startTimeOfDay
   *        am or pm of start time
   * @param endTimeOfDay
   *        am or pm of end time
   *
   */
  public Appointment(String startDate, String startTime, String endDate, String endTime, String description, String startTimeOfDay, String endTimeOfDay) throws Exception {
    //Check that the times and dates are in the correct format mm/dd/yyyy
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.startTimeOfDay = startTimeOfDay;
    this.endTimeOfDay = endTimeOfDay;


    String startAppt = startDate + " " + startTime;
    String endAppt = endDate + " " + endTime;

    //Check that description is not empty or null using regex
    String regEx = "^[0-9]?[0-9]/[0-9]?[0-9]/([0-9]{4}) [0-1]?[0-9]:[0-6][0-9]$";
    Pattern pattern = Pattern.compile(regEx);
    Matcher matcher = pattern.matcher(startAppt);


    String[] splitDate = startDate.split("/");
    String[] splitTime = startTime.split(":");
    String[] splitDateEnd = endDate.split("/");
    String[] splitTimeEnd = endTime.split(":");
    int year, month, date, hrs, min;
    int yearEnd, monthEnd, dateEnd, hrsEnd, minEnd;
    year = Integer.parseInt(splitDate[2]);
    month = Integer.parseInt(splitDate[0]);
    date = Integer.parseInt(splitDate[1]);
    hrs = Integer.parseInt(splitTime[0]);
    min = Integer.parseInt(splitTime[1]);
    yearEnd = Integer.parseInt(splitDateEnd[2]);
    monthEnd = Integer.parseInt(splitDateEnd[0]);
    dateEnd = Integer.parseInt(splitDateEnd[1]);
    hrsEnd = Integer.parseInt(splitTimeEnd[0]);
    minEnd = Integer.parseInt(splitTimeEnd[1]);

    if(month > 12 || monthEnd > 12)
    {
      throw new Exception("Month cannot be greater than 12");
    }
    if(date > 31 || dateEnd > 31)
    {
      throw new Exception("Days cannot be greater than 31");
    }
    if(hrs > 12 || hrsEnd > 12)
    {
      throw new Exception("Time format should be in hh:mm am/pm. Hours greater than 12 not allowed");
    }
    if(min > 59 || minEnd > 59)
    {
      throw new Exception("Minutes cannot be greater than 59");
    }

    if( matcher.matches() == false)
    {
      throw new Exception("Date and Time format incorrect. Must be of format dd/mm/yyyy hh:mm");
    }

    matcher = pattern.matcher(endAppt);
    if( matcher.matches() == false)
    {
      throw new Exception("Date and Time format incorrect. Must be of format dd/mm/yyyy hh:mm");
    }

    //Check that it is am or pm
    if(!(startTimeOfDay.toLowerCase().equals("am")) && !(startTimeOfDay.toLowerCase().equals("pm")))
    {
      throw new Exception("Start time of day is not am or pm. Please Specify 'am' or 'pm'");
    }

    if(!(endTimeOfDay.toLowerCase().equals("am")) && !(endTimeOfDay.toLowerCase().equals("pm")))
    {
      throw new Exception("End time of day is not am or pm. Please Specify 'am' or 'pm'");
    }

    //Set date and time to Date objects
    //Convert to ints

    this.yearB = year;
    this.monthB = month;
    this.dayB = date;
    this.hourB = hrs;
    this.minuteB = min;
    Calendar calStart = Calendar.getInstance();
    calStart.set(Calendar.YEAR, year);
    calStart.set(Calendar.MONTH, month-1);
    calStart.set(Calendar.DAY_OF_MONTH, date);
    calStart.set(Calendar.HOUR, hrs);
    calStart.set(Calendar.MINUTE, min);
    if(startTimeOfDay.toLowerCase().equals("am") == true)
    {
      if(hrs == 12)
      {
        calStart.set(Calendar.AM_PM, Calendar.PM);
      }
      else {
        calStart.set(Calendar.AM_PM, Calendar.AM);
      }
    }
    else
    {
      if(hrs == 12)
      {
        calStart.set(Calendar.AM_PM, Calendar.AM);
      }
      else {
        calStart.set(Calendar.AM_PM, Calendar.PM);
      }
    }


    year = Integer.parseInt(splitDateEnd[2]);
    month = Integer.parseInt(splitDateEnd[0]);
    date = Integer.parseInt(splitDateEnd[1]);
    hrs = Integer.parseInt(splitTimeEnd[0]);
    min = Integer.parseInt(splitTimeEnd[1]);
    this.yearE = year;
    this.monthE = month;
    this.dayE = date;
    this.hourE = hrs;
    this.minuteE = min;
    Calendar calEnd = Calendar.getInstance();
    calEnd.set(Calendar.YEAR, year);
    calEnd.set(Calendar.MONTH, month-1);
    calEnd.set(Calendar.DAY_OF_MONTH, date);
    calEnd.set(Calendar.HOUR, hrs);
    calEnd.set(Calendar.MINUTE, min);
    if(endTimeOfDay.toLowerCase().equals("am") == true)
    {
      if(hrs == 12)
      {
        calEnd.set(Calendar.AM_PM, Calendar.PM);
      }
      else {
        calEnd.set(Calendar.AM_PM, Calendar.AM);
      }
    }
    else
    {
      if(hrs == 12)
      {
        calEnd.set(Calendar.AM_PM, Calendar.AM);
      }
      else {
        calEnd.set(Calendar.AM_PM, Calendar.PM);
      }
    }

    startTimeDate = calStart.getTime();
    endTimeDate = calEnd.getTime();
    if(startTimeDate.compareTo(endTimeDate) > 0)
    {
      throw new Exception("Start time of appointment cannot be after end time");
    }
//    System.out.println(startTimeDate);
//    System.out.println(endTimeDate);
//    System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(startTimeDate));
//    System.out.println(DateFormat.getTimeInstance(DateFormat.SHORT).format(startTimeDate));
//    System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).format(endTimeDate));
//    System.out.println(DateFormat.getTimeInstance(DateFormat.SHORT).format(endTimeDate));

    if(description == null || description.isEmpty() == true || startDate.isEmpty() == true || startTime.isEmpty() == true || endDate.isEmpty() == true || endTime.isEmpty() == true)
    {
      throw new Exception("Description cannot be empty or null");
    }
    this.description = description;


  }

  /**
   * Method to return the  beginning time and date of the appointment using DateFormat.SHORT
   * @return Returns the startDate and startTime concatenated
   */
  @Override
  public String getBeginTimeString() {
    String returnString = DateFormat.getDateInstance(DateFormat.SHORT).format(startTimeDate) + " " + DateFormat.getTimeInstance(DateFormat.SHORT).format(startTimeDate);
    return returnString;
  }

  /**
   * Method to return the ending time and date of the appointment using DateFormat.SHORT
   * @return Returns the endDate and endTime concatenated
   */
  @Override
  public String getEndTimeString() {
    String returnString = DateFormat.getDateInstance(DateFormat.SHORT).format(endTimeDate)+ " " + DateFormat.getTimeInstance(DateFormat.SHORT).format(endTimeDate);
    return returnString;
  }

  /**
   * Method to return the description of the appointment
   * @return Returns the description member of the class
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * Method to return the beginning time in a Date object
   * @return Returns Data object of beginning time
   */
  @Override
  public Date getBeginTime() {
    return startTimeDate;
  }

  /**
   * Method to return the ending time in a Date object
   * @return Returns Date object of ending time
   */
  @Override
  public Date getEndTime() {
    return endTimeDate;
  }

  /**
   * Method to return beginning time in a format for the text file
   * @return Returns String of beginning time
   */
  public String getBeginTimeFile() {
    return startDate + " " + startTime + " " + startTimeOfDay;
  }

  /**
   * Method to return ending time in a format for the text file
   * @return Returns String of ending time
   */
  public String getEndTimeFile() {
    return endDate + " " + endTime + " " + endTimeOfDay;
  }

  /**
   * Method to compare one appointment to another in order to sort by chronologically/lexigraphically.
   * @param o Appointment to be compared
   * @return int that will represent if appointment is before, after, or equal to another appointment
   *         -1 : before appointment
   *         1  : after appointment
   *         0  : same time and description
   */
  @Override
  public int compareTo(Appointment o) {
//    int comparison = startTimeDate.compareTo(o.getBeginTime());
    String beginTimeC;
    String beginDateC;
    String beginTimeOfDayC;
    String endTimeC;
    String endDateC;
    String endTimeOfDayC;

    String [] begSplit = o.getBeginTimeFile().split(" ");
    String [] endSplit = o.getEndTimeFile().split(" ");

    beginDateC = begSplit[0];
    beginTimeC = begSplit[1];
    beginTimeOfDayC = begSplit[2];
    endDateC = endSplit[0];
    endTimeC = endSplit[1];
    endTimeOfDayC = endSplit[2];

    String[] splitDate = beginDateC.split("/");
    String[] splitTime = beginTimeC.split(":");
    int year, month, date, hrs, min;
    year = Integer.parseInt(splitDate[2]);
    month = Integer.parseInt(splitDate[0]);
    date = Integer.parseInt(splitDate[1]);
    hrs = Integer.parseInt(splitTime[0]);
    min = Integer.parseInt(splitTime[1]);

    String[] splitDateE = endDateC.split("/");
    String[] splitTimeE = endTimeC.split(":");
    int yearEnd, monthEnd, dateEnd, hrsEnd, minEnd;
    yearEnd = Integer.parseInt(splitDateE[2]);
    monthEnd = Integer.parseInt(splitDateE[0]);
    dateEnd = Integer.parseInt(splitDateE[1]);
    hrsEnd = Integer.parseInt(splitTimeE[0]);
    minEnd = Integer.parseInt(splitTimeE[1]);


    //CHECK BEGINNING FIRST
    if(this.yearB > year) {
      return 1;
    }
    if(this.yearB < year){
      return -1;
    }
    if(this.monthB > month) {
      return 1;
    }
    if(this.monthB < month){
      return -1;
    }
    if(this.dayB > date) {
      return 1;
    }
    if(this.dayB < date){
      return -1;
    }
    if(this.startTimeOfDay.toLowerCase().equals("pm") && beginTimeOfDayC.toLowerCase().equals("am"))
    {
      return 1;
    }
    if(this.startTimeOfDay.toLowerCase().equals("am") && beginTimeOfDayC.toLowerCase().equals("pm"))
    {
      return -1;
    }
    if(this.hourB > hrs){
      return 1;
    }
    if(this.hourB < hrs){
      return -1;
    }
    if(this.minuteB > min){
      return 1;
    }
    if(this.minuteB < min){
      return -1;
    }
    //ENDING DATE
    if(this.yearE > yearEnd) {
      return 1;
    }
    if(this.yearE < yearEnd){
      return -1;
    }
    if(this.monthE > monthEnd) {
      return 1;
    }
    if(this.monthE < monthEnd){
      return -1;
    }
    if(this.dayE > dateEnd) {
      return 1;
    }
    if(this.dayE < dateEnd){
      return -1;
    }

    if(this.endTimeOfDay.toLowerCase().equals("pm") && endTimeOfDayC.toLowerCase().equals("am"))
    {
      return 1;
    }
    if(this.endTimeOfDay.toLowerCase().equals("am") && endTimeOfDayC.toLowerCase().equals("pm"))
    {
      return -1;
    }
    if(this.hourE > hrsEnd){
      return 1;
    }
    if(this.hourE < hrsEnd){
      return -1;
    }
    if(this.minuteE > minEnd){
      return 1;
    }
    if(this.minuteE < minEnd){
      return -1;
    }
    return this.description.compareTo(o.getDescription());
  }
}

