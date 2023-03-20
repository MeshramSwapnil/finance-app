package com.minifinance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {
	@Id
	@GeneratedValue(generator="pseq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="pseq",sequenceName="product_seq", allocationSize=1, initialValue = 10000)
	Integer id;

	@NotBlank
	String name;

	@NotBlank
	String description;
	
	String imageUrl;

	@NotNull
	Integer charge;
}
