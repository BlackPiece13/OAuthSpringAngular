package com.dmr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String url;

    @OneToOne
    private Photo photo;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Lob
    private String content;
}
