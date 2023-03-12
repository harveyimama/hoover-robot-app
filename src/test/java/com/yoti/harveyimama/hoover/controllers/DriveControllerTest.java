package com.yoti.harveyimama.hoover.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.harveyimama.hoover.data.DefaultRoom;
import com.yoti.harveyimama.hoover.data.DriveHooverRequest;
import com.yoti.harveyimama.hoover.exceptions.DriverProcessingError;
import com.yoti.harveyimama.hoover.model.entities.DriveRequest;
import com.yoti.harveyimama.hoover.model.entities.DriveResponse;
import com.yoti.harveyimama.hoover.model.repositories.DriveRequestRepository;
import com.yoti.harveyimama.hoover.model.repositories.DriveResponseRepository;
import com.yoti.harveyimama.hoover.service.contracts.Room;
import com.yoti.harveyimama.hoover.services.DefaultHooverService;
import com.yoti.harveyimama.hoover.services.DefaultCoordinateValidator;

@SpringBootTest
@AutoConfigureMockMvc
 class DriveControllerTest {
	
	DriveController controller = new DriveController();
	DriveHooverRequest request =  new DriveHooverRequest();
	DriveResponse response = new DriveResponse();
	
	@MockBean
	DefaultHooverService service;
	
	@MockBean
	DriveRequestRepository requestRepo;
	
	@MockBean
	DriveResponseRepository responseRepo;

	@Autowired
	MockMvc mockMvc;

	@Rule // initMocks
	public MockitoRule rule = MockitoJUnit.rule();

	

	@BeforeEach
	public void setup() {
		

	}
	
	@Test
	 void testNoRequestSent() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/v1/drive")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
	
	
	@Test
	 void testValidRequestSent() throws Exception {
		
		request.setInstructions("NEWS");
		request.setRoomSize(new int[]{1,2});
		request.setCoords(new int[]{1,2});
		request.setPatches(new int[][]{{1,2},{3,4},{5,6}});
		
		DriveRequest driveInput = new DriveRequest();
		driveInput.setRoomSize(request.getRoomSize());
		driveInput.setCoords(request.getCoords());
		driveInput.setPatches(request.getPatches());
		driveInput.setInstructions(request.getInstructions());
	
		Room room = new DefaultRoom(request.getRoomSize(), request.getPatches(), new DefaultCoordinateValidator());
		
		Mockito.when(service.drive(request.getInstructions(),request.getCoords(),room)).thenReturn(response);
		Mockito.when(requestRepo.save(driveInput)).thenReturn(driveInput);
		Mockito.when(responseRepo.save(response)).thenReturn(response); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/drive")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
	}
	
	@Test
	 void testExceptionWhenValidRequestSent() throws Exception {
		DriveRequest driveInput = new DriveRequest();
		driveInput.setRoomSize(request.getRoomSize());
		driveInput.setCoords(request.getCoords());
		driveInput.setPatches(request.getPatches());
		driveInput.setInstructions(request.getInstructions());
		
		Mockito.when(service.drive(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenThrow(new DriverProcessingError("error",null));
		Mockito.when(requestRepo.save(driveInput)).thenReturn(driveInput);
		Mockito.when(responseRepo.save(response)).thenReturn(response); 
		
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/drive")
				.content(new ObjectMapper().writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError());
	}
	
	
	
	

}
