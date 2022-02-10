# ApptSched

Title: ApptSched
Purpose: The ApptSched program implements GUI-based desktop scheduling application.  Appointments are stored in a MySQL database.

Author: Jennifer Pillow 
Email: jpillo2@wgu.edu

Version: 1.17
Date: August 15, 2021

IDE: Apache NetBeans IDE 12.2
JDK: Java SE 11.0.12
JavaFX: javaFX-SDK-11.0.2
MySQL Connector: mysql-connector-java-8.0.26


Program Directions:

1) Login Screen: Enter a correct user name and password in the provided text fields.  Press the "Login" button.
Text fields can be cleared using the "Reset" button.

2) Schedule Manager main screen:  Press the corresponding button to go to the desired screen.
	View Appointments: Users can view, add or modify existing appointments.
	View Customers: Users can view, add or modify existing customers.
	Appointments by Type: Users can see a report of appointments by type for the selected month.
	Contact Schedule: Users can see a report of appointments for the selected contact.
	Customer Schedule: Users can see a report of appointments for the selected customer.
	Log Off: Returns user to the Login Screen.

3) Appointments: Users can filter appointments by all, month or week by choosing the corresponding radio button.
If month or week is chosen, users can specify a date in the week or month they choose to see data for that period.
Users must select an appointment first if they wish to edit or delete.
	Add Button: Users are taken to a screen where they can add a new appointment.
	Edit Button: Users can edit a selected appointment from the database.
	Delete Button: Users can delete a selected appointment from the database.
	Return to Main Menu Button: Returns user to the Schedule Manager main screen.

4) Appointment Data:  Users enter appointment data.  If a number appears in the Appointment ID text box, the
user is modifying an existing appointment.  Otherwise the user is creating a new appointment.  Required fields 
are marked with an asterisk (*).
	Reset Form Button: Resets data fields on the form to their starting values.
	Cancel Button: Returns user to Appointments screen without saving changes.
	Save: Saves the appointment data to the database and returns user to the Appointments screen.

5) Customers: Users must select a customer first if they wish to edit or delete.
	Add Button: Users are taken to a screen where they can add a new customer.
	Edit Button: Users can edit a selected customer from the database.
	Delete Button: Users can delete a selected customer from the database.
	Return to Main Menu Button: Returns user to the Schedule Manager main screen.

6) Customer Data: Users enter customer data.  If a number appears in the Customer ID text box, the
user is modifying an existing customer.  Otherwise the user is creating a new customer.  All fields are required.
	Reset Form Button: Resets data fields on the form to their starting values.
	Cancel Button: Returns user to Customers screen without saving changes.
	Save: Saves the customer data to the database and returns user to the Customers screen.

7) Appointments by Type: Users select any day in the month for which they wish to see appointment type totals.
	Return to Main Menu Button: Returns user to the Schedule Manager main screen.

8) Contact Schedule: Users select a contact from the menu to see all appointments for the contact.
	Return to Main Menu Button: Returns user to the Schedule Manager main screen.

9) Customer Schedule: Users select a customer from the menu to see all appointments for the customer.
	Return to Main Menu Button: Returns user to the Schedule Manager main screen.
