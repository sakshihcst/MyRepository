package com.searshc.mpuwebservice.processor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sears.dj.common.exception.DJException;
import com.sears.dj.common.logging.DJLogger;
import com.sears.dj.common.logging.DJLoggerFactory;
import com.sears.dj.common.service.DJService;
import com.searshc.mpuwebservice.bean.CSMTaskDTO;
import com.searshc.mpuwebservice.bean.ItemDTO;
import com.searshc.mpuwebservice.constant.MpuWebConstants;
import com.searshc.mpuwebservice.dao.MPUWebServiceDAO;
import com.searshc.mpuwebservice.processor.ActiveUserProcessor;
import com.searshc.mpuwebservice.processor.ExpiredOrderProcessor;
import com.searshc.mpuwebservice.processor.MODNotificationProcessor;
import com.searshc.mpuwebservice.processor.MPUWebServiceMODProcessor;
import com.searshc.mpuwebutil.util.CommonUtils;


@DJService("modNotificationProcessorImpl")
public class MODNotificationProcessorImpl implements MODNotificationProcessor {
	
/*	@Autowired
	private MPUWebServiceProcessor mpuWebServiceProcessorImpl;
*/	
	@Autowired
	private MPUWebServiceDAO mpuWebServiceDAOImpl;
	
	@Autowired
	@Qualifier("MPUWebServiceMODProcessorImpl")
	MPUWebServiceMODProcessor mPUWebServiceMODProcessorImpl;
	
	@Autowired
	private ActiveUserProcessor activeUserProcessor;
	
	@Autowired
	ExpiredOrderProcessor  expiredOrderProcessor;
	
/*	@Autowired
	private EhCacheCacheManager cacheManager;*/
	
	private static transient DJLogger logger = DJLoggerFactory.getLogger(MODNotificationProcessorImpl.class);
	
	private Map<String, String> mODNotification = new HashMap<String, String>();



	@Async("expiryExecutor")
	public void postProcessing(List<String> rqtList,List<String> rqdList,HashMap<String,String> mapping,String strNum,HashMap<String,String> requestTypeMap,String queueType,
			List<Map<String, ItemDTO>> expiredHGItems ,List<ItemDTO> modlist) throws DJException {
		logger.info("entering postProcessing", "");
		logger.error("Processing thread is"+Thread.currentThread().getName(),"");
		try{
			if(!CollectionUtils.isEmpty(modlist)){
				notifyMODForEligibleList(strNum, modlist);	
			}
			
			if(!CollectionUtils.isEmpty(rqdList)){
				expiredOrderProcessor.expireOrder(rqtList,rqdList, mapping,strNum,requestTypeMap, queueType,expiredHGItems );
			}
		}catch(Exception exp){
			logger.error("Exception in postProcessing = ", exp);
		}
		
		
		logger.info("exiting postProcessing", "");
	}
	
