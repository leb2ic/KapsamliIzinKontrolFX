package controller;
import model.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import application.Calculation;
import application.OnManualEntrySubmittedListener;

/*
 * Controller class; ana ekrandaki ve login ekranındaki tum view'leri
 * kontrol edildiği sınıf.
 */
public class Main extends Application implements OnManualEntrySubmittedListener{
	private Calculation calculation = new Calculation();
	private ObservableList<Employee> empData;
	private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	@SuppressWarnings("unused")
	private DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");	
	private User user =  null;
	private Dates dateForDelete = null;
	@FXML
	private Button deleteSelectedEmployeesCalculationButton;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Tab loginTab;

	@FXML
	private Tab anaEkranTab;
	
	@FXML
	private TabPane tabPane;
	
	@FXML
	private Text resultText;
	
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TableView<Dates> employeesCalculatedPaidLeaveView;

	@FXML
	private TableColumn<Dates, LocalDate> columnInstantYear;

	@FXML
	private TableColumn<Dates, String> columnIzinHakki;

	@FXML
	private TableColumn<Dates, Integer> columnAge;
	
	@FXML
	private TableColumn<Dates, String> columnHesaplamaTuru;
	
	@FXML
	private TableColumn<Dates, String> columnIlkKaydeden;
	
	@FXML
	private TableColumn<Dates, Timestamp> columnIlkKaydetmeZamani;
	
	@FXML
	private TableColumn<Dates, String> columnSonGuncelleyen;
	
	@FXML
	private TableColumn<Dates, Timestamp> columnSonGuncellemeZamani;
	
	@FXML
	private TableColumn<Dates, String> columnSilmeAciklamasi;
	
	@FXML
	private TableView<Employee> employeeTableView;

	@FXML
	private TableColumn<Employee, Integer> columnEmployeeID;

	@FXML
	private TableColumn<Employee, String> columnEmployeeName;

	@FXML
	private TableColumn<Employee, Date> columnEmployeeBirthday;

	@FXML
	private TableColumn<Employee, Date> columnEmployeeEntryDate;

	@FXML
	private TableColumn<Employee, Date> columnEmployeeQuitDate;

	@FXML
	private TableColumn<Employee, Date> columnEmployeeFirstEntryDate;
	
	@FXML
	private TableColumn<Employee, Integer> columnEmployeeToplamIzin;
	
	@FXML
	private TableColumn<Employee, Integer> columnEmployeeSozlesmeID;
    
    @FXML
    private Button calculateAllEmployeesLeaveButton;
     
    @FXML
    private void loginButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException, InterruptedException {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        System.out.println(username+ " " +password);
        user = new User();
        //Kullanici db'de kayitli mi degil mi
        if(UserDAO.checkUser(username, password)){
        	user.setUsername(username);
        	user.setPassword(password);      
        	TimeUnit.SECONDS.sleep(1);
        	loginTab.setDisable(true);
        	anaEkranTab.setDisable(false);
        	tabPane.getSelectionModel().select(anaEkranTab);
        	tabPane.getTabs().remove(loginTab);
        }
        else {
        	resultText.setText("Kullanici adi veya sifre yanlis.");
        }
        //Make db control
    }
  
    @FXML
    private void employeeTableViewOnMouseClicked(javafx.scene.input.MouseEvent event) throws SQLException, ClassNotFoundException {
        //Calisan tablosuna tiklanildiginda izin tablosunu guncelle
    	updateAndShowPaidLeave();    	
    }
    
