package com.searshc.mpuwebservice.processor;

import java.util.HashMap;
import java.util.List;

import com.sears.dj.common.exception.DJException;
import com.searshc.mpuwebservice.bean.CSMTaskDTO;

public interface MPUWebServiceMODProcessor {
	
/**
 * @description method to insert CSM task with all details and returns newly created task id.
 * @param csmTaskDTO
 * @return task id
 * @throws DJException
 */
	public int insertCSMTask(CSMTaskDTO csmTaskDTO) throws DJException;
	
	
	/**
	 * @description  method to get CSM task details for specified store and status
	 * @param storeNumber
	 * @param status
	 * @return List<CSMTaskDTO>
	 * @throws DJException
	 */
	public List<CSMTaskDTO>getCSMTaskList(String storeNumber,String status) throws DJException;
	
	
	/**
	 * @description method to update status of specified CSM task.
	 * @param TaskId
	 * @param storeNumber
	 * @param updatedBy
	 * @param status
	 * @param updatedDate String
	 * @return
	 * @throws DJException
	 */
	
	public int updateCSMTask(long TaskId,String storeNumber,String status,String updatedBy,String updatedDate) throws DJException;
	
	public String validateCSMInsertInputs(CSMTaskDTO csmTaskDTO)throws DJException;
	
	
	public CSMTaskDTO getCSMTaskDetail(String storeNumber,int taskId)throws DJException;
	
	public String validateCSMUpdateInputs(CSMTaskDTO csmTaskDTO) throws DJException;
		
	
	public CSMTaskDTO createCSMTaskDTO(int taskTypeId,int reqId,String storeNo,String userName,HashMap<String,String> map) throws DJException;
	
	/**
	 * This API closes  all the open tasks in CSM_Task Table
	 * @return int 
	 * @throws DJException
	 */
	public int closeAllOpenCSMTasks() throws DJException;

	/**This API gives the csm_task_id on the basis of given parameters
	 * @param store
	 * @param taskTypeId
	 * @param rqtId
	 * @param status
	 * @return
	 * @throws DJException
	 */
	public int getCSMTaskId(String store,int taskTypeId,String rqtId,String status,String rqdId) throws DJException;
	
	/**APi to update the confirm web Sale/store transfer sale warnings 
	 * 
	 * @param taskId
	 * @param storeNumber
	 * @throws DJException
	 */
	public void updateCSMTaskForCreateDate(int taskId,String storeNumber) throws DJException;
	public String getUserName(String userId);
	
	
	/** MOD Notification to insert CSM Task data into CSM Task and CSM Task Detail.
	 * 
	 * @param taskTypeId
	 * @param reqId
	 * @param storeNo
	 * @param userName
	 * @param map
	 * @throws DJException
	 */
	public void createMODNotificationFromNPOS(int taskTypeId,String storeNo,String nposMessage) throws DJException;
	/**
	 * 
	 * @param taskId
	 * @param rqtId
	 * @param storeNumber
	 * @param fullName
	 * @param map
	 * @throws DJException
	 */
	public void addCSMTask(int taskId,int rqtId,String storeNumber,String fullName,HashMap<String,String> map ) throws DJException;
	public void getCacheDirty(String store);
}
