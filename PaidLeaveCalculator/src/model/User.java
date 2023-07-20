package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/*
 * Programı kullanacak olan kullanıcının modelinin olusturuldugu 
 * model sınıfı.
 * 
 */
public class User {
    private StringProperty username;
	private StringProperty password;
    public User() {
		this.username = new SimpleStringProperty();
		this.password = new SimpleStringProperty();
	}
    
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);
	}
	public StringProperty getUsernameProperty() {
		return username;
	}
	
	
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public StringProperty getPasswordProperty() {
		return password;
	}
	

	
}
