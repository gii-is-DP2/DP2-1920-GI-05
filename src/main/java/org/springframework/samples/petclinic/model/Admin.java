package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Getter
@Setter
@Table(name = "admins")
public class Admin extends Person {

}
