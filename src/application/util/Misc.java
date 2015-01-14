package application.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This class is full of methods that can be used
 * 
 * @author Zack/Optimum
 *
 */
public class Misc {
	
	/**
	 * Adds a comma format to {@link number}
	 * 
	 * @param number
	 *            - The number being given a format
	 * @return The new number format
	 */
	public static String commaNumber(int number) {
		NumberFormat nf;
		Locale cl = Locale.ENGLISH;
		nf = NumberFormat.getNumberInstance(cl);
		String newNumber = nf.format(number);
		return newNumber;
	}

}
