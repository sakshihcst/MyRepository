package com.searshc.mpuwebservice.constant;


/**
 * This class contains the constants that are used in the application.
 * 
 * @author Tata Consultancy Services
 * @version 1.0
 * @since 
 */
public final class MpuWebConstants {

	public static final String ASSIGNED = "ASSIGNED";
	public static final String CLOSE = "close";
	public static final String COMPLETED = "COMPLETED";
	public static final String DIV_ITEM_SKU = "DIV-ITEM-SKU";
	public static final String DOT = ".";
	public static final String ENVIRONMENT = "env";
	public static final String ESCALATION_TIME = "escalation_time";
	public static final String NA = "NA";
	public static final String NOTAVAILABLE = "NOTAVAILABLE";
	public static final String OOS = "OOS";
	public static final String OPEN = "OPEN";
	public static final String OPENSTATUS = "OPEN";
	public static final String MOD_VERIFY = "MOD_VERIFY";
	public static final String REVSTRFLAG = "T";
	public static final String SPACE = " ";
	public static final String STOCKDECREMENT = "stockDecrement";
	public static final String STOCKINCREMENT = "stockIncrement";
	public static final String TIME_ZONE = "timeZone";
	public static final String UPC = "UPC";
	public static final String WIP = "WIP";
	public static final String ZIP = "zip";
	public static final String LAYAWAY = "LAYAWAY"; 

	
	public static final String SUCCESS_200 = "Request successfully created";
	public static final String ERROR_500 = "Internal Server error occured";
	public static final String STORE_NUMBER = "store_number";
	public static final String QUEUE = "queue";
	public static final String SALESCHECK = "salescheck";
	public static final String ORDER ="ORDER";
	public static final String PAYMENT ="PAYMENT";
	public static final String ITEM = "ITEM";
	public static final String IDENTIFIER = "CUSTOMER";
	public static final String CUSTOMER_ID = "customer_id";
	public static final String CUSTOMERID = "CUSTOMERID";
	
	public static final String SHOPIN_SALESCHECK = "SALESCHECK";
	
	
	public static final String STATUS = "status";
	public static final String ACTIVITY = "activity";
	public static final String SEQUENCE = "seq";
	public static final String CREATED_BY = "DJ-APP";
	public static final String STOREFORMAT ="SearsRetail";
	public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	public static final String CREATE="CREATE";
	
	public static final String BINWEB="BINWEB";
	public static final String BINPENDING="BIN_PENDING";
	
	public static final String MOD_REG_VISIT="MOD_REG_VISIT";
	public static final String ZERO ="0";
	public static final String TASKTYPE_REG_VISIT="20";
	public static final String SYSTEMUSER ="SYSTEM";
	public static final String MOD_PAGE_REGISTER="MOD_POSMSG";
	public static final String TASKTYPE_PAGE_REG="1";
	
	//Added for JIRA-25084 (platform pickup- pages from register are not coming to MPU)
	public static final String MPU_PAGE_REGISTER="POSMESSAGE";
	public static final String KIOSK_MPU1="MPU1";
	
	public static final String SCAN="SCAN";
	public static final String BIN="BIN";
	public static final String MANUAL_ENTRY="MANUAL_ENTRY";
	public static final String MOD_APPROVE="MOD_APPROVE";
	public static final String MOD_DISAPPROVE="MOD_DISAPPROVE";
	public static final String CANCEL="CANCEL";
	public static final String RESTOCKED="RESTOCKED";
	public static final String BINNED="BINNED";
	public static final String CANCELLED="CANCELLED";
	public static final String CLOSED="CLOSED";
	public static final String RESTOCK_PENDING="RESTOCK_PENDING";
	public static final String VOIDED="VOIDED";
	public static final String VOID="VOID";
	public static final String EXPIRED="EXPIRED";
	public static final String ALL="ALL";
	
	public static final String ITEM_STATUS="item_status";
	public static final String STORENUMBER = "storeNumber";
	public static final String REQUEST_NUMBER = "request_number";
	public static final String ORDER_ADAPTOR = "ORDER_ADAPTOR";
	
	public static final String UPDATE_DESC= "Item updated Successfully";
	public static final String UPDATE_DESC_CODE= "01";
	public static final String UPDATE_FAIL_DESC= "No Item updated ";
	public static final String UPDATE_FAIL_DESC_CODE= "02";
	public static final String EMPTY_STRING= "";
	
	public static final String LOCKER_NO= "locker_no";	
	public static final String UPDATE_DESC_LOCKER= "Pin Number Updated Successfully";
	public static final String UPDATE_DESC_FAIL= "Fail to update Pin Number";
	public static final String GET_PINNO_FAIL_DESC="Unable to get pin Number";
	public static final String STAGE="STAGE";
	public static final String BINSTAGE="BINSTAGE";
	public static final String S991="S991";
	public static final String LAYAWAYS="LAYAWAYS";
	public static final String LAYAWAYF="LAYAWAYF";
	public static final String STAGED_STATUS="STAGED";
	public static final String MPU_OPEN_STATUS="STAGED";

	public static final String PACKAGE = "PACKAGE";
	public static final String PICKUP_INITIATED="PICKUP_INITIATED";
	public static final String PICKED_UP="PICKED_UP";
	public static final String NOT_PICKED_UP = "NOT_PICKED_UP";
	public static final String PACKAGE_PENDING="PACKAGE_PENDING";
	public static final String PICKUP = "PICKUP";
	public static final String HELP = "HELP";
	public static final String N="N";
	public static final String Y="Y";
	
	
	

