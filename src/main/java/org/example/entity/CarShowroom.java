package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car_showroom")
@Getter
@Setter
public class CarShowroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "showroom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Car> cars = new ArrayList<>();
}