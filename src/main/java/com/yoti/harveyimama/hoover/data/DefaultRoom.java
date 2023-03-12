package com.yoti.harveyimama.hoover.data;

import com.yoti.harveyimama.hoover.service.contracts.Room;
import com.yoti.harveyimama.hoover.services.DefaultCoordinateValidator;
import com.yoti.harveyimama.hoover.utility.Cordinates;

import lombok.Data;
@Data
public class DefaultRoom extends Room{

	public DefaultRoom(int[] rightCorner, int[][] patches, DefaultCoordinateValidator coordinateValidator) {
		super(rightCorner, patches, coordinateValidator);
		
	}

}
