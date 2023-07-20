package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Dates {
    private final ObjectProperty<LocalDate> ID;
    private ObjectProperty<String> hesaplamaTuru;
    private IntegerProperty employeeID;
    private IntegerProperty sozlesmeID;
    private ObjectProperty<String> izinHakki;    
    private final IntegerProperty age;
    private final IntegerProperty totalLeave;  
    private ObjectProperty<String> ilkKaydeden;
    private ObjectProperty<Timestamp> ilkKayitZamani;
    private ObjectProperty<String> sonGuncelleyen;
    private ObjectProperty<Timestamp> sonGuncellemeZamani;
    private IntegerProperty silindiMi;
    private ObjectProperty<String> silmeAciklamasi;
    private ObjectProperty<String> silenKisi;
    

    public Dates() {
        this.ID = new SimpleObjectProperty<>();
        this.employeeID = new SimpleIntegerProperty();
        this.hesaplamaTuru = new SimpleObjectProperty<>();
        this.sozlesmeID = new SimpleIntegerProperty();
        this.age = new SimpleIntegerProperty();
        this.totalLeave = new SimpleIntegerProperty();
        this.izinHakki = new SimpleObjectProperty<>();
        this.ilkKaydeden = new SimpleObjectProperty<>();
        this.ilkKayitZamani = new SimpleObjectProperty<>();
        this.sonGuncelleyen = new SimpleObjectProperty<>();
        this.sonGuncellemeZamani = new SimpleObjectProperty<>();
        this.silindiMi = new SimpleIntegerProperty();
        this.silmeAciklamasi = new SimpleObjectProperty<>();
        this.silenKisi = new SimpleObjectProperty<>();
        
        
    }
    public Integer getemployeeID() {
        return employeeID.get();
    }

    public void setemployeeID(Integer employeeID) {
        this.employeeID.set(employeeID);
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }
    
    public Integer getSozlesmeID() {
        return sozlesmeID.get();
    }

    public void setSozlesmeID(Integer sozlesmeID) {
        this.sozlesmeID.set(sozlesmeID);
    }

    public IntegerProperty sozlesmeIDProperty() {
        return sozlesmeID;
    }
    
    public LocalDate getInstanstYearOfWorkedYear() {
        return ID.get();
    }

    public void setInstanstYearOfWorkedYear(LocalDate ID) {
        this.ID.set(ID);
    }

    public ObjectProperty<LocalDate> instanstYearOfWorkedYearProperty() {
        return ID;
    }

    public String getHesaplamaTuru() {
        return hesaplamaTuru.get();
    }

    public void setHesaplamaTuru(String hesaplamaTuru) {
        this.hesaplamaTuru.set(hesaplamaTuru);
    }

    public ObjectProperty<String> hesaplamaTuruProperty() {
        return hesaplamaTuru;
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

    public int getTotalLeave() {
        return totalLeave.get();
    }

    public void setTotalLeave(int totalLeave) {
        this.totalLeave.set(totalLeave);
    }

    public IntegerProperty totalLeaveProperty() {
        return totalLeave;
    }

    public String getIzinHakki() {
        return izinHakki.get();
    }

    public void setIzinHakki(String izinHakki) {
        this.izinHakki.set(izinHakki);
    }

    public ObjectProperty<String> izinHakkiProperty() {
        return izinHakki;
    }

    public String getIlkKaydeden() {
        return ilkKaydeden.get();
    }

    public void setIlkKaydeden(String ilkKaydeden) {
        this.ilkKaydeden.set(ilkKaydeden);
    }

    public ObjectProperty<String> ilkKaydedenProperty() {
        return ilkKaydeden;
    }

    public Timestamp getIlkKayitZamani() {
        return ilkKayitZamani.get();
    }

    public void setIlkKayitZamani(Timestamp ilkKayitZamani) {
        this.ilkKayitZamani.set(ilkKayitZamani);
    }

    public ObjectProperty<Timestamp> ilkKayitZamaniProperty() {
        return ilkKayitZamani;
    }

    public Timestamp getSonGuncellemeZamani() {
        return sonGuncellemeZamani.get();
    }

    public void setSonGuncellemeZamani(Timestamp sonGuncellemeZamani) {
        this.sonGuncellemeZamani.set(sonGuncellemeZamani);
    }

    public ObjectProperty<Timestamp> sonGuncellemeZamaniProperty() {
        return sonGuncellemeZamani;
    }

    public int getSilindiMi() {
        return silindiMi.get();
    }

    public void setSilindiMi(int silindiMi) {
        this.silindiMi.set(silindiMi);
    }

    public IntegerProperty silindiMiProperty() {
        return silindiMi;
    }

    public String getSilmeAciklamasi() {
        return silmeAciklamasi.get();
    }

    public void setSilmeAciklamasi(String silmeAciklamasi) {
        this.silmeAciklamasi.set(silmeAciklamasi);
    }

    public ObjectProperty<String> silmeAciklamasiProperty() {
        return silmeAciklamasi;
    }

    public String getSonGuncelleyen() {
        return sonGuncelleyen.get();
    }

    public void setSonGuncelleyen(String sonGuncelleyen) {
        this.sonGuncelleyen.set(sonGuncelleyen);
    }

    public ObjectProperty<String> sonGuncelleyenProperty() {
        return sonGuncelleyen;
    }

    public String getSilenKisi() {
        return silenKisi.get();
    }

    public void setSilenKisi(String silenKisi) {
        this.silenKisi.set(silenKisi);
    }

    public ObjectProperty<String> silenKisiProperty() {
        return silenKisi;
    }
}
