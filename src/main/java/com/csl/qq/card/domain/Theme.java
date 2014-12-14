package com.csl.qq.card.domain;

import java.util.Set;

public class Theme {
	private Integer id;
	private Integer themeID;
	private String name;
	private Integer level;
	private Set<Card> cards;
	public Integer getThemeID() {
		return themeID;
	}
	public void setThemeID(Integer themeID) {
		this.themeID = themeID;
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
