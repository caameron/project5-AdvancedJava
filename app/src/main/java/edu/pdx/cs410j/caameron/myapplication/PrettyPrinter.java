//package edu.pdx.cs410j.caameron.myapplication;
//
//import edu.pdx.cs410J.AbstractAppointmentBook;
//import edu.pdx.cs410J.AppointmentBookDumper;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.Period;
//import java.time.ZoneId;
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * PrettyPrinter class that implements AppointmentBookDumper. Its method will take the content of an appointment book
// * and create a nicely formatted textual representation of it.
// * @param <T> AppointmentBook Class
// */
//public class PrettyPrinter<T extends AbstractAppointmentBook> implements AppointmentBookDumper {
//
//    private String prettyOutput;
//    /**
//     * Method to create a new file and dump the content of an appointment book to it in a nice textual representation
//     * @param abstractAppointmentBook The appointment book's content that will be written out to a text file
//     * @throws IOException Will throw an exception if unable to create or write out to a text file
//     */
//    @Override
//    public void dump(AbstractAppointmentBook abstractAppointmentBook) throws IOException {
//        try {
//            AbstractAppointmentBook<Appointment> apptBook = abstractAppointmentBook;
//            Collection<Appointment> appointments = apptBook.getAppointments();
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("Appointment Book for ").append(abstractAppointmentBook.getOwnerName()).append("\n");
//            stringBuilder.append("Appointments Listed from earliest to latest\n\n");
//
//            //Pretty Print all the appointments and the owner name
//            int counter = 1;
//            for (Appointment appointment : appointments) {
//                LocalDateTime begin = appointment.getBeginTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//                LocalDateTime end = appointment.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//                Duration between = Duration.between(begin, end);
//                long minutesBetween = between.toMinutes();
//                stringBuilder.append("Appointment #" + counter + ": " + appointment.getDescription() + "\n");
//                stringBuilder.append("Appointment starts at : " + appointment.getBeginTimeString() + "   ");
//                stringBuilder.append("Appointment ends at : "+ appointment.getEndTimeString() + "\n");
//                stringBuilder.append("Duration of appointment: " + minutesBetween + " minutes\n\n");
//                ++counter;
//            }
//
////            if(filename.equals("-"))
////            {
////                System.out.println(stringBuilder);
////            }
////            else {
////                BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
////                writer.write(stringBuilder.toString());
////                writer.close();
////            }
//            prettyOutput = stringBuilder.toString();
//        }
//        catch (Exception err)
//        {
//            throw new IOException("Error in trying to pretty print the appointment book");
//        }
//    }
//
//    public String getPrettyOutput(){
//        if (prettyOutput == null)
//        {
//            return "";
//        }
//
//        return prettyOutput;
//    }
//}
