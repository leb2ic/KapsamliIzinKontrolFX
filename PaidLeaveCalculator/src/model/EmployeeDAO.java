package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Database;
/*
 * 
 * Database sınıfı ile baglantı kurularak calısanlar ile ilgili 
 * islemlerin yapıldıgı Employee Data Access Object
 * 
 */

public class EmployeeDAO {
	
	private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    // Çalışanı ID'ye göre ara
    public static Employee searchEmployee (int employeeID) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM dbo.Tab_Employee where ID = ?";
        try {
            ResultSet rsEmp = Database.dbExecuteQuery(selectStmt, employeeID);
            Employee emp = getEmployee(rsEmp);
            return emp;
        } catch (SQLException e) {
            throw new RuntimeException("SQL select operation has been failed: ", e);
        }
    }
    
    // Çalışan bilgilerini ResultSet'tan al
    private static Employee getEmployee(ResultSet rs) throws SQLException, ClassNotFoundException {
        Employee emp = null;
        while (rs.next()) {
            int employeeId = rs.getInt("ID");
            String employeeName = rs.getString("Name");
            Date employeeBirthday = rs.getDate("DogumTarihi");
            Date employeeEntryDate = rs.getDate("GirisTarihi");
            int sozlesmeId = rs.getInt("SozlesmeID");
            Date employeeFirstEntryDate = rs.getDate("IlkGirisTarihi");
            Date employeeQuitDate = rs.getDate("CikisTarihi");
            Boolean isActive = rs.getBoolean("AktifMi");
            int toplamIzin = rs.getInt("ToplamIzin");
            
            // Bilgileri kullanarak Employee nesnesi oluştur
            emp = new Employee.Builder()
            		.withID(employeeId)
            	    .withName(employeeName) // Empty name
            	    .withBirthday(employeeBirthday)
            	    .withEntryDate(employeeEntryDate)
            	    .withSozlesmeID(sozlesmeId)
            	    .withFirstEntryDate(employeeFirstEntryDate)
            	    .withQuitDate(employeeQuitDate)
            	    .withIsActive(isActive)
            	    .withToplamIzin(toplamIzin)
            	    .build();

        }
        //return empList (ObservableList of Employees)
        return emp;
    }
    
    // Tüm çalışanları getir
    public static ObservableList<Employee> searchEmployees () throws SQLException, ClassNotFoundException {

        String selectStmt = "SELECT * FROM dbo.Tab_Employee";
        
        try {
            ResultSet rsEmps = Database.dbExecuteQuery(selectStmt);
            ObservableList<Employee> empList = getEmployeeList(rsEmps);
            return empList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            throw e;
        }
    }
    
    // Tüm çalışanları ResultSet'tan al
    private static ObservableList<Employee> getEmployeeList(ResultSet rs) throws SQLException, ClassNotFoundException {
        ObservableList<Employee> empList = FXCollections.observableArrayList();
        while (rs.next()) {
        	
        	// Veritabanından bilgileri çek
            int employeeId = rs.getInt("ID");
            String employeeName = rs.getString("Name");
            Date employeeBirthday = rs.getDate("DogumTarihi");
            Date employeeEntryDate = rs.getDate("GirisTarihi");
            int sozlesmeId = rs.getInt("SozlesmeID");
            Date employeeFirstEntryDate = rs.getDate("IlkGirisTarihi");
            Date employeeQuitDate = rs.getDate("CikisTarihi");
            Boolean isActive = rs.getBoolean("AktifMi");
            int toplamIzin = rs.getInt("ToplamIzin");
            
            // Bilgileri kullanarak Employee nesnesi oluştur ve listeye ekle
            Employee emp = new Employee.Builder()
            		.withID(employeeId)
            	    .withName(employeeName) // Empty name
            	    .withBirthday(employeeBirthday)
            	    .withEntryDate(employeeEntryDate)
            	    .withSozlesmeID(sozlesmeId)
            	    .withFirstEntryDate(employeeFirstEntryDate)
            	    .withQuitDate(employeeQuitDate)
            	    .withIsActive(isActive)
            	    .withToplamIzin(toplamIzin)
            	    .build();

            empList.add(emp);
        }
        //return empList (ObservableList of Employees)
        return empList;
    }
    
    // Çalışan ID'ye göre tarihleri al
    public static ObservableList<Dates> searchDates (int employeeID) throws SQLException, ClassNotFoundException {
        //Declare a SELECT statement
        String selectStmt = "SELECT * FROM dbo.Tab_Personel_YUI_Hakedisleri where EmployeeID = ?";
        //Execute SELECT statement
        try {
            //Get ResultSet from dbExecuteQuery method
            ResultSet rsDates = Database.dbExecuteQuery(selectStmt,employeeID);
            //Send ResultSet to the getEmployeeList method and get employee object
            ObservableList<Dates> datesList = getDatesList(rsDates);
            //Return employee object
            return datesList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has been failed: " + e);
            //Return exception
            throw e;
        }
    }
    
    //Select * from employees operation
    private static ObservableList<Dates> getDatesList(ResultSet rs) throws SQLException, ClassNotFoundException {
        //Declare a observable List which comprises of Employee objects
        ObservableList<Dates> datesList = FXCollections.observableArrayList();
        while (rs.next()) {
            // Burada çalışanın hakedişleri çekiliyor ve oluşturulan Dates nesnesine atanıyor
            // Bunu her satır (yani her yıl) için yapıyor
        	
			Dates date = new Dates();
			int dateOfHakedis = rs.getInt("ID");
		    LocalDate ID = LocalDate.parse(String.valueOf(dateOfHakedis), inputFormatter);
		    //Date sqlDate = Date.valueOf(localDate);		    
		    //Format the java.sql.Date as a string in the desired format
		    //String formattedDate = outputFormatter.format(localDate);
			String hesaplamaTuru = rs.getString("HesaplamaTuru");
			int sozlesmeID = rs.getInt("SozlesmeID");
			int employeeID = rs.getInt("EmployeeID");
			int izinGunu = rs.getInt("HakedilenYUIGunu");
			int employeeAge = rs.getInt("EmployeeYas");
			int totalLeave = rs.getInt("ToplamIzin");
			String ilkKaydeden = rs.getString("IlkKaydeden");
			if(rs.getTimestamp("IlkKayitZamani")!=null) {
				Timestamp timestamp = rs.getTimestamp("IlkKayitZamani");			
				date.setIlkKayitZamani(timestamp);
			}else {
				date.setIlkKayitZamani(null);
			}
			String sonGuncelleyen = rs.getString("SonGuncelleyen");
			if(rs.getTimestamp("SonGuncellemeZamani")!=null) {
				Timestamp timestamp = rs.getTimestamp("SonGuncellemeZamani");	
				date.setSonGuncellemeZamani(timestamp);
			}else {
				date.setSonGuncellemeZamani(null);
			}
			int silindiMi = rs.getInt("SilindiMi");						
			String silmeAciklamasi = rs.getString("SilmeAciklamasi");
			String silenKisi = rs.getString("SilenKisi");

			date.setInstanstYearOfWorkedYear(ID);
			date.setHesaplamaTuru(hesaplamaTuru);
			date.setemployeeID(employeeID);
			date.setSozlesmeID(sozlesmeID);
			date.setIzinHakki(izinGunu + " days.");
			date.setAge(employeeAge);			
			date.setTotalLeave(totalLeave);
			date.setIlkKaydeden(ilkKaydeden);
			date.setSonGuncelleyen(sonGuncelleyen);
			date.setSilindiMi(silindiMi);
			date.setSilmeAciklamasi(silmeAciklamasi);
			date.setSilenKisi(silenKisi);
			datesList.add(date);
        }
        //return empList (ObservableList of Employees)
        return datesList;
    }
    
    public static ResultSet retrieveSozlesmeRanges(int sozlesmeId) throws ClassNotFoundException, SQLException {
        String query = "SELECT Min,Max,Val FROM dbo.Tab_Sozlesmeler_Child WHERE MasterID = ?";
        return Database.dbExecuteQuery(query, sozlesmeId);
    }
    
    public static ResultSet retrieveSozlesmeAges(int sozlesmeId) throws ClassNotFoundException, SQLException {
        String query = "SELECT MinimumYas, MaximumYas, IstisnaIzin FROM dbo.Tab_Sozlesmeler_Master WHERE ID = ?";
        return Database.dbExecuteQuery(query, sozlesmeId);
    }
    
    public static boolean yuiInDbExist(int integerSozlesmeID, int employeeID) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM dbo.Tab_Personel_YUI_Hakedisleri WHERE ID = ? AND EmployeeID = ?";
        ResultSet rs = Database.dbExecuteQuery(query, integerSozlesmeID, employeeID);
        return rs.next();
    }
    
    public static void deleteAllCalculationsFromDB() throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM dbo.Tab_Personel_YUI_Hakedisleri";
        Database.dbExecuteUpdate(query);
    }
    
    public static void deleteEmployeesCalculationFromDB(int employeeID) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM dbo.Tab_Personel_YUI_Hakedisleri WHERE EmployeeID = ?";
        Database.dbExecuteUpdate(query, employeeID);
    }
    
    public static void addDatetoDB(int integerSozlesmeID, int employeeID, int sozlesmeID, int hakedilenIzinGunu,
    								int age, int totalLeave, String ilkKaydeden,String sonGuncelleyen, String... hesaplamaTuru) throws SQLException, ClassNotFoundException {
        String tempString = (hesaplamaTuru.length != 0) ? hesaplamaTuru[0] : "Otomatik";
        String query = "INSERT INTO dbo.Tab_Personel_YUI_Hakedisleri (ID, HesaplamaTuru, EmployeeID, SozlesmeID, HakedilenYUIGunu, EmployeeYas, ToplamIzin, IlkKaydeden,SonGuncelleyen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (!yuiInDbExist(integerSozlesmeID, employeeID)) {
            Database.dbExecuteUpdate(query, integerSozlesmeID, tempString, employeeID, sozlesmeID, hakedilenIzinGunu, age, totalLeave, ilkKaydeden,sonGuncelleyen);
        }
    }
    
    public static ResultSet getDatefromDB(int employeeID) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM dbo.Tab_Personel_YUI_Hakedisleri WHERE EmployeeID = ?";
        return Database.dbExecuteQuery(query, employeeID);
    }
    
    public static boolean checkIsActive(int employeeID) throws SQLException, ClassNotFoundException {
        String query = "SELECT AktifMi FROM dbo.Tab_Employee WHERE ID = ?";
        ResultSet rs = Database.dbExecuteQuery(query, employeeID);
        if (rs.next()) {
            return rs.getBoolean("AktifMi");
        }
        return false;
    }
    
    public static void updateIsActive(int employeeID, boolean value) throws SQLException, ClassNotFoundException {
        String query = "UPDATE dbo.Tab_Employee SET AktifMi = ? WHERE ID = ?";
        Database.dbExecuteUpdate(query, value ? 1 : 0, employeeID);
    }

    public static void silButonu(int ID, String hesaplamaTuru, int employeeID, String silenKisi, String silmeAciklamasi,String sonGuncelleyen, int deger) throws SQLException, ClassNotFoundException {
        String query = "UPDATE dbo.Tab_Personel_YUI_Hakedisleri SET SilindiMi = ?, SilenKisi = ?, SilmeAciklamasi = ?,SonGuncelleyen = ? WHERE ID = ? AND EmployeeID = ? AND HesaplamaTuru = ?";
        Database.dbExecuteUpdate(query, deger, silenKisi, silmeAciklamasi,sonGuncelleyen, ID, employeeID, hesaplamaTuru);
    }

    public static int silindiMiKontrol(String hesaplamaTuru, int employeeID, int ID) throws SQLException, ClassNotFoundException {
        String query = "SELECT SilindiMi FROM dbo.Tab_Personel_YUI_Hakedisleri WHERE ID = ? AND EmployeeID = ? AND HesaplamaTuru = ?";
        ResultSet rs = Database.dbExecuteQuery(query, ID, employeeID, hesaplamaTuru);
        if (rs.next()) {
            return rs.getInt("SilindiMi");
        }
        return 0;
    }
    
    public static void closeConn() throws SQLException {
    	Database.close();
    }
	
    public static void setAutoCommit(Boolean value) throws SQLException {
    	Database.getConnection().setAutoCommit(value);
    }
    public static void commit() throws SQLException {
    	Database.getConnection().commit();
    }
    public static void rollback() throws SQLException {
    	Database.getConnection().rollback();
    }

}
