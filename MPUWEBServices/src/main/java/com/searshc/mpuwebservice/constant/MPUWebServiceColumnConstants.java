package com.searshc.mpuwebservice.constant;


/**
 * This class contains the constants that are used in the application.
 * 
 * @author Tata Consultancy Services
 * @version 1.0
 * @since 
 */
public enum MPUWebServiceColumnConstants{
	
	CREATE_TIME,CREATE_TIMESTAMP, CREATED_BY, KIOSK_NAME, MOD_ENABLED, SELL_OF_ACC_FLAG, HOLD_GO_FLAG, LOCKER_ENABLED, MODIFIED_BY, ORIGINALJSON, PARENT_SALESCHECK, RETURN_PARENT_ID, PICKUP_ASSIGNEE, PICKUP_END_TIME, PICKUP_START_TIME, PICKUP_STATUS, REQUEST_COUNT, REQUEST_NUMBER, REQUEST_STATUS, REQUEST_TYPE, RQT_ID, SALESCHECK, STORE_NUMBER,PKG_NBR,STORE_FORMAT, UPDATE_TIMESTAMP,
	ADDRESS1, ADDRESS2,CUSTOMER_ID, EMAIL, FIRST_NAME, LAST_NAME, PHONE,  SYW_NUMBER, SYW_STATUS, ZIPCODE,RETURNAUTHCODE,
	ASSIGNED_USER, DIV_NUM, ESCALATION, ESCALATION_TIME, FROM_LOCATION, FROM_LOCATION_BARCD, FULL_NAME, ITEM, ITEM_IMAGE, ITEM_SEQ, ITEM_STATUS, KSN,  PLUS4, QTY, TRANSDETAILID, RQD_ID,  SKU, STOCK_LOCATION, STOCK_QUANTITY, STORE, THUMBNAIL_DESC, TO_LOCATION, TO_LOCATION_BARCD, UPC,TOTAL,DELIVERED_QUANTITY,
	TYPE, ACCOUNT_NUMBER, AMOUNT, STATUS, PAYMENT_DATE, EXPIRATION_DATE, TASKTYPE, ACKNOWLEDGEMENT, TIMERECEIVED, EXPIRETIMESTRING, COUPONISSUED, PUBLISHERDEVICENAME, TARGETDEVICENAME, EXPIRETIME, TARGETROLE, TASKLOCATION, KIOSKLOCATION, PAGINGFAILED, 
	PRINTINGFAILED, NPOSUNAVAILABLE, COMPLETEDTIME, LANGUAGE, PRIORITY, STATE, VALUE,ENTRY_TYPE,ACTION_SEQ,ACTIVITY_DESCRIPTION,ITEM_ID,PIN_NO,LOCKER_NO,TRANSACTION_DATE,REFERENCE_ID,PIN_RECIEVED_DATE,PICKEDUP_INITIATED_DATE,PICKEDUP_DATE,CUSTOMER_NAME,CREATED_DATE,UPDATED_DATE,UPDATED_BY,STORE_NO,SALESCHECK_NO,ID,
	PHONE_NUMBER,ALT_PHONE_NUMBER,AREA_CODE,ALT_AREA_CODE,PACKAGE_NUM,PACKAGE_BIN,PACKAGE_NUMBER,NOT_DELIVERED_QTY,IDENTIFIER_VALUE,INSTALLER_FLAG,UNSECURED_FLAG,ORDER_DATE,REMAINING_QUANTITY,CUSTOMER_TYPE,ADDRESS1_TYPE,ADDRESS2_TYPE,ITEM_STATUS1,ITEM_STATUS2,LAYAWAY_FLAG,PACKAGE_ID,
	PICKUP_ID,REGION,DISTRICT,PICKUP_REQUEST_TYPE,REQ_QUANTITY,PICKEDUP_QUANTITY,SC_SCAN,DATE_TO,DATE_FROM,
	// CSM Related column
	TASK_TYPE_ID,TASK_DESCRIPTION,CSM_TASK_ID,CSM_TASK_STATUS,CSM_DISPLAY_FIELD,CSM_DISPLAY_VALUE,VER,LOCKER_ELIGIBLE,ORDER_SOURCE, PICKED_UP, PICKUP_INITIATED,MOD_FLAG,LOGGED_OUT_TIME,
	/* CSM Related column */
	
	//Pickup support report Related column
	DIV_ITEM_SKV,DURATION,
	//Pickup support report Related column
	
	//Pickup support report Related column
	DIRECT_PICKUP,STAGED_PICKUP,RETURNED,RETURNIN5,HELP,REPAIR_PICKUP,REPAIR_DROPOFF,
	//Pickup support report Related column

	/* Activity Report Related column */
	ASSOCIATE_ID,WORK_STATUS,ITEM_IDENTIFIER,PERIOD_CODE,BINNED_COUNT,OS_COUNT,NR_COUNT,START_TIME,END_TIME,CARD_SWIPED,SEARCH_METHOD,SEARCH_VALUE,ORIGINAL_SALESCHECK,KIOSK,TRANS_ID,REQUESTED_QUANTITY,NOT_DELIVERED_QUANTITY,TRANS_DETAIL_ID,QTY_REMAINING,IS_ACTIVE,LAYAWAY_TYPE,ITEM_SALE_TYPE,TRANSACTION_TYPE,ITEM_SALE_ORIGIN,
	REQUESTED_ITEMS,DELIVERED_ITEMS,TOTAL_PICKUP_ORDERS,TOTAL_DURATION,LONGEST_DURATION,SHORTEST_DURATION,HELP_REQUESTS,TOTAL_CANCELLED,TOTAL_EXCHANGES,TOTAL_COUPONS,TOTAL_ON_TIME,TOTAL_RETURNS,TOTAL_EXCHANGES_REQUESTED,TOTAL_EXCHANGES_COMPLETED,COUPON_GENERATED,ASSOCIATE_NAME,
	USER_ID,ACTIVE_USER_ID,HOSTNAME,WEBSOCKET_PORT,MOD,GIVEN_NAME,LOGGED_TIME,ACTIVE_USER_FLAG,IDENTIFIER_TYPE,PICKUP_SOURCE,CAPTAIN_FLAG,ACTIVE_KIOSK,INRANGE,SOCKET_HOST,LOGGED_IN_TIME,SEARS_SALES_ID,
	USER_FNAME,USER_LNAME,COUPONINDICATOR,MXTIME,VEHICLE,TODAY_DATE,PHONENO,TIME,ASSIGNEDUSER,QTY_SHIPPED,TIME_ZONE,FFM_TYPE,
	/* MPU Associate Report Related column */
	MPU_ASSOCIATE_REPORT_CREATED_TS, OPERATION_TYPE, OPERATION_VALUE, INPUT_TYPE, ORDER_TYPE, WORK_ITEM_STATUS,COMPLETED,CLOSED,
	/*Exchange Related column*/
	RETURN_AUTH_CODE
	;
	
	
	
	
}

//CREATE_TIMESTAMP, CREATED_BY, KIOSK_NAME, MODIFIED_BY, ORIGINALJSON, PICKUP_ASSIGNEE, PICKUP_END_TIME, PICKUP_START_TIME, PICKUP_STATUS, REQUEST_COUNT, REQUEST_NUMBER, REQUEST_STATUS, REQUEST_TYPE, RQT_ID, SALESCHECK, STORE_NUMBER,PKG_NBR,STORE_FORMAT, UPDATE_TIMESTAMP,>>>>>>> .r40863