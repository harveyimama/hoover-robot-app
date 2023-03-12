package com.yoti.harveyimama.hoover.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document
public class DriveRequest {
	@Id
	private String id;
	
	private int[] roomSize;
	
	private int[] coords;
	
	private int[][] patches;
	
	private String instructions;
	

}
