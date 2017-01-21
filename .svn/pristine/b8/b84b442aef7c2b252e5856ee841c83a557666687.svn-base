package com.mh.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TWebUserEleFavourite entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_web_user_ele_favourite")
public class TWebUserEleFavourite {

	// Fields

	private Integer id;
	private String userName;
	private String flat;
	private String gameCode;
	private String gameName;
	private String client;
	private Date createTime;
	private Date autoUpdateTime;

	// Constructors

	/** default constructor */
	public TWebUserEleFavourite() {
	}

	/** full constructor */
	public TWebUserEleFavourite(String userName, String flat, String gameCode,String gameName,String client, Date createTime, Date autoUpdateTime) {
		this.userName = userName;
		this.flat = flat;
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.client = client;
		this.createTime = createTime;
		this.autoUpdateTime = autoUpdateTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "user_name", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "flat", length = 50)
	public String getFlat() {
		return this.flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	@Column(name = "game_code", length = 50)
	public String getGameCode() {
		return this.gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@Column(name = "game_name", length = 50)
	public String getGameName() {
		return this.gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	@Column(name = "client", length = 50)
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auto_update_time", length = 19)
	public Date getAutoUpdateTime() {
		return this.autoUpdateTime;
	}

	public void setAutoUpdateTime(Date autoUpdateTime) {
		this.autoUpdateTime = autoUpdateTime;
	}

}