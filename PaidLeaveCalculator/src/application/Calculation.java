package application;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import model.Dates;
/*
 * Bu class tum hesaplama islemlerinin yapıldıgı ve programın mantıgının bulundugu 
 * class'tır.
 * calculateResult fonksiyonu ile ana fonksiyon olan calculatePaidLeave cagrılır. 
 * calculatePaidLeave fonksiyonu diğer fonksiyonları da kullanarak bir çalışanın
 * yıl bazlı izinlerinin bulundugu Arraylist'i döner.
 */
public class Calculation {
	
	public ArrayList<Dates> calculateResult(LocalDate birthday,LocalDate entryDate
			,Date firstEntryDate,ArrayList<int[]> ranges,int[] ages,Date quitDate) {
		
		
        // Eğer doğum tarihi ve işe başlama tarihi null değilse, ödenecek izinleri hesapla
        if (birthday != null && entryDate != null) {
            return calculatePaidLeave(birthday, entryDate,firstEntryDate, LocalDate.now(),ranges,ages,quitDate);
        }
		return null;
    }
	
	 // Yaş hesaplama
	public int calculateAge(LocalDate birthday,LocalDate year) {
		return Period.between(birthday, year).getYears();
	}
	
    // Çalışılan yıl sayısını hesaplama
	public int calculateWorkedYears(LocalDate entryDate, LocalDate year) {
		return Period.between(entryDate , year).getYears();
	}
	
	// İzin hesaplama
	public int calculateIzin(int workedYears, ArrayList<int[]> ranges) {
		int entitledToPaidLeave = 0;
		int maxi = 0;
		
		// Her çalışılan yıl aralığı için ödenecek izin miktarını bulun
		for(int i = 0;i<ranges.size();i++) {			
			if(workedYears >= ranges.get(i)[0] && workedYears < ranges.get(i)[1]) {
				entitledToPaidLeave = ranges.get(i)[2];
			}
			else {
				entitledToPaidLeave = 0;
			}
			maxi = Math.max(maxi, entitledToPaidLeave);
		}
	
		return maxi;
	}
	
	// Ödenecek izinlerin hesaplandığı ana fonksiyon
	public ArrayList<Dates> calculatePaidLeave(LocalDate birthday,LocalDate entryDate
											,Date firstEntryDate ,LocalDate currentDate
											,ArrayList<int[]> ranges,int[] ages
											,Date quitDate) {
		ArrayList<Dates> dates = new ArrayList<>();
		int totalLeave = 0;
		int workedYears = 0;
		int izin = 0;
		
        // Eğer işten çıkış tarihi belirtilmişse, hesaplamaları bu tarihe kadar yap
		if(quitDate!=null) {
			currentDate = quitDate.toLocalDate();
		}
		
        // İlk işe giriş tarihi belirtilmişse, hesaplamaları bu tarihten başlat
		if(firstEntryDate != null) {
			
			// İşe başlama tarihi ve ilk işe giriş tarihinin birleştirilmesi
			LocalDate compose = LocalDate.of(entryDate.getYear()
					,firstEntryDate.toLocalDate().getMonth()
					, firstEntryDate.toLocalDate().getDayOfMonth());
			
			
			//2003 02 20    2003 04 05 
			if(compose.isBefore(entryDate)) {
				compose = compose.plusYears(1);
			}			
			for(LocalDate i = compose;i.isBefore(currentDate);i = i.plusYears(1)) {
				Dates date = new Dates();
				int age = calculateAge(birthday,i);
				
				//Kac yil calisildigi her yil guncel yildan cikarilarak buluniyor.

				workedYears = calculateWorkedYears(firstEntryDate.toLocalDate(),i);
							
				// Yas kontrolu
				if((age < ages[0] || age >= ages[1])) {
					if(calculateIzin(workedYears, ranges)>= ages[2]) {
						izin = calculateIzin(workedYears, ranges);
					}
					else {
						izin = ages[2];
					}
				}
				else {
					izin = calculateIzin(workedYears, ranges);
				}
				totalLeave += izin;
				date.setAge(age);
				date.setInstanstYearOfWorkedYear(i);
				date.setIzinHakki(izin+" days.");
				date.setTotalLeave(totalLeave);				
				dates.add(date);										
			}	
		}
		else {
			//Ise girilen yildan guncel yila kadar hem yasi hem de izni kaydediyoruz
			for(LocalDate i = entryDate;i.isBefore(currentDate);i = i.plusYears(1)) {
				Dates date = new Dates();
				int age = calculateAge(birthday,i);
				
				//Kac yil calisildigi her yil guncel yildan cikarilarak buluniyor.
				workedYears = calculateWorkedYears(entryDate,i);	
				
				// Yas kontrolu
				if((age < ages[0] || age >= ages[1])) {
					if(calculateIzin(workedYears, ranges)>= ages[2]) {
						izin = calculateIzin(workedYears, ranges);
					}
					else {
						izin = ages[2];
					}
				}
				else {
					izin = calculateIzin(workedYears, ranges);
				}
				if(i == entryDate) {
					continue;
				}
				totalLeave += izin;
				date.setAge(age);
				date.setInstanstYearOfWorkedYear(i);
				date.setIzinHakki(izin+" days.");
				date.setTotalLeave(totalLeave);				
				dates.add(date);									
			}
			
		}
		return dates;
	}	
}

