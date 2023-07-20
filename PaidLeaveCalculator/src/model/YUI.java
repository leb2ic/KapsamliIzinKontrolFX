package model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

public class YUI {
    private final ObjectProperty<LocalDate> instanstYearOfWorkedYear;
    private final IntegerProperty age;
    private final IntegerProperty izinHakki;
    private final IntegerProperty totalLeave;

    public YUI() {
        this.instanstYearOfWorkedYear = new SimpleObjectProperty<>();
        this.age = new SimpleIntegerProperty();
        this.izinHakki = new SimpleIntegerProperty();
        this.totalLeave = new SimpleIntegerProperty();
    }

    public LocalDate getInstanstYearOfWorkedYear() {
        return instanstYearOfWorkedYear.get();
    }

    public void setInstanstYearOfWorkedYear(LocalDate instanstYearOfWorkedYear) {
        this.instanstYearOfWorkedYear.set(instanstYearOfWorkedYear);
    }

    public ObjectProperty<LocalDate> instanstYearOfWorkedYearProperty() {
        return instanstYearOfWorkedYear;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public int getIzinHakki() {
        return izinHakki.get();
    }

    public void setIzinHakki(int izinHakki) {
        this.izinHakki.set(izinHakki);
    }

    public IntegerProperty izinHakkiProperty() {
        return izinHakki;
    }

    public int getTotalLeave() {
        return totalLeave.get();
    }

    public void setTotalLeave(int totalLeave) {
        this.totalLeave.set(totalLeave);
    }

    public IntegerProperty totalLeaveProperty() {
        return totalLeave;
    }
}
