package com.israelopeters.rtireviews.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String country;

    private String bio;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate dateCreated;

    @OneToMany(mappedBy = "author") // Refer back to Review.author
    // TODO: Line 41 - Refactor reference to Review.author to use Metamodel Generator for better type safety
    private Set<Review> reviews;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
    )
    private List<Role> roles;
}
