package com.searshc.mpuwebservice.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.searshc.mpuwebservice.exception.GenericUtilException;
@Component
public class StoreDetailUtil {

	@Autowired
	ExternalPropertyMapServiceImpl propertiesImpl;
	
	String storeNumber;
	
	String storeFormat;
	
	private static Logger logger = Logger.getLogger(StoreDetailUtil.class.getName());


	/**
	 * Get DNS for printer of given store of Sears. Resulting DNS should contain
	 * 4 digit store number. store number may be of 4 digits or 5 digits. we
	 * required it to be 4 digits only. for example - if it is 991 so it would
	 * be change to 0991 and if it is 00991 so it will be change as 0991.
	 * 
	 * @param storeNumber
	 * @param storeFormat
	 * @return DNS for give store String.
	 */

	public String getDNSForStore() {

		String dnsForStore = "";
		logger.info("Store Number  " + storeNumber);
		logger.info("storeFormat  " + storeFormat);
		try {
			if (storeNumber.length() < 4) {
				storeNumber = StringUtils.leftPad(storeNumber, 4, '0');
			} else if (storeNumber.length() > 4) {
				storeNumber = StringUtils.removeStart(storeNumber, "0");
			}
			//Check if application is getting DSN name for QA
			if(propertiesImpl.getValueById("GetDSNForQA").equalsIgnoreCase("N"))
			{
				if (storeFormat.equals(propertiesImpl.getValueById("Sears.StoreFormat"))) {
					dnsForStore = propertiesImpl.getValueById("ticketing.printer.sears.dns");
				} else if (storeFormat.equals(propertiesImpl.getValueById("Kmart.StoreFormat"))) {
					dnsForStore = propertiesImpl.getValueById("ticketing.printer.kmart.dns");
				}
				dnsForStore = dnsForStore.replace("{0}", storeNumber);
			}
			else
			{
				dnsForStore = getDSNForQA(storeNumber);
			}
			logger.info("DNS For Store " + dnsForStore);
		} catch (GenericUtilException e) {
			logger.error("Error while getting the DNS details for store" + e);
		}
		return dnsForStore;
	}

	/**
	 * This method is only for testing - will remove on production
	 * @param storeNumber
	 * @return
	 */
	private String getDSNForQA(String storeNumber)
	{
		String dsn= null;
		try {
			if(storeNumber.equals(propertiesImpl.getValueById("AK.QA.StoreID")))
			{
				dsn = propertiesImpl.getValueById("AK.QA.IPAddress");
			}
			else if(storeNumber.equals(propertiesImpl.getValueById("Ohio.QA.StoreID")))
			{
				dsn = propertiesImpl.getValueById("Ohio.QA.IPAddress");
			}
			else if(storeNumber.equals(propertiesImpl.getValueById("DEJ.QA.StoreID")))
			{
				dsn = propertiesImpl.getValueById("DEJ.QA.IPAddress");
			}
			else if(storeNumber.equals(propertiesImpl.getValueById("RI.QA.StoreID")))
			{
				dsn = propertiesImpl.getValueById("RI.QA.IPAddress");
			}
		} catch (GenericUtilException e) {
			e.printStackTrace();
		}
		return dsn;
	}
	
	 public String getdnsForSOA(String key){
	        String dns="";
	        try{
	                dns=propertiesImpl.getValueById(key);
	                
	        }catch(Exception exception){
	            exception.printStackTrace();
	        }
	        return dns;
	    }
	    
	public static String getSevenDigitStoreNo(String storeNo){
		String sevenDigitStoreNo = storeNo;
		int	loop = 7- storeNo.length();
		if(loop > 0){
			for(int i = 0; i<loop;i++){
				sevenDigitStoreNo = "0"+sevenDigitStoreNo;
			}
		}
		return sevenDigitStoreNo;
	}
	/**
	 * @return the storeNumber
	 */
	public String getStoreNumber() {
		return storeNumber;
	}

	/**
	 * @return the storeFormat
	 */
	public String getStoreFormat() {
		return storeFormat;
	}

	/**
	 * @param storeNumber
	 *            the storeNumber to set
	 */
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	/**
	 * @param storeFormat
	 *            the storeFormat to set
	 */
	public void setStoreFormat(String storeFormat) {
		this.storeFormat = storeFormat;
	}

}
