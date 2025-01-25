package org.plamen.userwallet.service;

import org.plamen.userwallet.entity.Transaction;
import org.plamen.userwallet.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    Wallet getWallet(Long walletId);
    Wallet createWallet(Long userId, String name);
    BigDecimal getBalance(Long walletId);
    boolean deposit(Long walletId, BigDecimal amount);
    boolean withdraw(Long walletId, BigDecimal amount);
    Wallet updateWallet(Long walletId, String newName);
    Wallet deleteWallet(Long walletId);
    List<Transaction> getTransactions(Long walletId);
}
