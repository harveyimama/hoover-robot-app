package com.yoti.harveyimama.hoover.data;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class DriveHooverRequest {
	
	@NotEmpty(message = "Room size is required")
	private int[] roomSize;
	@NotEmpty(message = "Coordinates are required")
	private int[] coords;
	@NotEmpty(message = "Patches are required")
	private int[][] patches;
	@NotEmpty(message = "Instructions are required")
	private String instructions;

}
