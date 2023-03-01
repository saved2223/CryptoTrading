package ru.shatalov.cryptotrading.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shatalov.cryptotrading.entity.Person;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<Person, UUID> {
    Optional<Person> findByUserNameOrEmail(String userName, String email);
}