package com.example.userdetailservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.example.userdetailservice.jsonview.UserView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@DynamicUpdate
public class User {

	@JsonView(UserView.Private.class)
	@Id
	@NotNull(message = "Please provide an id")
	@Positive(message = "Please provide a positive numeric value")
	@NumberFormat(style = Style.NUMBER)
	private Long id;
	
	@JsonView(UserView.Public.class)
	private String title;
	
	@JsonView(UserView.Public.class)
	private String firstName;

	@JsonView(UserView.Public.class)
	private String lastName;
	
	@JsonView(UserView.Public.class)
	private String gender;
	
	@JsonView(UserView.Public.class)
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
	private Address address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
