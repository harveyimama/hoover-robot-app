package com.yoti.harveyimama.hoover.services;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.yoti.harveyimama.hoover.data.DefaultRoom;
import com.yoti.harveyimama.hoover.exceptions.DriverProcessingError;
import com.yoti.harveyimama.hoover.model.entities.DriveResponse;
import com.yoti.harveyimama.hoover.service.contracts.Room;


@SpringBootTest
 class DefaultHooverServiceTest {
	
DefaultHooverService service = new DefaultHooverService();



	@BeforeEach
	public void setup() {
	
	}
	
	@Test
	 void testZeroSizedRoom() throws Exception {
		
		String instructions = "NEWS";
		int[] startPoint = new int[]{1,2};
		int[][] patches = new int[][]{{1,2},{3,4},{5,6}};
		int[] roomSize = new int[]{0,0};
		 
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		
		boolean hasErrorResponse = false;
		String errorMessage = "";
		try {
			 service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
			errorMessage = e.getMessage();
		}
		
		assertEquals("Hoover not in room",errorMessage);
		assertEquals(true,hasErrorResponse);
	}
	
	@Test
	 void testDriveinOneDirection () throws Exception {
		
		String instructions = "NNNNNNNNNNNNNNNNNNNNNNN";
		int[] startPoint = new int[]{0,0};
		int[][] patches = new int[][]{{1,2},{0,4},{5,5}};
		int[] roomSize = new int[]{5,5};
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		boolean hasErrorResponse = false;
		DriveResponse resp = null;
		try {
			 resp = service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
		}
		
		assertEquals(0,resp.getCoords()[0]);
		assertEquals(5,resp.getCoords()[1]);
		assertEquals(1,resp.getPatches());
		assertEquals(false,hasErrorResponse);
	}
	
	@Test
	 void testHooverStartLocationOutsideRoom() throws Exception {
		
		String instructions = "NEWS";
		int[] startPoint = new int[]{10,2};
		int[][] patches = new int[][]{{1,2},{3,4},{5,6}};
		int[] roomSize = new int[]{5,5};
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		boolean hasErrorResponse = false;
		String errorMessage = "";
		try {
			 service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
			errorMessage = e.getMessage();
		}
		
		assertEquals("Hoover not in room",errorMessage);
		assertEquals(true,hasErrorResponse);
	}
	
	@Test
	 void testDrivingBackAndForth () throws Exception {
		
		String instructions = "NSNSNSNSNSNSNSNSNS";
		int[] startPoint = new int[]{0,0};
		int[][] patches = new int[][]{{1,2},{0,4},{5,5}};
		int[] roomSize = new int[]{5,5};
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		boolean hasErrorResponse = false;
		DriveResponse resp = null;
		try {
			 resp = service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
		}
		
		assertEquals(0,resp.getCoords()[0]);
		assertEquals(0,resp.getCoords()[1]);
		assertEquals(0,resp.getPatches());
		assertEquals(false,hasErrorResponse);
	}
	
	@Test
	 void testDrivingTroughDustInEveryPatch () throws Exception {
		
		String instructions = "NNESSENN";
		System.out.println(instructions);
		int[] startPoint = new int[]{0,0};
		int[][] patches = new int[][]{{0,1},{0,2},{1,0},{2,0},{1,1},{1,2},{2,1},{2,2},{0,0}};
		int[] roomSize = new int[]{2,2};
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		boolean hasErrorResponse = false;
		DriveResponse resp = null;
		try {
			 resp = service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
		}
		
		assertEquals(2,resp.getCoords()[0]);
		assertEquals(2,resp.getCoords()[1]);
		assertEquals(9,resp.getPatches());
		assertEquals(false,hasErrorResponse);
	}
	
	@Test
	 void testDrivingTroughDustInEveryPatchTwice () throws Exception {
		
		String instructions = "NNESSENNWSSWNNWSS";
		System.out.println(instructions);
		int[] startPoint = new int[]{0,0};
		int[][] patches = new int[][]{{0,1},{0,2},{1,0},{2,0},{1,1},{1,2},{2,1},{2,2},{0,0}};
		int[] roomSize = new int[]{2,2};
		Room room = new DefaultRoom(roomSize, patches, new DefaultCoordinateValidator());  
		boolean hasErrorResponse = false;
		DriveResponse resp = null;
		try {
			 resp = service.drive(instructions, startPoint, room);
		}catch(DriverProcessingError e)
		{
			hasErrorResponse= true;
		}
		
		assertEquals(0,resp.getCoords()[0]);
		assertEquals(0,resp.getCoords()[1]);
		assertEquals(9,resp.getPatches());
		assertEquals(false,hasErrorResponse);
	}


}
