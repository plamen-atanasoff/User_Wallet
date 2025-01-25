package org.plamen.userwallet.web;

import jakarta.validation.Valid;
import org.plamen.userwallet.dto.TransactionDto;
import org.plamen.userwallet.dto.WalletDto;
import org.plamen.userwallet.entity.Transaction;
import org.plamen.userwallet.entity.Wallet;
import org.plamen.userwallet.service.WalletService;
import org.plamen.userwallet.utils.ErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletRestController {
    private final WalletService walletService;

    @Autowired
    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    public Wallet getWallet(@PathVariable Long id) {
        return walletService.getWallet(id);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return walletService.getBalance(id);
    }

    @PostMapping
    public Wallet createWallet(@Valid @RequestBody WalletDto walletDto, Errors errors) {
        ErrorHandling.handleValidationErrors(errors);

        return walletService.createWallet(walletDto.getUserId(), walletDto.getName());
    }

    @PostMapping("/{id}/deposit")
    public boolean deposit(@PathVariable Long id, @Valid @RequestBody TransactionDto amountDto, Errors errors) {
        ErrorHandling.handleValidationErrors(errors);

        return walletService.deposit(id, amountDto.getAmount());
    }

    @PostMapping("/{id}/withdraw")
    public boolean withdraw(@PathVariable Long id, @Valid @RequestBody TransactionDto amountDto, Errors errors) {
        ErrorHandling.handleValidationErrors(errors);

        return walletService.withdraw(id, amountDto.getAmount());
    }

    @PutMapping("/{id}")
    public Wallet updateWallet(@PathVariable Long id, @Valid @RequestBody WalletDto walletDto, Errors errors) {
        ErrorHandling.handleValidationErrors(errors);

        return walletService.updateWallet(id, walletDto.getName());
    }

    @DeleteMapping("/{id}")
    public Wallet deleteWallet(@PathVariable Long id) {
        return walletService.deleteWallet(id);
    }

    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        return walletService.getTransactions(id);
    }
}
