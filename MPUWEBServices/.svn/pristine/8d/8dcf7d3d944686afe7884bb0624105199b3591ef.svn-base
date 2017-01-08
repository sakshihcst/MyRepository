package com.searshc.mpuwebservice.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.searshc.mpuwebservice.dao.impl.MPUWebServiceDAOImpl;

public class ItemNumValidator {

	private static transient DJLogger logger = DJLoggerFactory.getLogger(ItemNumValidator.class);		
		public static boolean isDivItemSku(String divItemSku) {
			if(divItemSku != null && divItemSku.length() > 1 ) {
				return divItemSku.length() == 11;
			} else {
				return false;
		    }
		}		
	    
	      /**
	       * Checks for valid KSN
	       * @description : 
	       *  -The barcode must be either 8 to 11 digits.  
	       *  -If it is 11 digits, then the first two digits must be ‘40’(Printed KSN barcodes have a leading ‘4’ imbedded,
	       *    but the leading ‘4’ is not printed as a digit under the barcode).
	       *  -If not then exit with false.
	       *  @author Mohd Adnan
	       *  @param StringBuilder : barcode
	       *  @return true or false
	       */
	      public static boolean isKSN(String ksn){
	    	  if(ksn!=null){
		      	  Pattern pattern = Pattern.compile("(40[\\d]{9})|([\\d]{8})|([\\d]{9})|([\\d]{10})");
		      	  Matcher matcher = pattern.matcher(ksn);
		      	  return matcher.matches();
	    	  }
	    	  return false;
	        }
	      
	      
	      /**
			 * Determines if the barcode scanned is a valid UPC barcode
			 * The UPC barcode is numeric with a length between 12-13
			 * @param String barcode 
			 * @return boolean - true if a UPC barcode was scanned, false otherwise
			 */
			public static boolean isUpc(String inBarcode) {
			
			    if (null!=inBarcode && (inBarcode.length() > 11) && (inBarcode.length() < 14)) {
					try {
						Long.parseLong(inBarcode);
						return true;
					}
					catch (NumberFormatException e) {
						logger.error("isUpc", e);
						return false;
					}       
			    }			    
			    return false;
			}
}