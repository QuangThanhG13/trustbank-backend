package org.example.accoutservice.commands.util.generator.implementation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Couter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
