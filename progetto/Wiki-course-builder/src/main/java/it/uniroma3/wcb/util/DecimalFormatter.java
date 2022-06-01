package it.uniroma3.wcb.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Support class used to format a decimal values.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public class DecimalFormatter {

	private int numberOfDecimals;
	
	
	/**
	 * DecimalFormatter constructor.
	 *<p> 
	 * 
	 * @param numberOfDecimals  max fraction digits
	 */
	public DecimalFormatter(int numberOfDecimals){
		this.numberOfDecimals = numberOfDecimals;
	}
	
	
	/**
	 * Returns formatted value.<br/>
	 * If {@code numberOfDecimals} is smaller or equal than 0, will be
	 * returned original value.
	 * <p>
	 * 
	 * @param value value to format
	 * @return formatted value
	 */
	public double format(double value){
		double result = value;
		
		if(this.numberOfDecimals>0){
    		DecimalFormat formatter = new DecimalFormat("#.#");
        	DecimalFormatSymbols decimalFormatSymbols = formatter.getDecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator( '.' );
            formatter.setDecimalFormatSymbols( decimalFormatSymbols );
    		formatter.setMaximumFractionDigits(numberOfDecimals);
        	
    		result = Double.parseDouble(formatter.format(result));
		}
		
		return result;
	}
}
