package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shatalov.cryptotrading.entity.CurrencyAmount;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICurrencyAmountRepository extends JpaRepository<CurrencyAmount, UUID> {
    List<CurrencyAmount> findByCurrency_Name(String name);
}