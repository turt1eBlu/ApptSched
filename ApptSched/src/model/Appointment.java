
package model;

import java.time.ZonedDateTime;
import utils.CurrUser;
import utils.TimeHandler;

/**
 * Model class for representing row data from appointments database table.
 * 
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class Appointment {
    private final int AppointmentId;
    private final String Title;
    private final String Description;
    private final String Location;
    private final String Type;
    private final ZonedDateTime Start;
    private final ZonedDateTime End;
    private final String CreatedBy;
    private final String UpdatedBy;
    private final ZonedDateTime CreateDate;
    private final ZonedDateTime LastUpdate; 
    private final int CustomerID;
    private final int UserID;
    private final int ContactID;

    /**
     * Full constructor for creating appointments to be inserted into to observable list.
     * @param AppointmentId the appointment id 
     * @param Title the appointment title
     * @param Description the appointment description 
     * @param Location the appointment location 
     * @param Type the appointment type 
     * @param Start the start date, time and timezone
     * @param End   the end date, time and timezone
     * @param CreatedBy name of user who created appointment
     * @param UpdatedBy name of user who last updated appointment
     * @param CreateDate the create date, time and timezone
     * @param LastUpdate the last update date, time and timezone
     * @param CustomerID id of customer who has appointment
     * @param UserID id of user in charge of appointment
     * @param ContactID id of contact attached to appointment
     */
    public Appointment(int AppointmentId, String Title, String Description, 
            String Location, String Type, ZonedDateTime Start, 
            ZonedDateTime End, String CreatedBy, String UpdatedBy, 
            ZonedDateTime CreateDate, ZonedDateTime LastUpdate, int CustomerID, 
            int UserID, int ContactID) {
        this.AppointmentId = AppointmentId;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = CreateDate;
        this.LastUpdate = LastUpdate;
        this.CustomerID = CustomerID;
        this.UserID = UserID;
        this.ContactID = ContactID;
    }

    /**
     * Overloaded partial constructor for creating appointments used to modify 
     * existing appointments in database.
     * Doesn't set create date or creator name.
     * @param AppointmentId the appointment id 
     * @param Title the appointment title
     * @param Description the appointment description 
     * @param Location the appointment location 
     * @param Type the appointment type 
     * @param Start the start date, time and timezone
     * @param End   the end date, time and timezone
     * @param UpdatedBy name of user who last updated appointment
     * @param LastUpdate the last update date, time and timezone
     * @param CustomerID id of customer who has appointment
     * @param UserID id of user in charge of appointment
     * @param ContactID id of contact attached to appointment
     */
    public Appointment(int AppointmentId, String Title, String Description, 
            String Location, String Type, ZonedDateTime Start, ZonedDateTime End, 
            String UpdatedBy, ZonedDateTime LastUpdate, int CustomerID, 
            int UserID, int ContactID) {
        this.AppointmentId = AppointmentId;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;       
        this.CreatedBy = null;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = null;
        this.LastUpdate = LastUpdate;
        this.CustomerID = CustomerID;
        this.UserID = UserID;
        this.ContactID = ContactID;
    }
    
    /**
     * Overloaded partial constructor for creating appointments used to add new 
     * rows to the database.
     * Uses current user name for creator and last modifier.  Uses current 
     * ZonedDateTime as create date and last modified date.
     * @param Title the appointment title
     * @param Description the appointment description 
     * @param Location the appointment location 
     * @param Type the appointment type 
     * @param Start the start date, time and timezone
     * @param End   the end date, time and timezone
     * @param CustomerID id of customer who has appointment
     * @param UserID id of user in charge of appointment
     * @param ContactID id of contact attached to appointment
     */
        public Appointment(String Title, String Description, 
            String Location, String Type, ZonedDateTime Start, 
            ZonedDateTime End, int CustomerID, int UserID, int ContactID) {
        this.AppointmentId = 0;         //initialization value will not be used
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.CreatedBy = CurrUser.getName();
        this.UpdatedBy = CurrUser.getName();
        this.CustomerID = CustomerID;
        this.UserID = UserID;
        this.ContactID = ContactID;
        this.CreateDate = ZonedDateTime.now();
        this.LastUpdate = ZonedDateTime.now();
    }
    
    /**
     * Getter for the Appointment ID value.
     * @return the appointment id
     */
    public int getAppointmentId() {
        return AppointmentId;
    }

    /**
     * Getter for the Appointment title.
     * @return the appointment title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Getter for the Appointment description.
     * @return the appointment description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Getter for the Appointment location
     * @return the appointment location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Getter for the Appointment type
     * @return the appointment type
     */
    public String getType() {
        return Type;
    }

    /**
     * Getter for name of user who created the appointment.
     * @return appointment creator name
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * Getter for name of user who last updated the appointment.
     * @return name of user who last updated the appointment
     */
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * Getter for creation date of the appointment
     * @return date and time of appointment creation, with time zone
     */
    public ZonedDateTime getCreateDate() {
        return CreateDate;
    }

    /**
     * Getter for last update date of the appointment
     * @return date and time of last appointment update, with time zone
     */
    public ZonedDateTime getLastUpdate() {
        return LastUpdate;
    }

    /**
     * Getter for ID number of customer for appointment.
     * @return customer ID for appointment
     */
    public int getCustomerID() {
        return CustomerID;
    }


    /**
     * Getter for UserID of user responsible for appointment.
     * @return ID number of user responsible for appointment
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * Getter for the contact ID number for the appointment.
     * @return the contact id number for the appointment
     */
    public int getContactID() {
        return ContactID;
    }

    /**
     * Getter for the date and time the appointment starts.
     * @return the date and time the appointment starts, with timezone
     */
    public ZonedDateTime getStart() {
        return Start;
    }

    /**
     * Getter for the date and time the appointment ends.
     * @return the date and time the appointment ends, with timezone
     */
    public ZonedDateTime getEnd() {
        return End;
    }

    /**
     * Returns appointment start time as a formatted string.
     * String contains date, time and time zone.
     * @return formatted string representation of start date, time and time zone
     */
    public String getStartString() {
        return TimeHandler.zdt2String(Start);
    }

    /**
     * Returns appointment end time as a formatted string.
     * String contains date, time and time zone.
     * @return formatted string representation of end date, time and time zone
     */
    public String getEndString() {
        return TimeHandler.zdt2String(End);
    }
}

