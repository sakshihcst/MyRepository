package com.searshc.mpuwebservice.dao.impl;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CREATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_DISPLAY_FIELD;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_DISPLAY_VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_TASK_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_TASK_STATUS;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NO;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.STORE_NUMBER;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TASK_TYPE_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATED_BY;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.UPDATED_DATE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQD_ID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.client.RestTemplate;

import com.sears.dj.common.dao.DJRepository;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.mpu.dao.DJMPUCommonDAO;
import com.sears.mpu.backoffice.domain.Order;
import com.sears.mpu.backoffice.domain.OrderAdaptorRequest;
import com.sears.mpu.backoffice.domain.OrderAdaptorResponse;
import com.sears.mpu.backoffice.domain.Printer;
import com.searshc.mpuwebservice.bean.CSMTaskDTO;
import com.searshc.mpuwebservice.bean.CSMTaskDetailDTO;
import com.searshc.mpuwebservice.dao.MODWebServiceDAO;
import com.searshc.mpuwebservice.util.MPUWebServiceUtil;
import com.searshc.mpuwebservice.util.PropertyUtils;


/**
 * @description This class contains DAO methods for CSM task.
 * @author gauti
 *
 */

@DJRepository("modWebServiceDAOImpl")
public class MODWebServiceDAOImpl  extends DJMPUCommonDAO implements MODWebServiceDAO    {
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MODWebServiceDAOImpl.class);

	@Autowired
	RestTemplate restTemplate;
		
	
	public int insertCSMTask(CSMTaskDTO csmTaskDTO) throws DJException {
		logger.debug("insertCSMTask", "Start ");
		Map<String, Object> param = new HashMap<String, Object>();
		
		//TODO Need to move sql and table string to CONSTANT file 
		String sql=PropertyUtils.getProperty("insert.into.csm_task") ;
		
		param.put(STORE_NUMBER.name(),csmTaskDTO.getStoreNo() );
		param.put(TASK_TYPE_ID.name(),csmTaskDTO.getTasktypeId() );
		param.put(RQT_ID.name(),csmTaskDTO.getReqId() );
		param.put(CREATED_BY.name(),csmTaskDTO.getCreatedBy() );
		param.put(CREATED_DATE.name(),csmTaskDTO.getCreatedDate() );
		param.put(UPDATED_BY.name(),csmTaskDTO.getUpDatedBy() );
		param.put(UPDATED_DATE.name(),csmTaskDTO.getUpDatedDate() );
		param.put(CSM_TASK_STATUS.name(), csmTaskDTO.getCsmTaskStatus());
		
		int taskId= super.updateWithKeyHolder(csmTaskDTO.getStoreNo(), sql, param).intValue();
		logger.debug("insertCSMTask", "Ended OK, new task id: "+taskId);
		return taskId;
	}

	public int insertCSMTaskDetail(List<CSMTaskDetailDTO> csmTaskDetailDTO,int taskid, String storeNO) throws DJException {
		//TODO Need to move sql and table string to CONSTAND file
		logger.debug("insertCSMTaskDetail", "Start ");
		String sql=PropertyUtils.getProperty("insert.into.csm_task_detail") ;
		
		// creating List Map as we need to insert multiple rows
		Iterator it=csmTaskDetailDTO.iterator();
		List<Map<String, ? super Object>> params = new ArrayList<Map<String,? super Object>>();
		while (it.hasNext()) {
			CSMTaskDetailDTO detailDTO = (CSMTaskDetailDTO) it.next();
			params.add(getCSMTaskDetailsColumns(detailDTO.getCsmDisplayField(), detailDTO.getCsmDisplayValue() , taskid,storeNO) );
		}
		logger.info("insertCSMTaskDetail", "List of MAP pf parameter created ");
		Map<String, ? super Object> paramsTemp[] = new HashMap[0];
		int updateCount= super.batchUpdate(storeNO, sql,params.toArray(paramsTemp)).length;
		logger.debug("insertCSMTaskDetail", "ENDED OK");
		return updateCount;
	}


	public List<Map<String, Object>> getCSMTaskList(String storeNumber,String status) throws DJException {
		logger.debug("getCSMTask", "Start  with param storeNumber/status"+storeNumber+"/"+status);
		//TODO need to move to constant file 
		String sql=PropertyUtils.getProperty("select.from.csm_task") ;
		//String sql="SELECT CSM_TASK_ID,STORE_NO,T.TASK_TYPE_ID AS TASK_TYPE_ID ,REQ_ID,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE,CSM_TASK_STATUS,TASK_DESCRIPTION FROM CSM_TASK T,CSM_TASK_TYPE TT WHERE STORE_NO=:STORE_NO AND CSM_TASK_STATUS=:CSM_TASK_STATUS AND T.TASK_TYPE_ID=TT.TASK_TYPE_ID ORDER BY CREATED_DATE";
		
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put(STORE_NUMBER.name(), storeNumber);
		paramMap.put(CSM_TASK_STATUS.name(), status);
		return (List<Map<String, Object>>) queryForList(storeNumber, sql, paramMap);
	}
	
		
	public int updateCSMTask(long taskId, String storeNumber, String status,String updatedBy,String updatedDate)throws DJException {
		logger.info("updateCSMTask ", "Param storeNumber/status "+storeNumber+"/"+status);
		
		String sql=PropertyUtils.getProperty("update.csm_task");
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put(CSM_TASK_STATUS.name(), status);
		param.put(CSM_TASK_ID.name(), taskId);
		param.put(UPDATED_BY.name(), updatedBy);
		param.put(UPDATED_DATE.name(), updatedDate);	
		int taskid=update(storeNumber, sql, param);
		logger.debug("updateCSMTask ", "Ended OK, updated taskId: "+taskid);
		return taskid;
	}
	
	
	// Private Method
	private Map<String, Object> getCSMTaskDetailsColumns(String columnIdentifier,Object coulmnValue,int csmTaskId,String storeNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(CSM_TASK_ID.name(), csmTaskId);
		param.put(STORE_NUMBER.name(), storeNo);
		param.put(CSM_DISPLAY_FIELD.name(), columnIdentifier);
		param.put(CSM_DISPLAY_VALUE.name(), coulmnValue);
		return param;
	}

	public List<Map<String, Object>> getCSMTask(String storeNumber,int taskId) throws DJException {
		logger.debug("getCSMTask", "Start  with param storeNumber/taskId"+storeNumber+"/"+taskId);
		//TODO need to move to constant file 
		String sql=PropertyUtils.getProperty("select.from.csm_task.forTaskId") ;
		
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put(STORE_NO.name(), storeNumber);
		paramMap.put(CSM_TASK_ID.name(), taskId);
		return (List<Map<String, Object>>) queryForList(storeNumber, sql, paramMap);
	}

	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.dao.MODWebServiceDAO#closeAllCSMOpenTasks(java.lang.String)
	 */
	public int closeAllCSMOpenTasks(String dateForclosingCSMTasks) throws DJException{
		logger.debug("Starting of closeAllCSMOpenTasks() for date:=== ", dateForclosingCSMTasks);
		String sql=PropertyUtils.getProperty("update.open.csm.tasks") ;
		int noOfRowsUpdated=0;
		Map<String,Object> paramMap=new HashMap<String, Object>();
		List<NamedParameterJdbcTemplate> namedParameterJdbcTemplatesList=getNamedParameterJdbcTemplates();
		for(NamedParameterJdbcTemplate namedParameterJdbcTemplate:namedParameterJdbcTemplatesList){
			noOfRowsUpdated+=namedParameterJdbcTemplate.update(sql, paramMap);
		}
		logger.debug("Ending of closeAllCSMOpenTasks() for date:=== "+dateForclosingCSMTasks+" with number of tasks closed:=== ",noOfRowsUpdated);
		return noOfRowsUpdated;
	}
	
	public int getCSMTaskId(String store,int taskTypeId,String rqtId,String status,String rqdId) throws DJException{
		try{
			logger.debug("Starting of getCSMTaskId()=== ","");
			String sql=PropertyUtils.getProperty("get_csm_task_id") ;
			Map<String,Object> paramMap=new HashMap<String, Object>();
			paramMap.put(STORE.name(), store);
			paramMap.put(TASK_TYPE_ID.name(), taskTypeId);
			paramMap.put(RQT_ID.name(), rqtId);
			paramMap.put(CSM_TASK_STATUS.name(), status);
			paramMap.put(RQD_ID.name(), rqdId);
			
			logger.debug("Ending of getCSMTaskId()=== ","");
			return queryForInt(store, sql, paramMap);

		}catch(Exception accessException){
			return 0;
		}
		
	}
	
	public void updateCSMTaskForCreateDate(int taskId,String store) throws DJException{
		logger.debug("Starting of updateCSMTaskForCreateDate()=== ","");
		String sql=PropertyUtils.getProperty("update_date_for_task") ;
		Date currentTime=Calendar.getInstance().getTime();
		Timestamp tsNw = new Timestamp(currentTime.getTime());
		
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put(STORE.name(), store);
		paramMap.put(CREATED_DATE.name(),tsNw);
		paramMap.put(CSM_TASK_ID.name(), taskId);
		update(store, sql, paramMap);
		logger.debug("Ending of getCSMTaskId()=== ","");
		
	}

	
	
	public List<String> beepToPrinter(String store, String storeFmt,String printerId, String kiosk) throws DJException {
		String serverURL =null;
		ResponseEntity<OrderAdaptorResponse> response = null;
		List<String> status = new ArrayList<String>();
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		request.setRequestType(OrderAdaptorRequest.PUSH_ORDER);
		Printer printer = new Printer();
		printer.setPrinterId(printerId);
		Order order = new Order();
		order.setKioskName(kiosk);
		request.setPrinter(printer);
		request.setRequestOrder(order);
		String dnsName = MPUWebServiceUtil.getDNSForStore(store,storeFmt);
		logger.info("the dns fro beep is ", dnsName);
		serverURL = dnsName+"/setKioskPrinter";
		
		if (serverURL != null) {
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
			logger.info("the url for beep is ", serverURL);
			response = restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
		}
		
		if (response.getBody().getStatus().getMessage() != null) {
			status.add(response.getBody().getStatus().getMessage());
		}
		return status;
	}
	
	public String subscribeCSMToNPOS(String store, String storeFmt) throws DJException {
		String serverURL =null;
		String status = "SUCCESS";
		OrderAdaptorRequest request=new OrderAdaptorRequest();
		request.setRequestType(OrderAdaptorRequest.PUSH_ORDER);
		String dnsName = MPUWebServiceUtil.getDNSForStore(store,storeFmt);
		
		serverURL = dnsName+"/modSubScribe";
		logger.info("dns for mod subscribe is ", serverURL);
		if (serverURL != null) {
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
			restTemplate.put(serverURL, requestEntity);
		}
		return status;
	}
	
	public void unSubscribeNPOSForStore(Order order, String dns) throws Exception {
		logger.info("Inside ", "unSubscribeNPOSForStore");
		OrderAdaptorRequest request = new OrderAdaptorRequest();
		ResponseEntity<OrderAdaptorResponse> response = null;
		String status = "FAILURE";
		String serverURL = dns+"/modUnSubScribe";
		request.setRequestOrder(order);
		logger.info("","ServerURL for  update NPOS ..."+serverURL);	
		if (serverURL != null) {
			HttpEntity<OrderAdaptorRequest> requestEntity = new HttpEntity<OrderAdaptorRequest>(request);
			//restTemplate.postForEntity(serverURL, requestEntity, OrderAdaptorResponse.class);
			restTemplate.put(serverURL, requestEntity);
		}		
	}
}
