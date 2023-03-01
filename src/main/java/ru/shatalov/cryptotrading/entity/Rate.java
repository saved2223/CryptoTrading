package ru.shatalov.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "currency_from_name", nullable = false)
    private Currency currencyFrom;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "currency_to_name", nullable = false)
    private Currency currencyTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Rate rate = (Rate) o;
        return id != null && Objects.equals(id, rate.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
