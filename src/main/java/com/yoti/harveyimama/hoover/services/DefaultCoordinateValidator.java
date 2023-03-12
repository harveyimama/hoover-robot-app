package com.yoti.harveyimama.hoover.services;

import com.yoti.harveyimama.hoover.service.contracts.Room;

import org.springframework.stereotype.Service;

import com.yoti.harveyimama.hoover.service.contracts.CoordinateValidator;
import com.yoti.harveyimama.hoover.utility.Cordinates;

/** 
* <h1>Default room validator that implements the CoordinateValidator Interface/h1> 
* This Class contains methods that validate coordinates of a room 
* They manages its boundaries
* 
* @author Harvey Imama 
* 
*/
@Service
public class DefaultCoordinateValidator implements CoordinateValidator {

	
	/** 
	    * This method is used to check if hoover is trying to navigate out of bound areas 
	    * @param proposedCordinates This is the coordinates Hoover is trying to navigate to
	    * @return boolean This returns true is proposed coordinates are valid 
	    */
	public boolean isValidCordinate(Cordinates proposedCordinates,Room room) {

		return (!(proposedCordinates.getY() > room.getRightCorner().getY()
				|| proposedCordinates.getX() > room.getRightCorner().getX()
				|| proposedCordinates.getY() < room.getLeftCorner().getY()
				|| proposedCordinates.getX() < room.getLeftCorner().getX()));

	}

}
