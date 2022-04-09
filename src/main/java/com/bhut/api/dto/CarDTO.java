package com.bhut.api.dto;

public class CarDTO {

	private String _id;

	private String title;

	private String brand;

	private String price;

	private int age;

	private int __v;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int get__v() {
		return __v;
	}

	public void set__v(int __v) {
		this.__v = __v;
	}

	@Override
	public String toString() {
		return "CarDTO [_id=" + _id + ", title=" + title + ", brand=" + brand + ", price=" + price + ", age=" + age
				+ ", __v=" + __v + "]";
	}

}
