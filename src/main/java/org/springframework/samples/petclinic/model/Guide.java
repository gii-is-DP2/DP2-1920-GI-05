package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Data
@Table(name = "guides")
public class Guide extends Person {

}
