package com.example.userdetailservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.example.userdetailservice.jsonview.UserView;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;


@Entity
@DynamicUpdate
public class Address {

	@Id
    @Column(name = "user_id")
	private Long id;
	
	@JsonView(UserView.Public.class)
	private String suburb;
	@JsonView(UserView.Public.class)
	private String state;
	@JsonView(UserView.Public.class)
	private String fullAddress;
	@JsonView(UserView.Public.class)
	private Integer postcode;
	
	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public Integer getPostcode() {
		return postcode;
	}

	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
