package com.dmr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("User")
@Data
@NoArgsConstructor
public class SimpleUser extends Person {
	@Enumerated(EnumType.STRING)
	private final Role role = Role.SIMPLE_USER;
}
