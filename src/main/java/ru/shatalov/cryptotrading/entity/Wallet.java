package ru.shatalov.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Wallet {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CurrencyAmount> currencies = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Wallet wallet = (Wallet) o;
        return id != null && Objects.equals(id, wallet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
