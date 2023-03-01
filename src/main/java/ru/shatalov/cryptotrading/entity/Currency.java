package ru.shatalov.cryptotrading.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Currency {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Currency currency = (Currency) o;
        return name != null && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
