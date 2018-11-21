package com.matrimonio.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class City {
	
	@Column(name = "coordinate")
	private final Point coordinate;

	@Column(name = "name")
	private String name;

	public City() {
		this.coordinate = null;
	}

	public City(final String name, final double latitude, final double longitude) {
		this.name = name;
		this.coordinate = new Point(longitude, latitude);
	}

	@JsonProperty
	public double getLatitude() {
		return this.coordinate.getY();
	}

	@JsonProperty
	public double getLongitude() {
		return this.coordinate.getX();
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonIgnore
	public Point getPoint() {
		return this.coordinate;
	}
}
