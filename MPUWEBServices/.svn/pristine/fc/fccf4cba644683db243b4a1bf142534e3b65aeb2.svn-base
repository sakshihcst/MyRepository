package com.searshc.mpuwebservice.util;

import org.springframework.stereotype.Service;

/**
 * @author dmadaka
 *
 */
public class MPUWebServiceValidator {
	
	public static boolean isSalescheckNumberValid(String salescheckNo){
		boolean validFlag = false;
		if(salescheckNo!=null && !salescheckNo.isEmpty()){
			if(salescheckNo.length() == 12){
				validFlag = true;
			}
		}
		return validFlag;		
	}
	
	public static boolean isStoreNumberValid(String storeNo){
		boolean validFlag = false;
		if(storeNo!=null && !storeNo.isEmpty()){
			if(storeNo.length() > 3 && storeNo.length() <= 5){
				validFlag= true;
			}
		}
		return validFlag;
	}
	
	public static boolean isLockerNumberValid(String lockerNo){
		boolean validFlag = false;
		if(lockerNo!=null && !lockerNo.isEmpty()){
			if(lockerNo.length()> 1 && lockerNo.length() <=2){
				validFlag = true;
			}
		}
		return validFlag;
	}
	
	public static boolean isSKUValid(String sku){
		boolean validFlag = false;
		if(sku!=null && !sku.isEmpty()){
			if(sku.length()> 1 && sku.length() <=3){
				validFlag = true;
			}
		}
		return validFlag;
	}
	public static boolean isDivValid(String div){
		boolean validFlag = false;
		if(div!=null && div.isEmpty()){
			if(div.length()> 1 && div.length() <=3){
				validFlag = true;
			}
		}
		return validFlag;
	}
}
