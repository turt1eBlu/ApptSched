
package model;

import java.time.ZonedDateTime;

/**
 * Model class for representing row data from the first_level_divisions database table.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class FirstLevelDivision {
    private final int DivisionId;
    private final String Division;
    private final String CreatedBy;
    private final ZonedDateTime CreateDate;
    private final int CountryId; 
    private final String UpdatedBy;
    private final ZonedDateTime LastUpdate;
    
    /**
     * Constructor for the FirstLevelDivision class.
     * @param Division_ID the division ID number
     * @param Division the division name
     * @param CreatedBy name of the user who added the division to the database
     * @param UpdatedBy name of the user who last updated the division data
     * @param CreateDate date and time the division was added to the database
     * @param LastUpdate date and time the division was last updated in the database
     * @param Country_ID the ID for the country containing the division
     */
    public FirstLevelDivision(int Division_ID, String Division, String CreatedBy, 
                String UpdatedBy, ZonedDateTime CreateDate, ZonedDateTime 
                LastUpdate, int Country_ID) {
        this.DivisionId = Division_ID;
        this.Division = Division;
        this.CreatedBy = CreatedBy;
        this.UpdatedBy = UpdatedBy;
        this.CreateDate = CreateDate;
        this.LastUpdate = LastUpdate;
        this.CountryId = Country_ID;
    }

    /**
     * Returns the division ID number.
     * @return division ID number
     */
    public int getDivisionId() {
        return DivisionId;
    }

    /**
     * Returns the division name.
     * @return division name
     */
    public String getDivision() {
        return Division;
    }

    /**
     * Returns the name of the user who added the division to the database
     * @return name of the user who added the division to the database
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     * Returns the name of the user who last updated the division in the database
     * @return name of the user who last updated the division in the database
     */
    public String getUpdatedBy() {
        return UpdatedBy;
    }

    /**
     * Returns the date and time the division was added to the database
     * @return date and time the division was added to the database
     */
    public ZonedDateTime getCreateDate() {
        return CreateDate;
    }

    /**
     * Returns the date and time the division was last modified in the database
     * @return date and time the division was last modified in the database
     */
    public ZonedDateTime getLastUpdate() {
        return LastUpdate;
    }

    /**
     * Returns the ID for the country containing the division
     * @return ID for the country containing the division
     */
    public int getCountryId() {
        return CountryId;
    }

    /**
     * Overrides the default toString method and returns the division name.
     * @return string representation of the division containing the division name
     */
    @Override
    public String toString(){
        return Division;
    }
}
