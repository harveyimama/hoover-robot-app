package com.yoti.harveyimama.hoover.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yoti.harveyimama.hoover.data.DefaultRoom;
import com.yoti.harveyimama.hoover.data.DriveHooverRequest;
import com.yoti.harveyimama.hoover.exceptions.DriverProcessingError;
import com.yoti.harveyimama.hoover.model.entities.DriveRequest;
import com.yoti.harveyimama.hoover.model.entities.DriveResponse;
import com.yoti.harveyimama.hoover.model.repositories.DriveRequestRepository;
import com.yoti.harveyimama.hoover.model.repositories.DriveResponseRepository;
import com.yoti.harveyimama.hoover.service.contracts.HooverService;
import com.yoti.harveyimama.hoover.service.contracts.Room;
import com.yoti.harveyimama.hoover.services.DefaultCoordinateValidator;

/**
 * <h1>Program to drive Hoover robot around defined room</h1> The
 * DriveController is the entry point to the service which holds the Web service
 * end-point
 * 
 * @author Harvey Imama
 * 
 */

@Controller
public class DriveController {

	@Autowired
	private HooverService defaultHooverService;

	@Autowired
	private DriveResponseRepository responseRepo;

	@Autowired
	private DriveRequestRepository requestRepo;

	/**
	 * This method is the method for the post service which drives the Hoover
	 * 
	 * @param request This the the input which holds the instructions for driving
	 *                the hoover around the room
	 * @return ResponseEntity This is a http response entity that returns either
	 *         error or success response
	 */

	@PostMapping(value = "/v1/drive")
	public ResponseEntity<?> postDrivingInstructions(@RequestBody(required = true) DriveHooverRequest request) {

		DriveResponse finalDriveOutput = new DriveResponse();
		DriveRequest driveInput = new DriveRequest();
		try {

			driveInput.setRoomSize(request.getRoomSize());
			driveInput.setCoords(request.getCoords());
			driveInput.setPatches(request.getPatches());
			driveInput.setInstructions(request.getInstructions());

			DriveRequest requestEntity = requestRepo.save(driveInput);

			if (null == requestEntity)
				return new ResponseEntity<>("Error saving request to database ", HttpStatus.INTERNAL_SERVER_ERROR);

			Room room = new DefaultRoom(driveInput.getRoomSize(), driveInput.getPatches(), new DefaultCoordinateValidator()); 

			finalDriveOutput = defaultHooverService.drive(driveInput.getInstructions(), driveInput.getCoords(), room);

			finalDriveOutput.setId(requestEntity.getId());

			responseRepo.save(finalDriveOutput);
		
		} catch (DriverProcessingError e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
	
			return new ResponseEntity<>("Exception Occured ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(finalDriveOutput, HttpStatus.OK);

	}

}
