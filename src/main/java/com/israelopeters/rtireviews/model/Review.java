package com.israelopeters.rtireviews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    private String imageUri;

    private Long likeCount;

    @Column(nullable = false)
    private LocalDateTime dateTimeCreated;

    @Enumerated(EnumType.STRING)
    private List<ReviewGenre> genreList;

    @ManyToOne
    private User author;

    public enum ReviewGenre {
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
