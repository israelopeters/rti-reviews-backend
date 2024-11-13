package com.israelopeters.rtireviews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    private String imageUri;

    @Column(nullable = false)
    private LocalDateTime dateTimeCreated;

    @Enumerated(EnumType.STRING)
    private List<ReviewTag> tagList;

    public enum ReviewTag {
        THRILLER,
        CRIME,
        SELF_HELP,
        ROMANCE,
        FICTION,
        AFRICAN,
        BIOGRAPHY,
        HISTORY
    }

}
