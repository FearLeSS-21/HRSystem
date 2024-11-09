package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Employee {

    protected String name;
    protected LocalDate dateOfBirth;
    protected String gender;
    protected LocalDate graduationDate;
    protected String department;

    @ElementCollection
    @CollectionTable(name = "expertise", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "expertise")
    private Set<String> expertise;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
