package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shatalov.cryptotrading.entity.Transaction;

import java.sql.Date;
import java.util.UUID;

public interface ITransactionRepository extends JpaRepository<Transaction, UUID> {
    long countByDateBetween(Date dateStart, Date dateEnd);
}