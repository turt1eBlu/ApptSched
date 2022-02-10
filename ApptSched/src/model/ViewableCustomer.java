
package model;

/**
 * Model class for representing customers to display in table views.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class ViewableCustomer {
    private final int CustomerId;
    private final String CustomerName;
    private final String Address;
    private final String Division;
    private final String Country;
    private final String PostalCode;
    private final String Phone;

    /**
     * Constructor for the ViewableCustomer class.
     * @param CustomerId the customer ID number
     * @param CustomerName the name of the customer
     * @param Address the customer's street address and city name
     * @param Division the name of the division for the customer's address
     * @param Country the name of the country for the customer's address
     * @param PostalCode the postal code for the customers address
     * @param Phone the customers phone number
     */
    public ViewableCustomer(int CustomerId, String CustomerName, String Address, String Division, String Country, String PostalCode, String Phone) {
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.Division = Division;
        this.Country = Country;
        this.PostalCode = PostalCode;
        this.Phone = Phone;
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
     * Returns the name of the first level division the customer resides in.
     * @return the customer's first level division
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Returns the name of customer's country of residence.
     * @return the customer's country of residence
     */
    public String getCountry() {
        return Country;
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
     * Overrides the default object toString method and returns string representation 
     * of the customer as the Customer ID number and name.
     * 
     * @return formatted string representation of the customer
     */
    @Override
    public String toString(){ 
        return CustomerId + ": " +CustomerName;
    }
}
