package com.csl.qq.card.domain;

public class Formula {
	private Integer id;
	private String target;
	private String source1;
	private String source2;
	private String source3;
	private Integer cost;
	private Theme theme;
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource1() {
		return source1;
	}
	public void setSource1(String source1) {
		this.source1 = source1;
	}
	public String getSource2() {
		return source2;
	}
	public void setSource2(String source2) {
		this.source2 = source2;
	}
	public String getSource3() {
		return source3;
	}
	public void setSource3(String source3) {
		this.source3 = source3;
	}
	
	
}
