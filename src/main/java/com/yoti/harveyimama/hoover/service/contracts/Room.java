package com.yoti.harveyimama.hoover.service.contracts;

import com.yoti.harveyimama.hoover.utility.Cordinates;

import lombok.Data;

@Data
public abstract class Room {
	
	private  Cordinates rightCorner = new Cordinates();
	private  Cordinates leftCorner =  new Cordinates();
	private int[][] patches;
	private CoordinateValidator coordinateValidator;
	
	
	private Room() {}
	
	protected Room(int[] rightCorner, int[][] patches,CoordinateValidator coordinateValidator)
	{	
		this.rightCorner.setY(rightCorner[1]);
		this.rightCorner.setX(rightCorner[0]);
		this.leftCorner.setY(0);
		this.leftCorner.setX(0);
		this.patches = patches;
		this.coordinateValidator = coordinateValidator;
	}
	
	
	

}
