package com.searshc.mpuwebservice.vo;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.searshc.mpuwebservice.vo.ProductInfoResponse.ProductInfo.Location.Price;


@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ItemMeta {
	
	private String departmentName;

	private int deptNbr;

	private String div;
		
	private String imageUrl;
	
	private String item;
	
	private String itemDescription;
	
	//Changes done by Pankaj to get  Hazmat
	private ItemHazmatDetail itemHazmatDetails;
	
	private String itemId;
	
	private String ksn;
	
	private List<String> linkedKsns;
	
	private List<POG> POGDetails;
	
	private List<Price> priceDetailsList;
	
	private String qtyAvailable;
	
	private String receivedToday;
	
	private String shortDescription;
	
	private String sku;
	
	private List<StockLocator> stockLocatorDetails;
	
	private String storeNbr;
	
	private String upc;
	
	public String getDepartmentName() {
		return departmentName;
	}

	public int getDeptNbr() {
		return deptNbr;
	}

	public String getDiv() {
		return div;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getItem() {
		return item;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public ItemHazmatDetail getItemHazmatDetails() {
		return itemHazmatDetails;
	}

	public String getItemId() {
		return itemId;
	}

	public String getKsn() {
		return ksn;
	}

	public List<String> getLinkedKsns() {
		if(linkedKsns == null) {
			linkedKsns = new ArrayList<String>();
		}
		return linkedKsns;
	}

	public List<POG> getPOGDetails() {
		if(POGDetails==null){
			POGDetails = new ArrayList<POG>();
		}
		return POGDetails;
	}

	public List<Price> getPriceDetailsList() {
		if(priceDetailsList==null){
			priceDetailsList = new ArrayList<Price>();
		}
		return priceDetailsList;
	}

	public String getQtyAvailable() {
		return qtyAvailable;
	}

	public String getReceivedToday() {
		return receivedToday;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getSku() {
		return sku;
	}

	public List<StockLocator> getStockLocatorDetails() {
		if(stockLocatorDetails==null){
			stockLocatorDetails = new ArrayList<StockLocator>();
		}
		return stockLocatorDetails;
	}

	public String getStoreNbr() {
		return storeNbr;
	}

	public String getUpc() {
		return upc;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public void setDeptNbr(int deptNbr) {
		this.deptNbr = deptNbr;
	}
	
//	public List<StoreInventory> getStoreInventoryDetails() {
//		if(storeInventoryDetails==null){
//			storeInventoryDetails = new ArrayList<StoreInventory>();
//		}
//		return storeInventoryDetails;
//	}
	
	public void setDiv(String div) {
		this.div = div;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	

	public void setItemHazmatDetails(ItemHazmatDetail itemHazmatDetails) {
		this.itemHazmatDetails = itemHazmatDetails;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public void setLinkedKsns(List<String> linkedKsns) {
		this.linkedKsns = linkedKsns;
	}
	
	public void setPOGDetails(List<POG> pOGDetails) {
		POGDetails = pOGDetails;
	}

	public void setPriceDetailsList(List<Price> priceDetailsList) {
		this.priceDetailsList = priceDetailsList;
	}

	public void setQtyAvailable(String qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public void setReceivedToday(String receivedToday) {
		this.receivedToday = receivedToday;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setStockLocatorDetails(List<StockLocator> stockLocatorDetails) {
		this.stockLocatorDetails = stockLocatorDetails;
	}

	public void setStoreNbr(String storeNbr) {
		this.storeNbr = storeNbr;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}	

	 
/*    @Override
    public String toString()
    {
    	System.out.println("in Overridden toString...."+this.getClass().getFields().length);
    	StringBuilder b = new StringBuilder("[");
    	for (Field f : this.getClass().getFields())
    	{
    		if (!isStaticField(f))
    		{
    			System.out.println("f..."+f);
    			try
    			{
    				b.append(f.getName() + "=" + f.get(this) + " ");
    			} catch (IllegalAccessException e)
    			{
    				// pass, don't print
    			}
    		}
    	}
    	b.append(']');
    	System.out.println("in Overridden toString  b.toString()...."+b.toString());
    	return b.toString();
    }
*/

/*    private boolean isStaticField(Field f)
    {
    	return Modifier.isStatic(f.getModifiers());
    }        
*/
   

	
}
