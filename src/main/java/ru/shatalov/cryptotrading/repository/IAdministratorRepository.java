package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shatalov.cryptotrading.entity.Administrator;

import java.util.List;
import java.util.UUID;

public interface IAdministratorRepository extends JpaRepository<Administrator, UUID> {


}