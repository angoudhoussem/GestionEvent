package com.isitcom.freindsevent.bean;

public class User {

	private int id;
	private String pseudo;
	private String login;
	private String password;
	private String email;
	private String image;


	public User( String pseudo, String login, String password,String email,String image) {
		super();
		this.pseudo = pseudo;
		this.login = login;
		this.password = password;
		this.email=email;
		this.image=image;
	}
	public User( String pseudo, String login, String password,String email) {
		super();
		this.pseudo = pseudo;
		this.login = login;
		this.password = password;
		this.email=email;
	
	}
	public User() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
