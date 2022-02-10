/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Model class for representing data from a query of the appointments table
 * that returns a list of appointment types and the number of each.
 * @author Jennifer Pillow <a href="mailto:jpillo2@wgu.edu">Jennifer Pillow</a>
 */
public class TypeCount {
    private final String Type;
    private final int Count;

    /**
     * Constructor for the TypeCount class.
     * @param Type appointment type
     * @param Count count of the number of appointments of the type
     */
    public TypeCount(String Type, int Count) {
        this.Type = Type;
        this.Count = Count;
    }

    /**
     * Returns the appointment type name.
     * @return appointment type
     */
    public String getType() {
        return Type;
    }

    /**
     * Returns the count of the number of appointments of the type.
     * @return count of the number of appointments of the type
     */
    public int getCount() {
        return Count;
    }    
}