	public void notifyMODForEligibleList(String storeNo,List<ItemDTO> itemdtoList) throws DJException{
		logger.info("entering mpuwebserviceprocessorImpl.notifyMOD","");
		int itemListSize = itemdtoList.size();
		try{
			String activeUsers = activeUserProcessor.getActiveUserForMOD(storeNo);
			boolean isModActive = (null != activeUsers && !"".equalsIgnoreCase(activeUsers)) ? true : false;
			logger.error("the mod flag is "+isModActive+"---"+itemListSize,"" );
			if(isModActive){
				
				
		for(int i =0;i<itemListSize;i++){
			ItemDTO itemdto = itemdtoList.get(i);
			String timeElapsed = itemdto.getExpireTime();
			String[] timeArray=timeElapsed.split(":");
			int elapsedMinutes =  Integer.parseInt(timeArray[0])*60+(Integer.parseInt(timeArray[1]))+(Integer.parseInt(timeArray[2]))/60;
			
			Map<String,Object> storeInfo=mpuWebServiceDAOImpl.getStoreDetails(itemdto.getStoreNumber());
		//	logger.debug("notifyMOD : storeInfo", storeInfo);
			String escalationTime="";
			int escalation_Time=60;
			if(storeInfo!=null){
				escalationTime=(String) storeInfo.get(MpuWebConstants.ESCALATION_TIME);
			}
			if(escalationTime!=null){
				escalation_Time= Integer.parseInt(escalationTime);

			}
			int notificationType=CommonUtils.getModNotificationType(elapsedMinutes,escalation_Time,itemdto.getRequestType());
			if(notificationType!=0){
				//logger.info("Sending MOD Notification for ITEM","itemdto=="+itemdto.toString());
				sendMODNotification(itemdto,notificationType);
			}
		}
		
			}
		}catch(Exception exp){
			logger.error("Exception in notifyMODForEligibleList ==", exp);
		}
		
		
		logger.info("exiting mpuwebserviceprocessorImpl.notifyMOD","");
	}
	
	
	 public  synchronized void sendMODNotification(ItemDTO itemDTO,int taskType) throws DJException{
		   /*if(itemDTO.getStoreNumber().length()>4){
			   String StoreNumber=itemDTO.getStoreNumber().substring(1,5);
			   itemDTO.setStoreNumber(StoreNumber);
		   }*/
	    	logger.info("Send MOD for :",itemDTO.getRqtId());
	    	HashMap<String,String> map =getMapFromItemDTO(itemDTO,taskType); 
	    			//mpuWebServiceProcessorImpl.getMapFromItemDTO(itemDTO);
	    	
				//if(mpuWebServiceDAOImpl.isModActive(itemDTO.getStoreNumber())){
							String mapKey=itemDTO.getRqdId();
							String mapStatus = this.mODNotification.get(mapKey);
					
					if((taskType==8) || (taskType ==11)){// task 16 is for coupon bypass notification
							//OOS actionID
						getCSMTaskId(taskType,itemDTO,map);
					}
					else if(taskType==6 || taskType==17/*(17 is for RI 30 min)*/){
						if(mapStatus==null || ("3").equalsIgnoreCase(mapStatus)){
							//30-45 warning range
							/*CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							webServiceMODProcessor.insertCSMTask(csmTaskDTO);*/
							logger.info("sendMODNotification == ", "Before addCSMTask");
							mPUWebServiceMODProcessorImpl.addCSMTask(taskType,Integer.parseInt(itemDTO.getRqtId()),
									itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							this.mODNotification.put(mapKey,"1");
						}
					}else if(taskType==7 || taskType==18/*(17 is for RI 45 min)*/){
						if(("1").equalsIgnoreCase(mapStatus)){
							//45-60 warning range
							/*CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							webServiceMODProcessor.insertCSMTask(csmTaskDTO);*/
							logger.info("sendMODNotification == ", "Before addCSMTask");
							mPUWebServiceMODProcessorImpl.addCSMTask(taskType,Integer.parseInt(itemDTO.getRqtId()),
									itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							this.mODNotification.put(mapKey,"2");
						}
					}else if(taskType==2 || taskType==19/*(17 is for RI 60 min)*/){
						if(("2").equalsIgnoreCase(mapStatus)){
							//60-90 warning range
							/*CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							webServiceMODProcessor.insertCSMTask(csmTaskDTO);*/
							logger.info("sendMODNotification == ", "Before addCSMTask");
							mPUWebServiceMODProcessorImpl.addCSMTask(taskType,Integer.parseInt(itemDTO.getRqtId()),
									itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
							this.mODNotification.put(mapKey,"3");
						}
					} else if((taskType ==4) || (taskType ==16) || (taskType ==10) || (taskType ==15)){ // task 4 Not Deliver Notification and task 16 coupon bypass, task 15 for Sign override 
						//OOS actionID
					//getCSMTaskId(taskType,itemDTO,map);
						CSMTaskDTO csmTaskDTO = mPUWebServiceMODProcessorImpl.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
						mPUWebServiceMODProcessorImpl.insertCSMTask(csmTaskDTO);
				}
				//}
				/**
				 * This is outside the if check for active MOD
				 * Since it does'nt matter if the mod is active or not
				 * MPU service notification for mod will be inserted and mod will 
				 * see when he/she logs in.
				 */
				if(taskType==9){
					//For MPU Service Warning
					logger.info("Inside Mpu Warning For Mod", "");
					map.put("MESSAGE",MpuWebConstants.MPU_SERVICE_WARNING_MSG);
					/*CSMTaskDTO csmTaskDTO = webServiceMODProcessor.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
					webServiceMODProcessor.insertCSMTask(csmTaskDTO);*/
					mPUWebServiceMODProcessorImpl.addCSMTask(taskType,Integer.parseInt(itemDTO.getRqtId()),
							itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
				}
				
				mPUWebServiceMODProcessorImpl.getCacheDirty(itemDTO.getStoreNumber());
			}
	
			    public HashMap<String, String> getMapFromItemDTO(ItemDTO itemDTO,int taskType) {
			    	
			    	HashMap<String,String> map = new HashMap<String,String>();
			    	if(taskType == 17 || taskType == 18 || taskType == 19){
			    		map.put("REQUEST_ID", itemDTO.getSalescheck());
			    		map.put("ITEM_DESC", itemDTO.getThumbnailDesc());
			    		map.put("ITEM", itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku());
			    		map.put("CUSTOMER_NAME", itemDTO.getFullName());
			    		map.put("PHONE_NUMBER", itemDTO.getPhoneNumber());
			    		
			    	}else{
				    	if(StringUtils.hasText(itemDTO.getFullName())){
				    		String[] nameList=itemDTO.getFullName().split(" ");
				    		if(nameList.length == 2){//code fixed for storesys 23357 
				    			map.put("FIRST_NAME",nameList[0]);
								map.put("LAST_NAME",nameList[1]); 
				    		}else{
				    			map.put("FIRST_NAME",nameList[0]);
				    		}
				    	}
				    	map.put("PHONE_NUMBER", itemDTO.getPhoneNumber());
						map.put("SALESCHECK", itemDTO.getSalescheck()); 
						map.put("ITEM_DESC", itemDTO.getThumbnailDesc()); 
						map.put("ITEM",itemDTO.getDivNum()+itemDTO.getItem()+itemDTO.getSku()); 
						if(taskType == 10)
						{
							map.put("MESSAGE", itemDTO.getCommentList());
						}
						else{
							map.put("MESSAGE", "");
						}
						map.put("REQ_QUANTITY", itemDTO.getQty());
						map.put("CREATED_DATE", itemDTO.getItemCreatedDate());
						map.put("ITEM_ID", itemDTO.getRqdId());
						map.put("ASSOCIATE_NAME", mPUWebServiceMODProcessorImpl.getUserName(itemDTO.getAssigneeFullName()));
						map.put("ALT_PH_NUMBER", itemDTO.getAltPhoneNumber());
						map.put("BIN_NUMBER", itemDTO.getItemBinNumber());
						map.put("NOT_DELIVER_QUANTITY", itemDTO.getItemQuantityNotDelivered());
						map.put("NOT_DELIVER_REASON", itemDTO.getCommentList());
			    		
			    	}

					
					return map;
				}

			    /**Service to check if there is any open request in the csm_task table for the task_type=8 or task_type=11.
			     * if found then update the create date of the record else insert the new record 
			     * 
			     * @param taskType
			     * @param itemDTO
			     * @param map
			     * @throws DJException
			     */
			    public void getCSMTaskId(int taskType,ItemDTO itemDTO,HashMap<String,String> map) throws DJException{
			    	int taskId = mPUWebServiceMODProcessorImpl.getCSMTaskId(itemDTO.getStoreNumber(), taskType, itemDTO.getRqtId(),MpuWebConstants.OPENSTATUS,itemDTO.getRqdId());
			    	if(taskId==0){
						CSMTaskDTO csmTaskDTO = mPUWebServiceMODProcessorImpl.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
						mPUWebServiceMODProcessorImpl.insertCSMTask(csmTaskDTO);
			    	}else{
			    		//mark existing record as completed
			    		mPUWebServiceMODProcessorImpl.updateCSMTaskForCreateDate(taskId, itemDTO.getStoreNumber());
			    		
						CSMTaskDTO csmTaskDTO = mPUWebServiceMODProcessorImpl.createCSMTaskDTO(taskType,Integer.parseInt(itemDTO.getRqtId()), itemDTO.getStoreNumber(), itemDTO.getFullName(), map);
						mPUWebServiceMODProcessorImpl.insertCSMTask(csmTaskDTO);

			    	}
			    }
			    
			    
			    public void updateCsmTask(int taskType,ItemDTO itemDTO) throws DJException{
			    	logger.info("entering updateCsmTask for: ",itemDTO.getRqtId());
			    	int taskId = mPUWebServiceMODProcessorImpl.getCSMTaskId(itemDTO.getStoreNumber(), taskType, itemDTO.getRqtId(),MpuWebConstants.OPENSTATUS,itemDTO.getRqdId());
			    	mPUWebServiceMODProcessorImpl.updateCSMTaskForCreateDate(taskId, itemDTO.getStoreNumber());
			    	
			    	mPUWebServiceMODProcessorImpl.getCacheDirty(itemDTO.getStoreNumber());
			    	}
			    
			    
			    public synchronized String getCSMTaskMapStatus(String mapKey){
			    	String mapStatus = this.mODNotification.get(mapKey);
			    	
			    	return mapStatus;
			    }
			    
			    
			    public synchronized void setCSMTaskMapStatus(String mapKey,String val){
			    	
			    	this.mODNotification.put(mapKey,val);
			    	
			    }

}
