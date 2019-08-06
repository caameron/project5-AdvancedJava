package edu.pdx.cs410j.caameron.myapplication;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {
    /**
     * Main Method for Project 4 Handles all parsing and invoking of REST client
     */
    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * Main Method takes in arguments from the command line. Handles all command line parsing and invoking the
     * REST client with the correct arguments and options
     * @param args
     */
    public static void main(String... args) {
        String hostName = null;
        String portString = null;

        //Parse the arguments sent in into arguments for the program and options (options start with '-')
        List arguments = new ArrayList();
        List options = new ArrayList();
        boolean stopOptionflag = true;
        boolean getNextHost = false;
        boolean printAndWrite = false;
        boolean getNextPort = false;
        boolean prettyWrite = false;
        boolean searchFlag = false;
        boolean justPrint = false;
        for (String arg : args) {
            if (arg.isEmpty() == true) {
                System.err.println("Found empty argument, arguments cannot be empty or null.");
                System.exit(1);
            }

            if (getNextHost == true) {
                getNextHost = false;
                hostName = arg;
                continue;
            }

            if (getNextPort == true) {
                getNextPort = false;
                portString = arg;
                continue;
            }

            if (arg.charAt(0) == '-' && stopOptionflag == true) {
                options.add(arg);
                if (arg.equals("-host")) {
                    printAndWrite = true;
                    getNextHost = true;
                } else if (arg.equals("-port")) {
                    prettyWrite = true;
                    getNextPort = true;
                } else if (arg.equals("-search")) {
                    searchFlag = true;
                }
            } else if (getNextHost == true) {
                getNextHost = false;
            } else if (getNextPort == true) {
                getNextPort = false;
            } else {
                arguments.add(arg);
                stopOptionflag = false;
            }
        }



        //Check if the README flag is there if it is print README and end program
        for (Object option : options) {
            if (option.toString().equals("-README")) {
                System.out.println("Caameron Nakasone\nAdvanced Programming in Java\nProject 4: A REST-ful Appointment Book Web Service\n" +
                        "Project 4 will take in arguments from the command line to create an Appointment Book and an Appointment\n" +
                        "that will be automatically added to the created Appointment Book. The arguments include the name of the owner for the\n" +
                        "appointment book and details about the appointment which will be added (description, start date/time and end date/time\n" +
                        "There are now host and port options which can specify a host and port for the project to make get and post requests to the server\n" +
                        "The server code is in the AppointmentBookServlet.java file and will allow you to create appointments and appointment books on the server\n" +
                        "You can then get all the appointments that will be formatted using PrettyPrinter, and there will also be an option to search for\n" +
                        "specific appointments within a time period\n" +
                        "To create an appointment on the server the arguments are the same. However to get all appointments of a certain book only the owner\n" +
                        "is needed and to search only the owner of the book and the start and end dates are needed.\n" +
                        "There are also README and print options which you may choose to invoke. The README brings up this message and the print\n" +
                        "option will print the appointment that has just been added to the standard output. No fields can be empty from the command\n" +
                        "line and the dates and times must be formatted correctly for the program to run. If you have entered in something\n" +
                        "incorrectly an error message will be printed out and the program will exit.\n");
                System.exit(0);
            }
        }

        for (Object option : options) {
            if (!(option.toString().equals("-README")) && !(option.toString().equals("-print")) && !(option.toString().equals("-host")) && !(option.toString().equals("-port")) && !(option.toString().equals("-search"))) {
                System.err.println("Option not recognized. Available options are : -README  -print -host <host> -port <port> -search");
                System.exit(1);
            }
        }

        String owner, description = null, beginDate = null, beginTime = null, beginTimeOfDay = null, endDate = null, endTime = null, endTimeOfDay = null;


        //Check that the correct amount of arguments have been passed in  keeping in mind that they can add the options tag if they
        //Choose to do so.
        if (searchFlag == true) {
            if (arguments.size() != 7) {
                System.err.println("Did not give correct arguments to command line with search option active. There should be 7 arguments - owner, beginDate, beginTime, begin(am or pm), endDate, endTime, end(am or pm)");
                System.exit(1);
            }
            owner = (arguments.get(0)).toString();
            beginDate = (arguments.get(1)).toString();
            beginTime = (arguments.get(2)).toString();
            beginTimeOfDay = (arguments.get(3).toString());
            endDate = (arguments.get(4)).toString();
            endTime = (arguments.get(5)).toString();
            endTimeOfDay = (arguments.get(6).toString());
        }
        else if(hostName != null && portString != null && arguments.size() == 1)
        {
            owner = (arguments.get(0).toString());
            justPrint = true;
        }
        else
        {
            {
                if (arguments.size() != 8) {
                    System.err.println("Did not give the correct arguments to command line. There must be a 8 arguments - owner, description, beginDate, beginTime, begin(am or pm), endDate, endTime, end(am or pm) if you want to add an appointment");
                    System.err.println("7 arguments if you wish to wish to search for appointments in a given time period - owner, beginDate, beginTime, begin(am or pm), endDate, endTime, end(am or pm)");
                    System.err.println("or 1 argument if you wish to print the contents of that appointment book - owner\n" +
                            "for search and print contents of appointment book, the port and host options need to be specified");
                    System.exit(1);
                }

                owner = (arguments.get(0)).toString();
                description = (arguments.get(1)).toString();
                beginDate = (arguments.get(2)).toString();
                beginTime = (arguments.get(3)).toString();
                beginTimeOfDay = (arguments.get(4).toString());
                endDate = (arguments.get(5)).toString();
                endTime = (arguments.get(6)).toString();
                endTimeOfDay = (arguments.get(7).toString());
            }
        }


        if(justPrint == false) {
            try {
                String[] splitB = beginDate.split("/");
                String[] splitE = endDate.split("/");
                int monthB = Integer.parseInt(splitB[0]);
                int monthE = Integer.parseInt(splitE[0]);
                if (monthB > 12 || monthE > 12) {
                    System.err.println("Invalid Date entered. Month cannot be greater than 12.");
                    System.exit(1);
                }

                try {
                    int dayB = Integer.parseInt(splitB[1]);
                    int dayE = Integer.parseInt(splitE[1]);
                    if (dayB > 31 || dayE > 31) {
                        System.err.println("Invalid Day entered. Day cannot be greater than 31.");
                        System.exit(1);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Date incorrect format. Format is mm/dd/yyyy");
                }

                String[] splitTimeB = beginTime.split(":");
                String[] splitTimeE = endTime.split(":");
                try {
                    int minutesB = Integer.parseInt(splitTimeB[1]);
                    int minutesE = Integer.parseInt(splitTimeE[1]);
                    if (minutesB > 60 || minutesE > 60) {
                        System.err.println("Invalid Time entered. Minutes cannot be more than 60");
                        System.exit(1);
                    }
                } catch (ArrayIndexOutOfBoundsException er) {
                    System.err.println("Time incorrect format. Format is hh:mm");
                }
                int hoursB = Integer.parseInt(splitTimeB[0]);
                int hoursE = Integer.parseInt(splitTimeE[0]);
                if (hoursB > 12 || hoursE > 12) {
                    System.err.println("Invalid Time entered. Hours cannot be more than 12");
                    System.exit(1);
                }
            } catch (NumberFormatException e) {
                System.err.println("Date and Time has to be in integers not strings. Ex) 12/12/1212\n" +
                        "July 14th 1994 is not a correct format");
                System.exit(1);
            }
        }

        if((hostName != null && portString == null) || (hostName == null && portString != null))
        {
            System.err.println("Need to specify btoh the host name and the port. Cannot only specify one");
            System.exit(1);
        }
        int port = 0;
        if(portString != null) {
            try {
                port = Integer.parseInt(portString);
            } catch (NumberFormatException err) {
                System.err.println("Port : " + portString + " must be an interger");
            }
        }

        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

        //If just the owner is given print out the appointments for that owner
        if(justPrint == true)
        {
            try {
                String output = client.getAllAppointments(owner);
                if(output.equals(hostName))
                {
                    throw new Exception("Could not connect to host: " + hostName);
                }
                System.out.println(output);
                System.exit(0);
            }catch (Exception err)
            {
                if(err.getMessage().equals(hostName)) {
                    System.err.println("Could not connect to server: " + err.getMessage());
                    System.exit(1);
                }
                else {
                    System.err.println("Error from server: " + err.getMessage());
                    System.exit(1);
                }
            }
        }

        if(searchFlag == true )
        {
            try {
                String output = client.searchForAppointments(owner, beginDate, beginTime, beginTimeOfDay, endDate, endTime, endTimeOfDay);
                System.out.println(output);
                System.exit(0);

            } catch (Exception err)
            {
                if(err.getMessage().equals(hostName)) {
                    System.err.println("Could not connect to server: " + err.getMessage());
                    System.exit(1);
                }
                else {
                    System.err.println("Error from server: " + err.getMessage());
                    System.exit(1);
                }
            }
        }


        //There are the correct amount of arguments now create appointmentBook and appointment. Then add appointment to appointmentBook
        AppointmentBook apptBook;
        Appointment appt = null;
        if (searchFlag == false) {
            try {
                appt = new Appointment(beginDate, beginTime, endDate, endTime, description, beginTimeOfDay, endTimeOfDay);
            } catch (Exception e) {
                System.err.println("Error in creating appointment: " + e.getMessage());
                System.exit(1);
            }
        }

        if(hostName != null && portString != null) {
            try {
                client.addAppointment(owner, appt);
            } catch (Exception err) {
                if(err.getMessage().equals(hostName)) {
                    System.err.println("Could not connect to server: " + err.getMessage());
                    System.exit(1);
                }
                else {
                    System.err.println("Error from server: " + err.getMessage());
                    System.exit(1);
                }
            }
        }

        //Check for print flag and print out appointment if it is there
        for (Object option : options) {
            if (option.toString().equals("-print")) {
                System.out.println(appt.toString());
                System.exit(0);
            }
        }

        /*--------------------------------------------------*/

//        f:waor (String arg : args) {
//            if (hostName == null) {
//                hostName = arg;
//
//            } else if ( portString == null) {
//                portString = arg;
//
//            } else if (word == null) {
//                word = arg;
//
//            } else if (definition == null) {
//                definition = arg;
//
//            } else {
//                usage("Extraneous command line argument: " + arg);
//            }
//        }
//
//        if (hostName == null) {
//            usage( MISSING_ARGS );
//
//        } else if ( portString == null) {
//            usage( "Missing port" );
//        }
//
//        int port;
//        try {
//            port = Integer.parseInt( portString );
//
//        } catch (NumberFormatException ex) {
//            usage("Port \"" + portString + "\" must be an integer");
//            return;
//        }
//
//        AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);
//
//        String message;
//        try {
//            if (word == null) {
//                // Print all word/definition pairs
//                Map<String, String> dictionary = client.getAllDictionaryEntries();
//                StringWriter sw = new StringWriter();
//                Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
//                message = sw.toString();
//
//            } else if (definition == null) {
//                // Print all dictionary entries
//                message = Messages.formatDictionaryEntry(word, client.getDefinition(word));
//
//            } else {
//                // Post the word/definition pair
//                client.addDictionaryEntry(word, definition);
//                message = Messages.definedWordAs(word, definition);
//            }
//
//        } catch ( IOException ex ) {
//            error("While contacting server: " + ex);
//            return;
//        }
//
//        System.out.println(message);
//
//        System.exit(0);
//    }
//
//    private static void error( String message )
//    {
//        PrintStream err = System.err;
//        err.println("** " + message);
//
//        System.exit(1);
//    }
//
//    /**
//     * Prints usage information for this program and exits
//     * @param message An error message to print
//     */
//    private static void usage( String message )
//    {
//        PrintStream err = System.err;
//        err.println("** " + message);
//        err.println();
//        err.println("usage: java Project4 host port [word] [definition]");
//        err.println("  host         Host of web server");
//        err.println("  port         Port of web server");
//        err.println("  word         Word in dictionary");
//        err.println("  definition   Definition of word");
//        err.println();
//        err.println("This simple program posts words and their definitions");
//        err.println("to the server.");
//        err.println("If no definition is specified, then the word's definition");
//        err.println("is printed.");
//        err.println("If no word is specified, all dictionary entries are printed");
//        err.println();
//
//        System.exit(1);
//    }
        System.exit(0);
    }
}