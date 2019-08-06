package edu.pdx.cs410j.caameron.myapplication;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;
import java.io.Serializable;
import java.util.Collections;

/**
 * Appointment Book class that holds a Collection of appointments and the name of the owner of the book
 * The Collection of appointments will be represented using an ArrayList of Appointments and the owner
 * will be represented using a String. The class contains methods to get the owner name, add an appointment,
 * and get the current appoints in the collection.
 * @param <T> Appointment Class
 */
public class AppointmentBook<T extends Appointment> extends AbstractAppointmentBook {

    //Data members for Appointmentbook class.
    //Owner of the book and the appointments collection
    private String owner;
    private ArrayList<Appointment> appointments;

    /**
     * Creates a new AppointmentBook and sets the owner name data member to the argument passed in. It will
     * also check to make sure that the owner name passed in is not null or empty or else it exit out and
     * print an error message.
     * @param ownerName
     *        Name of the owner of the Appointment Book
     */
    public AppointmentBook(String ownerName) {
        //Check that the owner field is not empty
        if(ownerName == null)
        {
            System.err.println("Owner name cannot be null");
            System.exit(1);
        }
        if(ownerName.isEmpty())
        {
            System.err.println("Owner name cannot be empty");
            System.exit(1);
        }

        this.owner = ownerName;
    }

    /**
     * Method to return the owner of the Appointment Book
     * @return Returns the owner data member of the class
     */
    @Override
    public String getOwnerName() {
        return owner;
    }

    /**
     * Method to return the Collection of appointments currently in the class. It will also check to
     * see if there are any appointments in the current Collection.
     * @return Returns the appointments as an ArrayList. If there are no appointments, error message is
     * displayed and null is returned.
     */
    @Override
    public ArrayList<Appointment> getAppointments() {
        if(appointments == null)
        {
            return null;
        }

        return appointments;
    }

    /**
     * Method to add an appointment to the ArrayList of appointments. It will first check if the appointments
     * data member is null, if it is it will create a new ArrayList and add the appointment passed in. If
     * it is not null it will simply add it to the ArrayList.
     * Also calls sort appointments upon entry of a new appointment
     * @param appointment Appointment to be added to the Appointment Book.
     */
    @Override
    public void addAppointment(AbstractAppointment appointment) {
        if(appointments == null)
        {
            appointments = new ArrayList<>();
        }
        appointments.add((Appointment) appointment);
        Collections.sort(appointments);
    }

}

