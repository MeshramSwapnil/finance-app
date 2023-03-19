package com.minifinance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class CheckBook {

	@Id
	@GeneratedValue(generator="chkbkseq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="chkbkseq",sequenceName="chkbk_seq", allocationSize=1, initialValue = 10000)
	Long id;

	@NotNull
	Integer pages;

	@NotNull
	Integer ratePerPage;
	
}
