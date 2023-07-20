package application;
/*
 * Calisan izni tablosunun manuel izin girildiginde de Main.java'daki fonksiyonun
 * kullanilarak otomatik yenilenebilmesi icin bir interface
 */
public interface OnManualEntrySubmittedListener {
	void onManualEntrySubmitted();
}
