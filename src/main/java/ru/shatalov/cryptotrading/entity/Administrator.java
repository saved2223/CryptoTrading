package ru.shatalov.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "secret_key", nullable = false)
    private UUID secretKey;
}
