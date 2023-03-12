package com.yoti.harveyimama.hoover.service.contracts;

import com.yoti.harveyimama.hoover.exceptions.DriverProcessingError;
import com.yoti.harveyimama.hoover.model.entities.DriveResponse;

public interface HooverService {
	
	 DriveResponse  drive(String instructions,int[] startPoint, Room room) throws DriverProcessingError;

}