    @FXML
    private void showDeleteReasonPopup() {
    	// Eger izin silme islemi gerceklestirilecekse silme sebebinin popup seklinde cikmasi ve 
    	// submit ile silinmesi
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Reason");
        dialog.setHeaderText("Please enter the reason for deleting.");
        // Set the delete button text
        Button deleteSelectedEmployeesCalculationButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        deleteSelectedEmployeesCalculationButton.setText("Delete");

        // Disable the delete button initially
        deleteSelectedEmployeesCalculationButton.setDisable(true);

        // Enable the delete button only if there is input text
        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
        	deleteSelectedEmployeesCalculationButton.setDisable(newValue.trim().isEmpty());
        });

        Optional<String> result = dialog.showAndWait();
        
        result.ifPresent(deleteReason -> {
            // Perform the delete operation with the provided reason
        	try {
				EmployeeDAO.silButonu(Integer.parseInt(dateForDelete.getInstanstYearOfWorkedYear().format(inputFormatter))
						, dateForDelete.getHesaplamaTuru()
						, dateForDelete.getemployeeID()
						, user.getUsername()
						, deleteReason
						, user.getUsername()
						, 1);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
        	//EmployeeDAO.callRecalculateProcedure(dateForDelete.getemployeeID());
            System.out.println("Delete Reason: " + deleteReason);
        });
        
    }
    
    @FXML
    private void updateEmployeeTableAction(ActionEvent event) throws ClassNotFoundException, SQLException {
    	//Calisan tablosunu db'ye gore guncelle
        empData = EmployeeDAO.searchEmployees();     
        employeeTableView.setItems(empData);
    }
    
    @FXML
    private void deleteSelectedEmployeesCalculationEvent(ActionEvent event) throws ClassNotFoundException, SQLException {
    	//eger hem calisan hem de silmek istenilen izin seciliyse silme islemine yonlendir (showDeletePopup())
    	if(employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem()!=null && employeeTableView.getSelectionModel().getSelectedItem()!=null) {
    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    		int formattedDate = Integer.parseInt(employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem().getInstanstYearOfWorkedYear().format(formatter));
    		System.out.println(employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem().getHesaplamaTuru());
    		if(EmployeeDAO.silindiMiKontrol(
    				employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem().getHesaplamaTuru()
    				,employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem().getemployeeID()
    				,formattedDate)==0) {
    			dateForDelete = employeesCalculatedPaidLeaveView.getSelectionModel().getSelectedItem();
    			showDeleteReasonPopup();
  
    			updateAndShowPaidLeave();    			
    		}else {
    			

    			System.out.println("Zaten Silindi");

    		}
    		
    	}
    	//Eger secme islemi yapilmadiysa alert goster
    	else {
    		
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Calisani ve silmek istediginiz girisi secmelisiniz!");
            alert.showAndWait();
        	
    	}

    }
    
    //Tum hesaplamalari sil
    @FXML
    private void deleteAllEmployeesAction(ActionEvent event) throws ClassNotFoundException, SQLException {    	    	
    	EmployeeDAO.deleteAllCalculationsFromDB();
    	updateAndShowPaidLeave();
    	System.out.println("Tum hesaplamalar silindi!");
    }
    
    //Tek bir çalısanın izinlerini hesapla
    @FXML
    private void handleCalculateButton(ActionEvent event) {
        // Processevent fonksiyonuna calisani gonder
    	
    	Employee emp = null;
		try {
			if(employeeTableView.getSelectionModel().getSelectedItem()!=null) {
				emp = EmployeeDAO.searchEmployee(employeeTableView.getSelectionModel().getSelectedItem().getEmployeeID());
			}else {
				//Eger seçim yapılmadıysa alert goster
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Calisan secili degil!");
	            alert.showAndWait();
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        processEvent(emp);
    }
    
    @FXML
    private void calculateAllEmployeesLeaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {      	
        // Hesaplama islemi uzun surdugu icin programin cokmemesi icin olusturdum ama riskli!
		calculateAndStoreToDB();

    }
    
    //Tum calısanların izinlerini hesapla
    private void calculateAndStoreToDB() throws ClassNotFoundException, SQLException {
        
    	// DB'deki her bir calisani processevent ile hesaplamaya gonder
    	
        ObservableList<Employee> employeeList = EmployeeDAO.searchEmployees();
        if(employeeList!=null) {
            employeeList.forEach(this::processEvent);
        } else {
            System.out.println("burasiiii");
        }
        
    }
    
    //Hesaplama islemlerini verilen calısan icin gerceklestir
    private void processEvent(Employee employee) {    	
        // Calisan yoksa    	
        if (employee == null) return;
        try {
        	//Eger calisan uzerinde aktif olarak islem yapiliyorsa
            if (EmployeeDAO.checkIsActive(employee.getEmployeeID())) {
                System.out.println("Su anda bu calisan uzerinde baska bir islem yapiliyor. EmployeeID = " + employee.getEmployeeID());
                return;
            }
            
            
            // yapilmiyorsa active olarak islem yapmayi true yap
            EmployeeDAO.updateIsActive(employee.getEmployeeID(), true);

            // sozlesme-mevzuat alt ust degerleri ve yas degerlerini db'den cek
            ArrayList<int[]> rangesList = getRangesList(employee);
            int[] agesArray = getAgesArray(employee);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            
            ArrayList<Dates> resultList = calculation.calculateResult(
                employee.getBirthday().toLocalDate(),
                employee.getEntryDate().toLocalDate(),
                employee.getFirstEntryDate(),
                rangesList,
                agesArray,
                employee.getQuitDate());

            System.out.println("Buraya girdim");
            EmployeeDAO.setAutoCommit(false);
            
            resultList.forEach(date -> {
				try {
					addDateToDB(date, employee, formatter);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
            EmployeeDAO.commit(); 
            
            EmployeeDAO.updateIsActive(employee.getEmployeeID(), false);

            //
            updateAndShowPaidLeave();

        } catch (ClassNotFoundException | SQLException e) {
        	
            try {
                // An error occurred, rollback the transaction
                EmployeeDAO.rollback();
            } catch (SQLException rollbackExc) {
                System.err.println("Error during transaction rollback: " + rollbackExc);
            }
            e.printStackTrace();
        } finally {
            try {
                // Set auto-commit back to true
                EmployeeDAO.setAutoCommit(true);
            } catch (SQLException exc) {
                System.err.println("Error setting auto-commit to true: " + exc);
            }
        }
    }
    
    //Mevzuat veya sozlesmelerin sınır degerlerini DB'den cek
    private ArrayList<int[]> getRangesList(Employee employee) throws SQLException, ClassNotFoundException {
    	// RangesDAO ve Range class'i olusturulup yapilmasi daha clean
        // Rangeleri db'den cek
        ResultSet ranges = EmployeeDAO.retrieveSozlesmeRanges(employee.getSozlesmeID());
        ArrayList<int[]> rangesList = new ArrayList<>();
        while (ranges.next()) {
            int[] temp = {ranges.getInt("Min"), ranges.getInt("Max"), ranges.getInt("Val")};
            rangesList.add(temp);
        }
        return rangesList;
    }
    
    //Mevzuat veya sozlesmlerde bulunan yas degerlerini cek
    private int[] getAgesArray(Employee employee) throws SQLException, ClassNotFoundException {
    	// AgesDAO ve age class'i olusturulup yapilmasi daha clean
        // Age'i db'den cek
        ResultSet ages = EmployeeDAO.retrieveSozlesmeAges(employee.getSozlesmeID());
        int[] agesArray = new int[3];
        while (ages.next()) {
            agesArray[0] = ages.getInt("MinimumYas");
            agesArray[1] = ages.getInt("MaximumYas");
            agesArray[2] = ages.getInt("IstisnaIzin");
        }
        return agesArray;
    }

    //Hesaplanan izni DB'ye ekle
    private void addDateToDB(Dates date, Employee employee, DateTimeFormatter formatter) throws ClassNotFoundException {
        // Database'e izinleri tarihleriyle birlikte ekle
        try {
            String[] parts = date.getIzinHakki().split("\\D");
            String extractedNumberString = parts[0];
            int formattedDate = Integer.parseInt(date.getInstanstYearOfWorkedYear().format(formatter));
            int izinGunu = Integer.parseInt(extractedNumberString);
            int age = date.getAge();
            int totalLeave = date.getTotalLeave();
            EmployeeDAO.addDatetoDB(formattedDate, employee.getEmployeeID(), employee.getSozlesmeID(), izinGunu, age, totalLeave, user.getUsername(),user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Login ekraninda enter'a basildiginda button event'e git
    @FXML
    private void handleEnterPressed(KeyEvent event) throws ClassNotFoundException, SQLException, InterruptedException {   	
        if (event.getCode() == KeyCode.ENTER) {
            loginButtonAction(new ActionEvent());
        }
    }
    
    //Manuel bir izin girdisi yapilacaksa yeni bir fxml olusturup farkli bir controller'a yonlendir
    @FXML
    private void handleManualEntryButton(ActionEvent event) {    	
    	if(employeeTableView.getSelectionModel().getSelectedItem()!=null) {
            try {
                // Get the selected employee
                Employee selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ManualEntryForm.fxml"));
                // Set the controller with the selected employee
                fxmlLoader.setController(new ManualEntryFormController(selectedEmployee,user,this));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Manual Paid Leave Entry");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    	else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Manuel entry girmek istediginiz calisani tablodan secmelisiniz!");
            alert.showAndWait();
    	}

    }
    //Populate Employees for TableView
    @FXML
    private void initialize() {
    	//baslangicta kullanici giris yapana kadar anaekrani gosterme
    	anaEkranTab.setDisable(true);
    	
    	columnHesaplamaTuru.setCellValueFactory(cellData -> cellData.getValue().hesaplamaTuruProperty());
    	columnInstantYear.setCellValueFactory(cellData -> cellData.getValue().instanstYearOfWorkedYearProperty());
    	columnIzinHakki.setCellValueFactory(cellData -> cellData.getValue().izinHakkiProperty());
    	columnAge.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
    	columnIlkKaydeden.setCellValueFactory(cellData -> cellData.getValue().ilkKaydedenProperty());
    	columnIlkKaydetmeZamani.setCellValueFactory(cellData -> cellData.getValue().ilkKayitZamaniProperty());
    	columnSonGuncelleyen.setCellValueFactory(cellData -> cellData.getValue().sonGuncelleyenProperty());
    	columnSonGuncellemeZamani.setCellValueFactory(cellData -> cellData.getValue().sonGuncellemeZamaniProperty());
    	columnSilmeAciklamasi.setCellValueFactory(cellData -> cellData.getValue().silmeAciklamasiProperty());
    	
    	columnEmployeeID.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty().asObject());
    	columnEmployeeName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	columnEmployeeBirthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
    	columnEmployeeEntryDate.setCellValueFactory(cellData -> cellData.getValue().entryDateProperty());
    	columnEmployeeQuitDate.setCellValueFactory(cellData -> cellData.getValue().quitDateProperty());
    	columnEmployeeFirstEntryDate.setCellValueFactory(cellData -> cellData.getValue().firstEntryDateProperty());
    	columnEmployeeToplamIzin.setCellValueFactory(cellData -> cellData.getValue().toplamIzinPropery().asObject());
    	columnEmployeeSozlesmeID.setCellValueFactory(cellData -> cellData.getValue().sozlesmeIDProperty().asObject());

    	// Eger silme veya manuel izin girisi yapilirsa satir rengi degistir
    	employeesCalculatedPaidLeaveView.setRowFactory(tv -> new TableRow<Dates>() {
    	    @Override
    	    protected void updateItem(Dates item, boolean empty) {
    	        super.updateItem(item, empty);
    	        if (item != null && item.getSilindiMi() == 1) {
    	            // Set the row style to red
    	            setStyle("-fx-background-color: red;");
    	        } 
    	        else if(item != null && item.getHesaplamaTuru().equals("Manual")){
    	        	setStyle("-fx-background-color: orange;");
    	        }
    	        else {
    	            // Reset the row style
    	            setStyle("");
    	        }
    	    }
    	});
    }
    
    @Override
    public void start(Stage primaryStage) {
        // Load UI
        setupUI(primaryStage);
        // Populate Employee TableView
        populateEmployeeTable();
    }
    
    @SuppressWarnings("unchecked")
	private void setupUI(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paid Leave Calculator");
            primaryStage.setResizable(false);
            primaryStage.show();
            
            // Get references to controls
            employeeTableView = (TableView<Employee>) scene.lookup("#employeeTableView");
            employeesCalculatedPaidLeaveView  = (TableView<Dates>) scene.lookup("#employeesCalculatedPaidLeaveView");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Tabloya calısanları DB'den cek
    private void populateEmployeeTable() {
        try {
            empData = EmployeeDAO.searchEmployees();
            employeeTableView.setItems(empData);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {    	
        launch(args);
    }
    
	@Override
	public void onManualEntrySubmitted() {
		try {
			updateAndShowPaidLeave();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
	}
	
	// Izin tablosunun degisiklik durumlarinda guncellenmesi gerceklestiriliyor
    private void updateAndShowPaidLeave() throws SQLException, ClassNotFoundException {   	
    	employeesCalculatedPaidLeaveView.getItems().clear();
        if(employeeTableView.getSelectionModel().getSelectedItem()!=null) {
            ObservableList<Dates> observableDateList = EmployeeDAO.searchDates(employeeTableView.getSelectionModel().getSelectedItem().getEmployeeID());
            employeesCalculatedPaidLeaveView.setItems(observableDateList);
        }
        
    }
}
