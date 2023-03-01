package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shatalov.cryptotrading.entity.Currency;
import ru.shatalov.cryptotrading.entity.Rate;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IRateRepository extends JpaRepository<Rate, UUID> {
    Optional<Rate> findByCurrencyFromAndCurrencyTo(Currency currencyFrom, Currency currencyTo);

    Set<Rate> findByCurrencyFrom(Currency currencyFrom);

}