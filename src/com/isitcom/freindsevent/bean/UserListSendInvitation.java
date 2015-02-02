package com.isitcom.freindsevent.bean;

public class UserListSendInvitation {
	private int id;
	private String pseudo;
	private String adresse;
	private String image;
	private boolean isCheked;
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isCheked() {
		return isCheked;
	}
	public void setCheked(boolean isCheked) {
		this.isCheked = isCheked;
	}

}
