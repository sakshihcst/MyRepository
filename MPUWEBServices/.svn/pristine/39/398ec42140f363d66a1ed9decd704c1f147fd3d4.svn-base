package com.searshc.mpuwebservice.dao;

import java.util.List;
import java.util.Map;

import com.sears.dj.common.exception.DJException;
import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.CSMTaskDTO;
import com.searshc.mpuwebservice.bean.CSMTaskDetailDTO;

public interface MODWebServiceDAO {
	
	/**
	 * @description DAO method to insert CSMTask into CSM_Task table and returns newly created task id
	 * @param csmTaskDTO
	 * @return
	 * @throws DJException
	 */
	public int insertCSMTask(CSMTaskDTO csmTaskDTO) throws DJException;
	
	/**
	 * @description DAO method to insert CSMTaskDetail into CSM_Task_Detail table
	 * @param csmTaskDetailDTO
	 * @param taskid
	 * @param storeNO
	 * @return
	 * @throws DJException
	 */
	public int insertCSMTaskDetail(List<CSMTaskDetailDTO> csmTaskDetailDTO,int taskid,String storeNO) throws DJException;
	
	/**
	 * 
	 * @param storeNumber
	 * @param status
	 * @return
	 * @throws DJException
	 */
	public List<Map<String, Object>> getCSMTaskList(String storeNumber,String status) throws DJException;
	
	
	/**
	 * @description DAO method to update CSM task status in CSM_TASK table
	 * @param TaskId
	 * @param storeNumber
	 * @param status
	 * @return int
	 * @throws DJException
	 */
	
	public int updateCSMTask(long taskId,String storeNumber,String status,String updatedBy,String updatedDate) throws DJException;

	public List<Map<String, Object>> getCSMTask(String storeNumber,int taskId) throws DJException;
	
	/**
	 * This API closes all the open tasks in csm_task table
	 * @param dateForclosingCSMTasks String
	 * @return int
	 * @throws DJException
	 */
	public int closeAllCSMOpenTasks(String dateForclosingCSMTasks) throws DJException;
	
	public void unSubscribeNPOSForStore(Order order, String serverURL) throws Exception;

	public void updateCSMTaskForCreateDate(int taskId, String storeNumber) throws DJException;

	public int getCSMTaskId(String store, int taskTypeId, String rqtId,
			String status, String rqdId) throws DJException;	
	
}
