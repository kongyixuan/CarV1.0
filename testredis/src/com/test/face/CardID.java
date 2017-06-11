package com.test.face;

public class CardID {

	private int type;	//证件类型。返回1，代表是身份证。
	
	private String address;//住址
	private String birthday;//生日，格式为YYYY-MM-DD
	private String gender;//性别（男/女）
	private String id_card_number;//身份证号
	private String name;//姓名
	private String race;//民族（汉字）
	private String side;//front/back 表示身份证的正面或者反面（illegal）
	private String issued_by;//签发机关
	private String valid_date;//有效日期，格式为一个16位长度的字符串，表示内容如下YYYY.MM.DD-YYYY.MM.DD，或是YYYY.MM.DD-长期。
	private Object legality;
	
	public CardID() {
		super();
	}
	public CardID(int type, String address, String birthday, String gender,
			String id_card_number, String name, String race, String side,
			String issued_by, String valid_date) {
		super();
		this.type = type;
		this.address = address;
		this.birthday = birthday;
		this.gender = gender;
		this.id_card_number = id_card_number;
		this.name = name;
		this.race = race;
		this.side = side;
		this.issued_by = issued_by;
		this.valid_date = valid_date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getId_card_number() {
		return id_card_number;
	}
	public void setId_card_number(String id_card_number) {
		this.id_card_number = id_card_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getIssued_by() {
		return issued_by;
	}
	public void setIssued_by(String issued_by) {
		this.issued_by = issued_by;
	}
	public String getValid_date() {
		return valid_date;
	}
	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	public Object getLegality() {
		return legality;
	}
	public void setLegality(Object legality) {
		this.legality = legality;
	}
	
}
