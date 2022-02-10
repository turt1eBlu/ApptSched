
package model;

import java.time.ZonedDateTime;

/**
 * Model class for representing row data from the customers database table.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class Customer {
    
    private final int CustomerId;
    private final String CustomerName;
    private final String Address;
    private final int DivisionId;
    private final String PostalCode;
    private final String Phone;
    private final String CreatedBy;
    private final String UpdatedBy;
    private final ZonedDateTime CreateDate;
    private final ZonedDateTime LastUpdate; 

    /**
     * Full constructor for creating customers to be inserted into to observable list.
     * @param CustomerId the customer ID number
     * @param CustomerName the name of the customer
     * @param Address the customer's street address and city name
     * @param DivId the first level division ID number corresponding to the customer's address
     * @param PostalCode the postal code for the customers address
     * @param Phone the customers phone number
     * @param CreatedBy name of user who added customer to database
     * @param UpdatedBy name of user who last updated the customer data
     * @param CreateDate the date, time and timezone the customer was added
     * @param LastUpdate the date, time and timezone the customer data was last updated
     */
    public Customer(int CustomerId, String CustomerName, String Address, 
            int DivId, String PostalCode, String Phone, String CreatedBy, 
            String UpdatedBy, ZonedDateTime CreateDate, ZonedDateTime LastUpdate) {
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.Phone = Phone;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = CreateDate;
        this.LastUpdate = LastUpdate;
        this.DivisionId = DivId;
    }
    
    /**
     * Overloaded partial constructor for creating customers used to modify 
     * existing customers in database.
     * Doesn't set create date or creator name. 
     * @param CustomerId the customer ID number
     * @param CustomerName the name of the customer
     * @param Address the customer's street address and city name
     * @param DivId the first level division ID number corresponding to the customer's address
     * @param PostalCode the postal code for the customers address
     * @param Phone the customers phone number
     * @param UpdatedBy name of user who last updated the customer data
     * @param LastUpdate the date, time and timezone the customer data was last updated
     */
    public Customer(int CustomerId, String CustomerName, String Address, 
            int DivId, String PostalCode, String Phone, 
            String UpdatedBy, ZonedDateTime LastUpdate) {
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.Phone = Phone;
        this.CreatedBy = null;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = null;
        this.LastUpdate = LastUpdate;
        this.DivisionId = DivId;
    }
    
    /**
     * Overloaded partial constructor for creating customers used to add new 
     * customers to the database.
     * Uses current user name for creator and last modifier.  Uses current 
     * ZonedDateTime as create date and last modified date.
     * @param CustomerName the name of the customer
     * @param Address the customer's street address and city name
     * @param DivId the first level division ID number corresponding to the customer's address
     * @param PostalCode the postal code for the customers address
     * @param Phone the customers phone number
     * @param CreatedBy name of user who added customer to database 
     * @param UpdatedBy name of user who last updated the customer data
     */
    public Customer(String CustomerName, String Address, 
            int DivId, String PostalCode, String Phone, String CreatedBy, 
            String UpdatedBy) {
        this.CustomerId = -1;           //initialization value will not be used
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.Phone = Phone;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = null;
        this.LastUpdate = null;
        this.DivisionId = DivId;
    }

    /**
     * Returns the Customer ID number
     * @return the customer id number
     */
    public int getCustomerId() {
        return CustomerId;
    }

    /**
     * Returns the customer name.
     * @return the customer name
     */
    public String getCustomerName() {
        return CustomerName;
    }

    /**
     * Returns the Customer street address and city
     * @return the customer street address and city
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Returns the ID number for the first level division the customer resides in.
     * @return the customer's first level division
     */
    public int getDivision() {
        return DivisionId;
    }

    /**
     * Returns the customer's postal code
     * @return the customers postal code
     */
    public String getPostalCode() {
        return PostalCode;
    }

    /**
     * Returns the customer's phone number
     * @return the customer phone number
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Returns the name of user who added customer to database
     * @return name of user who added customer to database
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * Returns the name of user who last updated the customer data
     * @return name of user who last updated the customer data
     */
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * Returns the date, time and timezone the customer was added to the database.
     * @return the date, time and timezone the customer was added to the database
     */
    public ZonedDateTime getCreateDate() {
        return CreateDate;
    }

    /**
     * Returns the date, time and timezone the customer was last modified in the database.
     * @return the date, time and timezone the customer was last modified in the database
     */
    public ZonedDateTime getLastUpdate() {
        return LastUpdate;
    }

    /**
     * Overrides the default object toString method and returns string representation 
     * of the customer as the Customer name.
     * 
     * @return formatted string representation of the customer
     */
    @Override
    public String toString(){
        return CustomerName;
    }
}
