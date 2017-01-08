package com.searshc.mpuwebservice.processor;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.sears.mpu.backoffice.domain.Order;
import com.searshc.mpuwebservice.bean.HGRequestDTO;

public interface OBUUpdateProcessor {
	
	public void updateOBU(Order order) throws JsonGenerationException, JsonMappingException, IOException;
	public void updateHGOBU(HGRequestDTO order) throws JsonGenerationException, JsonMappingException, IOException;

}
