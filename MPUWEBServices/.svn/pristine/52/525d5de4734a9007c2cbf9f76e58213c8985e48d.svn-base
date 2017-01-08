package com.searshc.mpuwebservice.processor;

import java.util.List;
import java.util.Set;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.RequestDTO;

public interface CSMProcessor {
	
	public String getTimeDifference(String createDate, String store) throws DJException ;
	
	public RequestDTO getPostVoidOrder(String store, String salescheck)	throws DJException ;
	
	public RequestDTO updatePostVoid(String store, RequestDTO requestDTO)throws DJException ;
	
	public List<String> beepToPrinter(String store, String storeFmt,String printerId, String kiosk) throws DJException ;
	public String subscribeCSMToNPOS(String store, String storeFmt) throws DJException ;
	
	public Set<String> logoutModActiveUsers() throws Exception;
	
	public void updateNPOSToLogoutModActiveUsers (Set<String> modstoreNo) throws Exception ;
}
