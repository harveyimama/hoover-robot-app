package com.yoti.harveyimama.hoover.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoti.harveyimama.hoover.exceptions.DriverProcessingError;
import com.yoti.harveyimama.hoover.model.entities.DriveResponse;
import com.yoti.harveyimama.hoover.model.repositories.DriveRequestRepository;
import com.yoti.harveyimama.hoover.service.contracts.HooverService;
import com.yoti.harveyimama.hoover.service.contracts.Room;
import com.yoti.harveyimama.hoover.utility.Cordinates;


/** 
* <h1>Default Hoover Service that implements the Hoover Interface/h1> 
* This Class contains methods that operate the Default hoover
* They specify how the defaulf hoover drives and how it cleans the room
* 
* @author Harvey Imama 
* 
*/
@Service
public class DefaultHooverService implements HooverService {
	private Cordinates position = new Cordinates();
	

	/** 
	    * This method is used to find perform the drive function  
	    * @param instructions This is the parameter holding coordinates for Hoover to navigate
	    * @param startPoint  This is the starting coordinates for Hoover
	    * @param room  This is the room dimensions and coordinates of all the dirt patches
	    * @return DriveResponse This returns the number of patches cleaned and final position of Hoover
	    */
	@Override
	public  DriveResponse drive(String instructions, int[] startPoint, Room room)
			throws DriverProcessingError {

		DriveResponse driveResponse = new DriveResponse();

		position.setY(startPoint[1]);
		position.setX(startPoint[0]);

		if (!room.getCoordinateValidator().isValidCordinate(position,room))
			throw new DriverProcessingError("Hoover not in room", null);

		int patchesFound = cleanPosition(room);

		char[] moves = instructions.toCharArray();

		for (Character move : moves) {
			Cordinates proposedPosition = proposeMove(move, position);
			if (room.getCoordinateValidator().isValidCordinate(proposedPosition,room)) { 
				position = proposedPosition;
				patchesFound = patchesFound + cleanPosition(room);
			}
		}

		driveResponse.setCoords(position.getAsIntArray());
		driveResponse.setPatches(patchesFound);

		return driveResponse;
	}
	
	/** 
	    * This method is used to determine the next coordinates the Hover would be if it makes the move 
	    * @param move This is either North(N), West(W), East(E) or South(S) direction which is part of the array of
	    * 		instructions sent to Hoover 
	    * @param currentPosition  This is the current coordinates for Hoover
	    * @return Coordinates This returns the coordinates Hoover would be if move is made
	    */

	private Cordinates proposeMove(Character move, Cordinates currentPosition) {
		Cordinates proposedCordinates = new Cordinates();
		proposedCordinates.setY(currentPosition.getY());
		proposedCordinates.setX(currentPosition.getX());

		switch (move) {
		case 'N':
			proposedCordinates.setY(proposedCordinates.getY() + 1);
			break;
		case 'S':
			proposedCordinates.setY(proposedCordinates.getY() - 1);
			break;
		case 'W':
			proposedCordinates.setX(proposedCordinates.getX() - 1);
			break;
		case 'E':
			proposedCordinates.setX(proposedCordinates.getX() + 1);
			break;
		}

		return proposedCordinates;

	}
	
	/** 
	    * This method is used to check if Hoover is currently on a dirt patch . Hover would remove the patch once it is found
	    * @param room This is the dimension of the room and all the current dirt patches 
	    * @return boolean This returns true if dirt patch is found and removed 
	    */

	private boolean checkForDirt(Room room) {
		int[][] dirtPatches = room.getPatches();

		for (int i = 0; i < dirtPatches.length; i++) {
			int[] dirtPatch = dirtPatches[i];

			if (dirtPatch.length == 2 && dirtPatch[0] == position.getX() && dirtPatch[1] == position.getY()) {
				dirtPatches[i] = new int[] {};
				return true;
			}

		}
		return false;
	}
	
	/** 
	    * This method returns number of dirt patches cleaned in one operation. It returns either 0 or 1
	    * @param room This is the dimension of the room and all the current dirt patches 
	    * @return int This returns  number of dirt patches cleaned
	    */

	private int cleanPosition(Room room) {
		boolean isHoverOnDirtyPatch = checkForDirt(room);
		return (isHoverOnDirtyPatch ? 1 : 0);
	}

}
