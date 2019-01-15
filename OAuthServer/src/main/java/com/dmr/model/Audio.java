package com.dmr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MAIRE")
public class Audio extends Media {

}
