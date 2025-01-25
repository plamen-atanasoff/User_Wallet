package org.plamen.userwallet.service;

import org.plamen.userwallet.dao.WalletRepository;
import org.plamen.userwallet.entity.Status;
import org.plamen.userwallet.entity.Transaction;
import org.plamen.userwallet.entity.Type;
import org.plamen.userwallet.entity.Wallet;
import org.plamen.userwallet.exception.NonexistentEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet getWallet(Long walletId) {
        return walletRepository.findByIdQuery(walletId)
            .orElseThrow(() -> new NonexistentEntityException(String.format("Wallet with id %s not found", walletId)));
    }

    @Override
    public Wallet createWallet(Long userId, String name) {
        Wallet wallet = new Wallet(userId, name);
        LocalDateTime now = LocalDateTime.now();
        wallet.setCreated(now);
        wallet.setModified(now);
        walletRepository.save(wallet);

        return wallet;
    }

    @Override
    public BigDecimal getBalance(Long walletId) {
        return getWallet(walletId).getBalance();
    }

    @Override
    public boolean deposit(Long walletId, BigDecimal amount) {
        if(isNotValid(amount)) {
            return false;
        }

        Wallet wallet = getWallet(walletId);
        Transaction deposit = new Transaction(amount, Type.DEPOSIT);
        wallet.getTransactions().add(deposit);
        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public boolean withdraw(Long walletId, BigDecimal amount) {
        if(isNotValid(amount)) {
            return false;
        }

        Wallet wallet = getWallet(walletId);
        BigDecimal balance = wallet.getBalance();
        if (balance.compareTo(amount) < 0) {
            return false;
        }

        Transaction withdrawal = new Transaction(amount, Type.WITHDRAWAL);
        wallet.getTransactions().add(withdrawal);
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        return true;
    }

    @Override
    public Wallet updateWallet(Long walletId, String newName) {
        Wallet wallet = getWallet(walletId);
        wallet.setName(newName);
        wallet.setModified(LocalDateTime.now());
        walletRepository.save(wallet);

        return wallet;
    }

    @Override
    public Wallet deleteWallet(Long walletId) {
        Wallet wallet = getWallet(walletId);
        wallet.setStatus(Status.DELETED);
        wallet.setModified(LocalDateTime.now());
        walletRepository.save(wallet);

        return wallet;
    }

    @Override
    public List<Transaction> getTransactions(Long walletId) {
        return getWallet(walletId).getTransactions();
    }

    private boolean isNotValid(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
