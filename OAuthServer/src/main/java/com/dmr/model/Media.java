package com.dmr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    private LocalDateTime CreationDate;
    private LocalDateTime updateDate;

    @Column(name = "VIEWS", columnDefinition = "Decimal(7,0) default '0'")
    private Long viewsNumber;
}