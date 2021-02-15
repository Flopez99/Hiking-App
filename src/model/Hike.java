package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import javafx.beans.property.SimpleStringProperty;

public class Hike implements Comparable, Serializable {
	private String trailName;
	private String dateAndTimeStarted;
	private String dateAndTimeEnded;
	private String distanceHiked;
	private String averagePace;
	private String hikeDuration;
	private LinkedList<String> picturesTaken;

	public Hike(String trailName, String dateAndTimeStarted, String dateAndTimeEnded, String distanceHiked,
			String duration, String averagePace, LinkedList<String> picturesTaken) {
		super();
		this.hikeDuration = duration;
		this.trailName = trailName;
		this.averagePace = averagePace;
		this.dateAndTimeStarted = dateAndTimeStarted;
		this.dateAndTimeEnded = dateAndTimeEnded;
		this.distanceHiked = distanceHiked;
		this.picturesTaken = picturesTaken;
	}

	public String getHikeDuration() {
		return hikeDuration;
	}

	public void setHikeDuration(String hikeDuration) {
		this.hikeDuration = hikeDuration;
	}

	public String getAveragePace() {
		return averagePace;
	}

	public void setAveragePace(String averagePace) {
		this.averagePace = averagePace;
	}

	

	public String getTrailName() {
		return trailName;
	}

	public void setTrailName(String trailName) {
		this.trailName = trailName;
	}

	public String getDateAndTimeStarted() {
		return dateAndTimeStarted;
	}

	public void setDateAndTimeStarted(String dateAndTimeStarted) {
		this.dateAndTimeStarted = dateAndTimeStarted;
	}

	public String getDateAndTimeEnded() {
		return dateAndTimeEnded;
	}

	public void setDateAndTimeEnded(String dateAndTimeEnded) {
		this.dateAndTimeEnded = dateAndTimeEnded;
	}

	public String getDistanceHiked() {
		return distanceHiked;
	}

	public void setDistanceHiked(String distanceHiked) {
		this.distanceHiked = distanceHiked;
	}

	public LinkedList<String> getPicturesTaken() {
		return picturesTaken;
	}

	public void setPicturesTaken(LinkedList<String> picturesTaken) {
		this.picturesTaken = picturesTaken;
	}

	@Override
	public String toString() {
		return "Hike [trailName=" + trailName + ", dateAndTimeStarted=" + dateAndTimeStarted + ", dateAndTimeEnded="
				+ dateAndTimeEnded + ", distanceHiked=" + distanceHiked + ", picturesTaken=" + picturesTaken + "]";
	}

	@Override
	public int compareTo(Object o) {

		return 0;
	}
	
	
}
