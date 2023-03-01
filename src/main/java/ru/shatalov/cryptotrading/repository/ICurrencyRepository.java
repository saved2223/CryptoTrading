package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shatalov.cryptotrading.entity.Currency;

public interface ICurrencyRepository extends JpaRepository<Currency, String> {
}