package com.safetynet.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    static public int calculateAgeWithJava7(LocalDate birthDate) {
	// validate inputs ...
	LocalDate currentDate = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	int d1 = Integer.parseInt(formatter.format(birthDate));
	int d2 = Integer.parseInt(formatter.format(currentDate));
	int age = (d2 - d1) / 10000;
	return age;
    }
    
}
