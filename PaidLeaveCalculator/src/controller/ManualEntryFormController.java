package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

import application.OnManualEntrySubmittedListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.Employee;
import model.EmployeeDAO;
import model.User;

/*
 * Manuel bir izin kaydı girilmek istendiğinde, ManualEntryForm.fxml view'inin controller'ı
 * olan bu controller'a, Main.java'daki handleManualEntryButton fonksiyonu içinden
 * yönlendirme sağlanır.
 * 
 */
public class ManualEntryFormController {
	private static Employee employeeToBeAdded;
	private static User userT;
	private OnManualEntrySubmittedListener listener;

    @FXML
    private TextField paidLeaveField;

    @FXML
    private void handleSubmitButton(ActionEvent event) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String formattedDate = today.format(formatter);
        int currentDateAsInt = Integer.parseInt(formattedDate);
        
        int paidLeave = Integer.parseInt(paidLeaveField.getText());

        // Begin transaction
        try {
            EmployeeDAO.setAutoCommit(false);

            // Your code to add the data to the database goes here. You can use the EmployeeDAO.
            // EmployeeDAO.addManualEntry(id, name, age, paidLeave);
            //int integerSozlesmeID, int employeeID, int sozlesmeID, int hakedilenIzinGunu,
            //int age, int totalLeave, String ilkKaydeden,String sonGuncelleyen, String... hesaplamaTuru
            EmployeeDAO.addDatetoDB(
                    currentDateAsInt,
                    employeeToBeAdded.getEmployeeID(),
                    employeeToBeAdded.getSozlesmeID(),
                    paidLeave, 
                    0, 
                    0, 
                    userT.getUsername(),
                    userT.getUsername(),
                    "Manual"
            );

            // If there is no exception, commit the transaction
            EmployeeDAO.commit();
        } catch (ClassNotFoundException | SQLException e) {
            try {
                // If there is any exception, rollback the transaction
                EmployeeDAO.rollback();
            } catch (SQLException rollbackExc) {
                System.err.println("Error during transaction rollback: " + rollbackExc);
            }
            e.printStackTrace();
        } finally {
            try {
                // In the end, set auto-commit back to true
                EmployeeDAO.setAutoCommit(true);
            } catch (SQLException exc) {
                System.err.println("Error setting auto-commit to true: " + exc);
            }
        }

        // Listener
        listener.onManualEntrySubmitted();

        // Close the form
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        // Close the form
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public ManualEntryFormController(Employee employee,User user,OnManualEntrySubmittedListener listener) {
        // ageField.setText(...); // Here, you need to set the age, which is not part of your Employee model. You may need to calculate it or retrieve it from another source.
        // paidLeaveField.setText(...); // This should be populated with the appropriate data from the employee object or from another source.
        userT = user;
        employeeToBeAdded = employee;
        this.listener  = listener;


        
    }
    @FXML
    private void initialize(){
        // Create a UnaryOperator to filter out non-numeric characters
        UnaryOperator<TextFormatter.Change> numberFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= 3) {
                return change; // Accept the change if it contains only digits
            }
            return null; // Reject the change if it contains non-numeric characters
        };

        // Create a TextFormatter with the number filter
        TextFormatter<String> numberTextFormatter = new TextFormatter<>(numberFilter);


        // Set the text formatter
        if(paidLeaveField!=null) {
        	paidLeaveField.setTextFormatter(numberTextFormatter);
        }
    }
    


}
