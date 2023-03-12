package com.yoti.harveyimama.hoover.service.contracts;

import com.yoti.harveyimama.hoover.utility.Cordinates;

public interface CoordinateValidator {
	
	public abstract boolean isValidCordinate(Cordinates cordinates,Room  room);

}
