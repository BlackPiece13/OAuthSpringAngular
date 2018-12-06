package com.dmr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Admin")
@Data
@NoArgsConstructor
public class Admin extends Person {
	@Enumerated(EnumType.STRING)
	private final Role role = Role.ADMIN;
}
