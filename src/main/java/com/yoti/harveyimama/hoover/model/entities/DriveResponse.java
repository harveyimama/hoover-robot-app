package com.yoti.harveyimama.hoover.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class DriveResponse {
	@Id
	private String id;
	private int[] coords;
	private int patches;

}
