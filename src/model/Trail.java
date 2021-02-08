package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trail {

	private SimpleStringProperty name;
	private SimpleStringProperty address;
	private SimpleDoubleProperty distance;
	private SimpleDoubleProperty elevationGain;
	private SimpleStringProperty type;
	private SimpleStringProperty difficulty;
	
	public Trail(String name, String address, double distance, double elevationGain, Type type, Difficulty difficulty) {
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.distance = new SimpleDoubleProperty(distance);
		this.elevationGain = new SimpleDoubleProperty(elevationGain);
		this.type = new SimpleStringProperty(type.toString());
		this.difficulty = new SimpleStringProperty(difficulty.toString());
	}

	public String getName() {
		return name.get();
		
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

	public double getDistance() {
		return distance.get();
	}

	public void setDistance(SimpleDoubleProperty distance) {
		this.distance = distance;
	}

	public double getElevationGain() {
		return elevationGain.get();
	}

	public void setElevationGain(SimpleDoubleProperty elevationGain) {
		this.elevationGain = elevationGain;
	}

	public String getType() {
		return type.get();
	}

	public void setType(SimpleStringProperty type) {
		this.type = type;
	}

	public String getDifficulty() {
		return difficulty.get();
	}

	public void setDifficulty(SimpleStringProperty difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Trail [name=" + name + ", address=" + address + ", distance=" + distance + ", elevationGain="
				+ elevationGain + ", type=" + type + ", difficulty=" + difficulty + "]";
	}
	
	
}
