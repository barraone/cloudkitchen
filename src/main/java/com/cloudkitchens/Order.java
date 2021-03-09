package com.cloudkitchens;

public class Order {
		
	private String id;
	private String name;
	private Temp temp;
	private int shelfLife;
	private float decayRate;
	
	@Override
	public String toString() {
		return name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Temp getTemp() {
		return temp;
	}
	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	public int getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}
	public float getDecayRate() {
		return decayRate;
	}
	public void setDecayRate(float decayRate) {
		this.decayRate = decayRate;
	}
	
}
