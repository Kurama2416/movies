package com.crud.peliculas.modelo;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "socios")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false, length = 15)
    private String telefono;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prestamo> prestamos = new ArrayList<>();
}