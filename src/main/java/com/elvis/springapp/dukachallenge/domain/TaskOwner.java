package com.elvis.springapp.dukachallenge.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class TaskOwner implements Serializable {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    @Builder.Default
    private Boolean isVerified=false;
}
