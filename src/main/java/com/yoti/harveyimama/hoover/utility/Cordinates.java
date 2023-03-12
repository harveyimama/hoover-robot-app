package com.yoti.harveyimama.hoover.utility;

import lombok.Data;

@Data
public class Cordinates {
	
	private int Y;
	private int X;

	public int[] getAsIntArray() {
		
		return new int[]{this.X,this.Y};
	}
	
	

}
