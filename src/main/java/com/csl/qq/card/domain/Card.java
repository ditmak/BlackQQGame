package com.csl.qq.card.domain;

public class Card {
	private Theme theme;
	private Integer id;
	private Integer cardID;
	private Integer price;
	private String name;
	public Integer getCardID() {
		return cardID;
	}
	public void setCardID(Integer cardID) {
		this.cardID = cardID;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
