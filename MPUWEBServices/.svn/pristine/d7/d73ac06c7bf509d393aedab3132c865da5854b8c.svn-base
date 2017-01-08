package com.searshc.mpuwebservice.processor.impl;

import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_DISPLAY_FIELD;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_DISPLAY_VALUE;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.CSM_TASK_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.RQT_ID;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TASK_DESCRIPTION;
import static com.searshc.mpuwebservice.constant.MPUWebServiceColumnConstants.TASK_TYPE_ID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sears.dej.interfaces.vo.UserVO;
import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.service.DJService;
import com.searshc.mpuwebservice.bean.CSMTaskDTO;
import com.searshc.mpuwebservice.bean.CSMTaskDetailDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.AssociateActivityServiceDAO;
import com.searshc.mpuwebservice.dao.MODWebServiceDAO;
import com.searshc.mpuwebservice.processor.ActiveUserProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceMODProcessor;
import com.searshc.mpuwebservice.util.PropertyUtils;

/**
 * 
 * @author gauti
 * @description This is business layer for CSM functionality and business implementation
 */

	@DJService("MPUWebServiceMODProcessorImpl")
	public class MPUWebServiceMODProcessorImpl implements MPUWebServiceMODProcessor {
	
	@Autowired
	private MODWebServiceDAO modWebServiceDAOImpl;
	@Autowired
	private AssociateActivityServiceDAO associateActivityServiceDAOImpl;

	@Autowired
	private EhCacheCacheManager cacheManager; 
	
	@Autowired
	private ActiveUserProcessor activeUserProcessor;
	
	@Autowired
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MPUWebServiceMODProcessorImpl.class);
	
	
	//@Transactional(readOnly = false, propagation=Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public int insertCSMTask(CSMTaskDTO csmTaskDTO) throws DJException {
		// insert CSM details to CSM_TASK table
		int taskId=modWebServiceDAOImpl.insertCSMTask(csmTaskDTO);
		//insert CSM Display and key fields in CSM_TASK_DETAIL table
		logger.info("insertCSMTask", "Added details to CSM task now inserting to CSM Task Details");
		modWebServiceDAOImpl.insertCSMTaskDetail(csmTaskDTO.getCsmTaskDetailDTO(), taskId,csmTaskDTO.getStoreNo());
		logger.info("Added CSMTaskDetails", "Ended OK");
		return taskId;
	}
	
	//@Transactional(readOnly = true,propagation=Propagation.REQUIRES_NEW,  rollbackFor = Exception.class)
	public List<CSMTaskDTO> getCSMTaskList(String storeNumber,String status)throws DJException {
 		logger.info("getCSMTaskList", "Start fprparam storeNumber/status "+storeNumber+"/"+status);
		List<CSMTaskDTO> csmTaskListReturn=new ArrayList<CSMTaskDTO>();
		
		List<Map<String, Object>> taskMap=getCSMTaskFromcache(storeNumber);
		if(CollectionUtils.isEmpty(taskMap)){
			taskMap = modWebServiceDAOImpl.getCSMTaskList(storeNumber, status);
			addCSMtoCache(taskMap, storeNumber);
			
		}
				
		if(null==taskMap){
			throw new DJException("error at getCSMTaskList: modWebServiceDAPImpl.getCSMTask returned  null task map");
		}
		// Merging CSMTaskDetailDTO into CSMTaskDTO 
		csmTaskListReturn=populateAllCSMDetails(taskMap);
		logger.info("getCSMTaskList", "End total CSM task returning :"+csmTaskListReturn.size());
		return csmTaskListReturn;
	}

	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRES_NEW,  rollbackFor = Exception.class)
	public int updateCSMTask(long taskId,String storeNumber, String status,String updatedBy,String updatedDate)
			throws DJException {
		// TODO Auto-generated method stub
		int count = modWebServiceDAOImpl.updateCSMTask(taskId, storeNumber, status, updatedBy,updatedDate);
		if(count>0){
			getCacheDirty(storeNumber);
		}
		return count;
	}
	
	//TODO NEED TO REMOVE.. KEPT FOR TESTING PURPOSE
	public CSMTaskDTO getCSMTaskDetail(String storeNumber, int taskId)
			throws DJException {
		/*
		List<Map<String, Object>> taskMap=modWebServiceDAPImpl.getCSMTask(storeNumber, taskId);
		
		if(null==taskMap){
			throw new DJException("error at getCSMTaskDetail: modWebServiceDAPImpl.getCSMTaskDetail returned  null task map");
		}
		CSMTaskDTO csmTaskDTO= new CSMTaskDTO();
		List<CSMTaskDetailDTO> csmTaskDetailList=new ArrayList<CSMTaskDetailDTO>();
		
		String[] taskIdList={""+taskId};
		for(Map<String, Object> map:taskMap){
			
			csmTaskDTO.setTaskId((Integer)map.get("CSM_TASK_ID"));
			csmTaskDTO.setTasktypeId((Integer)map.get("TASK_TYPE_ID"));
			csmTaskDTO.setReqId((Integer)map.get("REQ_ID"));
			csmTaskDTO.setTaskDescription((String)map.get("TASK_DESCRIPTION"));
		}
		// fetching display details for every CSM task
		List<Map<String, Object>> taskDetailsMap=modWebServiceDAPImpl.getCSMTaskDetail(taskIdList,storeNumber);
				
		//populating CSMTaskDetailDTO for retreived details
		for(Map<String, Object> map:taskDetailsMap){
			CSMTaskDetailDTO csmTaskDetailDTO= new CSMTaskDetailDTO();
			csmTaskDetailDTO.setCsmTaskId((Integer)map.get("CSM_TASK_ID"));
			csmTaskDetailDTO.setCsmDisplayField((String)map.get("CSM_DISPLAY_FIELD"));
			csmTaskDetailDTO.setCsmDisplayValue((String)map.get("CSM_DISPLAY_VALUE"));
			csmTaskDetailList.add(csmTaskDetailDTO);
		}
		
		csmTaskDTO.setCsmTaskDetailDTO(csmTaskDetailList);
	*/	return null;
	}
	
	
	// private methods
	
	/**
	 * This method creates List<CSMTaskDTO> for MOD Tasks according to passed map
	 * @param taskDetailsMap List<Map<String, Object>
	 * @return List<CSMTaskDTO>
	 */
	private List<CSMTaskDTO> populateAllCSMDetails(List<Map<String, Object>> taskDetailsMap){
		Map<Long,CSMTaskDTO> csmTaskDetails =  new TreeMap<Long, CSMTaskDTO>();
		List<CSMTaskDTO> csmTaskDTOs = new ArrayList<CSMTaskDTO>();
		List<CSMTaskDetailDTO> csmTaskDetailDTOs = null;
		CSMTaskDetailDTO csmTaskDetailDTO=null;
		CSMTaskDTO csmTaskDTO=null;
		long csmTaskId=0;
		for(Map<String, Object> map:taskDetailsMap){
			csmTaskDetailDTOs = new ArrayList<CSMTaskDetailDTO>();
			csmTaskId = (Long)map.get(CSM_TASK_ID.name());
			if(csmTaskDetails.containsKey(csmTaskId)){
				csmTaskDTO = csmTaskDetails.get(csmTaskId);
				csmTaskDetailDTOs =  csmTaskDTO.getCsmTaskDetailDTO();
				
			}else{
				csmTaskDTO= new CSMTaskDTO();
				csmTaskDTO.setTaskId(csmTaskId);
				csmTaskDTO.setReqId((Integer)map.get(RQT_ID.name()));
				csmTaskDTO.setTaskDescription((String)map.get(TASK_DESCRIPTION.name()));
				csmTaskDTO.setTasktypeId((Long)map.get(TASK_TYPE_ID.name()));
				csmTaskDTO.setCsmTaskDetailDTO(csmTaskDetailDTOs);
			}
			csmTaskDetailDTO= new CSMTaskDetailDTO();
			csmTaskDetailDTO.setCsmTaskId(csmTaskId);
			csmTaskDetailDTO.setCsmDisplayField((String)map.get(CSM_DISPLAY_FIELD.name()));
			/**
			 * For preventing the Zoom out of the CSM screen
			 */
			String csmDisplayValue = (String)map.get(CSM_DISPLAY_VALUE.name());
			if("ITEM_DESC".equalsIgnoreCase(csmTaskDetailDTO.getCsmDisplayField()) && null!=csmDisplayValue && csmDisplayValue.length()>30){
				csmDisplayValue = csmDisplayValue.substring(0, 30);
			}
			csmTaskDetailDTO.setCsmDisplayValue(csmDisplayValue);
			csmTaskDTO.getCsmTaskDetailDTO().add(csmTaskDetailDTO);
			csmTaskDetails.put(csmTaskId, csmTaskDTO);
		}
		Set<Long> keys= csmTaskDetails.keySet();
		for(Long key:keys){
			csmTaskDTOs.add(csmTaskDetails.get(key));
		}
		return csmTaskDTOs;
	}
	
	
	
	public String validateCSMInsertInputs(CSMTaskDTO csmTaskDTO)
			throws DJException {
		String validationOK=PropertyUtils.getProperty("mpu.csm.validation.ok");
		if(csmTaskDTO==null){
			return PropertyUtils.getProperty("mpu.csm.input.null.CSMTaskDTO");
		}if(csmTaskDTO.getTasktypeId()<=0){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.taskTypeId");
		}if(csmTaskDTO.getStoreNo()==null||csmTaskDTO.getStoreNo().isEmpty()){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.storeNo");
		}if(csmTaskDTO.getReqId()<0){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.ReqId");
		}
		return validationOK;
	}
	
	public String validateCSMUpdateInputs(CSMTaskDTO csmTaskDTO)
			throws DJException {
		String validationOK=PropertyUtils.getProperty("mpu.csm.validation.ok");
		
		if(csmTaskDTO.getTaskId() <=0 ){
			return  PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.taskId");
		}
		if(csmTaskDTO.getStoreNo() ==null || csmTaskDTO.getStoreNo().trim().isEmpty()){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.storeNo");
		}
		if(csmTaskDTO.getCsmTaskStatus()==null || csmTaskDTO.getCsmTaskStatus().trim().isEmpty()){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.status");
		}
		if(csmTaskDTO.getUpDatedBy() ==null || csmTaskDTO.getUpDatedBy().trim().isEmpty()){
			return PropertyUtils.getProperty("mpu.csm.input.nullOrInvalid.updatedBy");
		}
		return validationOK;
	}
	
	public CSMTaskDTO createCSMTaskDTO(int taskTypeId,int reqId,String storeNo,String userName,HashMap<String,String> map) throws DJException{
		CSMTaskDTO csmDTO= new CSMTaskDTO();
		csmDTO.setStoreNo(storeNo);
		csmDTO.setTasktypeId(taskTypeId);
		csmDTO.setReqId(reqId);
		csmDTO.setCreatedDate(getCurrentDate());
		//logger.info("Inside create mod note",userName);
		csmDTO.setCreatedBy(userName);
		csmDTO.setCsmTaskStatus(MpuWebConstants.OPEN);
		
		List<CSMTaskDetailDTO> csmDetailTaskList= new ArrayList<CSMTaskDetailDTO>();
		for(String key:map.keySet()){
			CSMTaskDetailDTO csmDetailDTO= new CSMTaskDetailDTO();
			csmDetailDTO.setCsmDisplayField(key);
			csmDetailDTO.setCsmDisplayValue(map.get(key));
			csmDetailTaskList.add(csmDetailDTO);
		}
		csmDTO.setCsmTaskDetailDTO(csmDetailTaskList);
		return csmDTO;
	}
	private String getCurrentDate(){
		Date currentTime=Calendar.getInstance().getTime();
		Timestamp tsNw = new Timestamp(currentTime.getTime());
		return tsNw.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.searshc.mpuwebservice.processor.MPUWebServiceMODProcessor#closeAllOpenCSMTasks()
	 */
	@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public int closeAllOpenCSMTasks() throws DJException {
		String currentDate=getCurrentDate();
		int noOfTasksClosed=0;
		//logger.info("Starting of closeAllOpenCSMTasks() for date:==== ", currentDate);
		noOfTasksClosed= modWebServiceDAOImpl.closeAllCSMOpenTasks(getCurrentDate());
		//logger.info("Ending of closeAllOpenCSMTasks() for date:==== "+currentDate+" with number of CSM tasks closed is:==== ",noOfTasksClosed);
		return noOfTasksClosed;
	}
	
	/**Service to get the CSM_TASK_ID on the basis of store,task_type_id,rqt_id and status
	 * 
	 */
	public int getCSMTaskId(String store,int taskTypeId,String rqtId,String status,String rqdId) throws DJException{
		return modWebServiceDAOImpl.getCSMTaskId(store, taskTypeId, rqtId, status,rqdId);
	}
	
	/** Update the create_date , update_date,created_by and updated_by in the csm_task table
	 * 
	 * @param taskId
	 * @param store
	 * @param user
	 * @throws DJException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void updateCSMTaskForCreateDate(int taskId,String storeNumber) throws DJException{
		 modWebServiceDAOImpl.updateCSMTaskForCreateDate(taskId,storeNumber);
	}
	
//	@Async("miscExecutor")
	public void addCSMTask(int taskType, int rqtId, String storeNumber,
			String fullName, HashMap<String, String> map) throws DJException {
		logger.error("addCSMTask = "+Thread.currentThread().getName(), "");
		//String activeUsers = activeUserProcessor.getActiveUserForMOD(storeNumber);
		//boolean isModActive = (null != activeUsers && !"".equalsIgnoreCase(activeUsers)) ? true : false;
		//if(isModActive){
			CSMTaskDTO csmTaskDTO = createCSMTaskDTO(taskType,rqtId, storeNumber, fullName, map);
			insertCSMTask(csmTaskDTO);
	/*	}else{
			logger.info("addCSMTask =", "Mod is not active for the store");
		}
	*/	
	}
	public String getUserName(String userId){
		String userName=null;
		try{
		EhCacheCache associateCache =  (EhCacheCache) cacheManager.getCache("associateCache");
		//String associateName=null;
	
		
		if( userId!=null&&!"".equals(userId)&&" ".equals(userId)){
			if(associateCache.get(userId)!=null){
			userName=(String) associateCache.get(userId).get();}
		//	logger.info("getting data from cache"+userId,userName);
		
		
		if(userName==null||"".equals(userName)){
			
			
			UserVO associateInfo = associateActivityServiceDAOImpl.getAssociateInfo(userId);
			
			if(associateInfo!=null)
			{
			if(associateInfo.getGivenName()!=null){
				userName=associateInfo.getGivenName();
				
			}
			}
			
		//	userName=associateInfo.getGivenName();
			
		//	logger.info("getting data from service: "+ userId+":",userName);
			
			associateCache.put(userId.toUpperCase(), userName);
			
		}
		}
		}
		catch(Exception e){
			logger.error("Exception while fetching user name",e);
		}
		if (userName==null) userName=userId;
		return userName;
		
		
}
	
	/** MOD NOTIFICATION FOR THE REGULATORY VISIT AND REGISTER PAGE 
	 * 
	 * @param taskTypeId
	 * @param storeNumber
	 * @param nposMessage
	 * @throws DJException
	 */
	public void createMODNotificationFromNPOS(int taskTypeId, String storeNumber,String nposMessage) throws DJException{
		
		int reqId = Integer.parseInt(MpuWebConstants.ZERO);
		String userName =  MpuWebConstants.SYSTEMUSER;
		HashMap<String,String> messageMap = new HashMap<String,String>();
		messageMap.put("MESSAGE",nposMessage);
	
		//Create CSMTaskDTO
		CSMTaskDTO csmTaskDTO =	createCSMTaskDTO(taskTypeId,reqId,storeNumber,userName, messageMap);
		//Insert CSM Task into the DB					
		insertCSMTask(csmTaskDTO);
		getCacheDirty(storeNumber);
	}
	
	/**Service to get the CSMTaskDTO list from cache 
	 * 
	 * @return
	 */
	public List<Map<String,Object>> getCSMTaskFromcache(String store){
		
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("modlistcache");
		String csmListKey = store+"_CSM";
		String cacheDirtyKey = store+"_isCSMCacheDirty";
		List<Map<String ,Object>> csmList = new ArrayList<Map<String,Object>>(); 
		if(requestQueueCache!=null){
		if(null != requestQueueCache.get(cacheDirtyKey)){
			boolean isCacheDirty = (Boolean)requestQueueCache.get(cacheDirtyKey).get();
			if(!isCacheDirty){
				if(null != requestQueueCache.get(csmListKey)){
					csmList = (List<Map<String ,Object>>) requestQueueCache.get(csmListKey).get();
				}
			}/*else{
				requestQueueCache.clear();
			}*/
			
		}
		}
		return csmList;
	}
	
	/**Add the csmTaskList from the database to the cache
	 * 
	 * @param csmList
	 * @param store
	 */
	public void addCSMtoCache(List<Map<String, Object>> csmList,String store){
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("modlistcache");
		String csmListKey = store+"_CSM";
		String cacheDirtyKey = store+"_isCSMCacheDirty";
		if(requestQueueCache!=null){
			requestQueueCache.put(csmListKey, csmList);
			requestQueueCache.put(cacheDirtyKey, false);
		}
		
		
	}
	
	
    public void getCacheDirty(String store){
		EhCacheCache requestQueueCache =  (EhCacheCache) cacheManager.getCache("modlistcache");
		String cacheDirtyKey = store+"_isCSMCacheDirty";
		if(requestQueueCache!=null){
			requestQueueCache.put(cacheDirtyKey, true);
		}
    }
}
