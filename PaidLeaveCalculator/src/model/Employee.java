package model;

/*import java.sql.Date;

public class Employee {
    private int employeeID;
    private String name;
    private Date birthday;
    private Date entryDate;
    private int sozlesmeID;
    private Date firstEntryDate;
    private Date quitDate;
    private boolean isActive;

    private Employee(Builder builder) {
        this.employeeID = builder.employeeID;
        this.name = builder.name;
        this.birthday = builder.birthday;
        this.entryDate = builder.entryDate;
        this.sozlesmeID = builder.sozlesmeID;
        this.firstEntryDate = builder.firstEntryDate;
        this.quitDate = builder.quitDate;
        this.isActive = builder.isActive;
    }
    public Date getQuitDate() {
		return quitDate;
	}
    public boolean getIsActive() {
		return isActive;
	}
    public Date getFirstEntryDate() {
		return firstEntryDate;
	}

	public int getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public int getSozlesmeID() {
        return sozlesmeID;
    }

    public static class Builder {
        private boolean isActive;
		private int employeeID;
        private String name = "";
        private Date birthday;
        private Date entryDate;
        private int sozlesmeID;
        private Date firstEntryDate;
        private Date quitDate;
        public Builder withID(int employeeID) {
            this.employeeID = employeeID;
            return this;
        }
        public Builder withIsActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        public Builder withQuitDate(Date quitDate) {
            this.quitDate = quitDate;
            return this;
        }
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }
        public Builder withFirstEntryDate(Date firstEntryDate) {
        	this.firstEntryDate = firstEntryDate;
        	return this;
        }
        public Builder withEntryDate(Date entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder withSozlesmeID(int sozlesmeID) {
            this.sozlesmeID = sozlesmeID;
            return this;
        }

        public Employee build() {
            if (birthday == null || entryDate == null) {
                throw new IllegalStateException("Birthday and entry date cannot be empty");
            }

            return new Employee(this);
        }
    }
}
*/
/*
 * usage:
Employee employee = new Employee.Builder(123)
    .withName("") // Empty name
    .withBirthday(new Date(System.currentTimeMillis()))
    .withEntryDate(new Date(System.currentTimeMillis()))
    .withSozlesmeID(456)
    .build();
*/
import java.sql.Date;
import javafx.beans.property.*;
/*
 * Personel modelinin olusturuldugu sınıf.
 * Builder pattern kullanılarak olusturulmustur.
 * 
 */
public class Employee {
    private IntegerProperty employeeID;
    private StringProperty name;
    private ObjectProperty<Date> birthday;
    private ObjectProperty<Date> entryDate;
    private IntegerProperty sozlesmeID;
    private ObjectProperty<Date> firstEntryDate;
    private ObjectProperty<Date> quitDate;
    private BooleanProperty isActive;
    private IntegerProperty toplamIzin;

    private Employee(Builder builder) {
        this.employeeID = new SimpleIntegerProperty(builder.employeeID);
        this.name = new SimpleStringProperty(builder.name);
        this.birthday = new SimpleObjectProperty<>(builder.birthday);
        this.entryDate = new SimpleObjectProperty<>(builder.entryDate);
        this.sozlesmeID = new SimpleIntegerProperty(builder.sozlesmeID);
        this.firstEntryDate = new SimpleObjectProperty<>(builder.firstEntryDate);
        this.quitDate = new SimpleObjectProperty<>(builder.quitDate);
        this.isActive = new SimpleBooleanProperty(builder.isActive);
        this.toplamIzin = new SimpleIntegerProperty(builder.toplamIzin);
    }
    
    public IntegerProperty toplamIzinPropery() {
    	return toplamIzin;
    }
    
    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<Date> birthdayProperty() {
        return birthday;
    }

    public ObjectProperty<Date> entryDateProperty() {
        return entryDate;
    }

    public IntegerProperty sozlesmeIDProperty() {
        return sozlesmeID;
    }

    public ObjectProperty<Date> firstEntryDateProperty() {
        return firstEntryDate;
    }

    public ObjectProperty<Date> quitDateProperty() {
        return quitDate;
    }

    public BooleanProperty isActiveProperty() {
        return isActive;
    }
    
    public final int getToplamIzin() {
    	return toplamIzin.get();
    }
    
    public final int getEmployeeID() {
        return employeeID.get();
    }

    public final String getName() {
        return name.get();
    }

    public final Date getBirthday() {
        return birthday.get();
    }

    public final Date getEntryDate() {
        return entryDate.get();
    }

    public final int getSozlesmeID() {
        return sozlesmeID.get();
    }

    public final Date getFirstEntryDate() {
        return firstEntryDate.get();
    }

    public final Date getQuitDate() {
        return quitDate.get();
    }

    public final boolean getIsActive() {
        return isActive.get();
    }

    public static class Builder {
    	private int toplamIzin;
        private boolean isActive;
        private int employeeID;
        private String name = "";
        private Date birthday;
        private Date entryDate;
        private int sozlesmeID;
        private Date firstEntryDate;
        private Date quitDate;

        public Builder withID(int employeeID) {
            this.employeeID = employeeID;
            return this;
        }

        public Builder withToplamIzin(int toplamIzin) {
            this.toplamIzin = toplamIzin;
            return this;
        }
        public Builder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder withQuitDate(Date quitDate) {
            this.quitDate = quitDate;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBirthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withEntryDate(Date entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public Builder withSozlesmeID(int sozlesmeID) {
            this.sozlesmeID = sozlesmeID;
            return this;
        }

        public Builder withFirstEntryDate(Date firstEntryDate) {
            this.firstEntryDate = firstEntryDate;
            return this;
        }

        public Employee build() {
            if (birthday == null || entryDate == null) {
                throw new IllegalStateException("Birthday and entry date cannot be empty");
            }

            return new Employee(this);
        }
    }
}
