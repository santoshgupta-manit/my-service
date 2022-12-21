package com.abn.assessment.recipe.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "RECIPE_TBL")
@DynamicUpdate
@Getter
@Setter
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String name;

	@NotBlank
	private String type;

	@NotBlank
	private String ingredients;

	@NotBlank
	private String instructions;

	@NotNull
	private Integer numberOfServings;

	@Column(updatable = false)
	@CreationTimestamp
	@JsonIgnoreProperties
	private LocalDateTime createdAt;

	@Column
	@UpdateTimestamp
	@JsonIgnoreProperties
	private LocalDateTime lastUpdatedAt;

}
