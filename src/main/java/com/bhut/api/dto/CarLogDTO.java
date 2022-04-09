package com.bhut.api.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class CarLogDTO {

	private static final AtomicInteger count = new AtomicInteger(0);

	private int id;

	private String car_id;

	private String data_hora;

	public CarLogDTO(int id, String car_id, String data_hota) {
		super();
		this.id = count.incrementAndGet();
		this.car_id = car_id;
		this.data_hora = data_hota;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getData_hora() {
		return data_hora;
	}

	public void setData_hora(String data_hora) {
		this.data_hora = data_hora;
	}

}
