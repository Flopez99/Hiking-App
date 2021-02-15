package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trail implements Serializable{
	private String name;
	private String address;
	private double distance;
	private double elevationGain;
	private Type type;
	private Difficulty difficulty;

	public Trail(String name, String address, double distance, double elevationGain, Type type, Difficulty difficulty) {
		super();
		this.name = name;
		this.address = address;
		this.distance = distance;
		this.elevationGain = elevationGain;
		this.type = type;
		this.difficulty = difficulty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getDistance() {
		String strDouble = String. format("%.2f", distance);
		distance = Double.parseDouble(strDouble);
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getElevationGain() {
		String strDouble = String. format("%.2f", elevationGain);
		elevationGain = Double.parseDouble(strDouble);
		return elevationGain;
	}

	public void setElevationGain(double elevationGain) {
		this.elevationGain = elevationGain;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Trail [name=" + name + ", address=" + address + ", distance=" + distance + ", elevationGain="
				+ elevationGain + ", type=" + type + ", difficulty=" + difficulty + "]";
	}

}
