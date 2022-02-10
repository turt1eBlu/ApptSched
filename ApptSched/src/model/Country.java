
package model;

import java.time.ZonedDateTime;

/**
 * Model class for representing row data from countries database table.
 * 
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class Country {
    private final int CountryId;
    private final String Country;
    private final String CreatedBy;
    private final String UpdatedBy;
    private final ZonedDateTime CreateDate;
    private final ZonedDateTime LastUpdate; 

    /**
     * Constructor for the Country class.
     * @param Country_ID the Country ID number
     * @param Country the Country name
     * @param CreatedBy name of the user who added the country to the database
     * @param UpdatedBy name of the user who last updated the country data
     * @param CreateDate date and time the country was added to the database
     * @param LastUpdate date and time the country was last updated in the database
     */
    public Country(int Country_ID, String Country, String CreatedBy, String UpdatedBy, 
            ZonedDateTime CreateDate, ZonedDateTime LastUpdate) {
        this.CountryId = Country_ID;
        this.Country = Country;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = CreateDate;
        this.LastUpdate = LastUpdate;
    }

    /**
     * Returns the ID number of the country.
     * @return the country id number
     */
    public int getCountryId() {
        return CountryId;
    }

    /**
     * Returns the name of the country.
     * @return the country name
     */
    public String getCountry() {
        return Country;
    }

    /**
     * Returns the name of the user who added the country to the database.
     * @return the name of the user who added the country to the database
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * Returns the name of the user who last updated the country in the database.
     * @return the name of the user who last updated the country in the database
     */
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * Returns the date and time the country was added to the database
     * @return date and time the country was added to the database
     */
    public ZonedDateTime getCreateDate() {
        return CreateDate;
    }

    /**
     * Returns the date and time the country was last updated in the database
     * @return date and time the country was last updated in the database
     */
    public ZonedDateTime getLastUpdate() {
        return LastUpdate;
    }

    /**
     * Overrides the default toString method and returns the country name.
     * @return string representation of the country containing the country name
     */
    @Override
    public String toString(){
        return Country;
   }
}
