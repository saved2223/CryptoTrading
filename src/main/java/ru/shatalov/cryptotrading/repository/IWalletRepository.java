package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shatalov.cryptotrading.entity.Wallet;

import java.util.UUID;

public interface IWalletRepository extends JpaRepository<Wallet, UUID> {

}
