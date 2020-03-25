package org.springframework.samples.petclinic.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.h2.value.DataType;

import lombok.Data;

@Embeddable
@Data
public class Money extends DataType{

	// Attributes -------------------------------------------------------------

	@Digits(integer = 10, fraction = 2)
	@Min(0)
	private Double				amount;

	@NotBlank
	private String				currency;


	// Object interface -------------------------------------------------------

	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append("<<");
		result.append(this.currency);
		result.append(" ");
		result.append(String.format("%.2f", this.amount));
		result.append(">>");

		return result.toString();
	}
}
