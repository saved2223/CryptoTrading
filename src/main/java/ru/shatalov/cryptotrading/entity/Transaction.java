package ru.shatalov.cryptotrading.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_from_id")
    private Wallet walletFrom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_to_id")
    private Wallet walletTo;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "currency_name", nullable = false)
    private Currency currency;

}