	public static final String REPAIRPICK = "REPAIRPICK";
	public static final String REPAIRDROP = "REPAIRDROP";
	public static final String RETURN = "RETURN";
	public static final String RETURNIN5 = "RETURNIN5";
	public static final String EXCHANGE = "EXCHANGE";
	public static final String EXCHANGEIN5 = "EXCHANGEIN5";
	public static final String PICK_UP_REQUEST = "PickUpRequest";
	public static final String HG_REQUEST_TYPE="H&G";
	public static final String ORDER_SOURCE_DIRECT = "DIRECT";
	public static final String ORDER_SOURCE_NPOS = "NPOS";
	public static final String CANCEL_REQUEST = "CANCEL";
	public static final String CNR = "CNR";
	public static final String EDIT = "EDIT";
	public static final String REPORT_START_TIME=" 00:00:00";
	public static final String REPORT_END_TIME=" 23:59:59";
	public static final String REPORT_START_DATE="REPORT_START_DATE";
	public static final String REPORT_END_DATE="REPORT_END_DATE";
	public static final String MPU_SERVICE_WARNING_MSG="There are no associate logged on to receive MPU pages.";
	public static final String RSTK_PEND_VOID="RSTK_PEND_VOID";
	public static final String COMMA = ",";
	public static final String CUSTOMER_TYPE = "10";
	public static final String ADDRESS1_TYPE = "7";
	public static final String ADDRESS2_TYPE = "8";
	public static final String ALL_STATUS = "'OPEN','EXPIRED','COMPLETED'";
	public static final String NORESPONSE = "NORESPONSE";
	public static final String AVAILABLE = "AVAILABLE";
	public static final String PROCESSED = "PROCESSED";
	public static final String MPU_DIRECT = "MPU_DIRECT";
	public static final String RSTK_PEND_CNL="RSTK_PEND_CNL";
	public static final String QUEUE_TYPE_WEB="WEB";
	public static final String DEFAULT_STORE="00000";
	public static final String WEB_SALE_SALESCHECK="093";
	public static final String WEB_TRANSFER="192";
	public static final String KEY_SEPARATOR="_";
	public static final String DEFAULT_ASSOCIATE="000000";
	public static final String CURBSIDE="CURBSIDE";
	public static final String SHOPIN="SHOPIN";
	public static final String KIOSK = "KIOSK";
	
	public static final String LOCKER_OPEN="LOCKER_OPEN"; 
	public static final String PHONE="PHONE"; 
	public static final String LOCKER_PICKUP = "LOCKER_PICKUP";
	public static final String NAME_ADDRESS = "NAME_ADDRESS";
	public static final String MATCHKEY = "MATCHKEY";
	
	public static final String PICK_UP_STATUS="The Item Pickup is done.";
	public static final String PICK_UP_SEQ="100";
	
	public static final String VEHICLE_YEAR = "19";
	public static final String VECHICLE_TYPE = "20";
	public static final String VEHICLE_MAKE = "21";
	public static final String VEHICLE_COLOR = "22";
	public static final String SHOPIN_NOTIFICATION_ID = "23";
	public static final String IDENTIFIER_TYPE = "type" ;
	public static final String IDENTIFIER_VALUE = "value";
	public static final String SHOPIN_SUCCESS = "200";
	public static final String SHOPIN_FAIL = "500";
	public static final String SHOPIN_FAIL_RESPONSE = "exception has been occured while creating pickup";
	public static final String SHOPIN_SUCCESS_RESPOSNE = "pickup created successfully";
	
	public static final String SC_SCAN = "SC_SCAN";
	public static final String NOT_DELIVER = "NOT_DELIVER";
	public static final String NOT_DELIVERED = "NOT DELIVERED";
	public static final String RETURNED = "RETURNED";
	
	public static final String COMPLETE_STATUS_LIST = "COMPLETED, PICKED_UP, RETURNED,CANCELLED";
	public static final String NOT_IN_MPU = "NOT_IN_MPU";
	public static final String POSTVOID = "POSTVOID";
	public static final String CHK_STATUS_MSG = "Invalid Request Number. Please Rescan/Enter";
	public static final String SFS = "SFS";
	
	public static final String EXCHANGEIN5RETURN = "EXCHANGEIN5RETURN";
	public static final String ONLINESTORELIST = "09300,09301,09306,09308,09311,09312,09321,09322,09372,09501,09911,09917";
	
	public static final String SEARSRETAIL = "SearsRetail";
	public static final String RETURNIN5_ERROR_MESSAGE = "Please complete return at Register";
	public static final String HOLDGO = "HOLDGO";
	public static final String SUCCESS = "SUCCESS";
	public static final String COMPLETE = "COMPLETE";
	public static final String REQUEST_STATUS = "request_status";
	public static final String REQUEST_TYPE = "request_type";
	public static final String ITEM_POST_VOID_DESC = "Item is POST VOIDED";
	public static final String HEALTHCHECK = "HEALTHCHECK";
	public static final String ERROR = "ERROR";
	public static final String CACHE_REFRESH_FLAG = "cacheRefreshFlag";


	public static final String FILTER = "FILTER";


	private MpuWebConstants() {		
	}

}
