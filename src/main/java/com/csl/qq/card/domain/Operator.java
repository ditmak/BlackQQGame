package com.csl.qq.card.domain;

public class Operator {
	private Integer id;
	private String url;
	private String name;
	public Operator(String url,String name){
		this.url= url;
		this.name=name;
	}
	public Operator(){}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
