package org.plamen.userwallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.plamen.userwallet.dao.WalletRepository;
import org.plamen.userwallet.entity.Status;
import org.plamen.userwallet.entity.Transaction;
import org.plamen.userwallet.entity.Type;
import org.plamen.userwallet.entity.Wallet;
import org.plamen.userwallet.exception.NonexistentEntityException;
import org.plamen.userwallet.service.WalletServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(1L, "Test Wallet");
        wallet.setBalance(BigDecimal.valueOf(100));
    }

    @Test
    void testGetWallet() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        Wallet res = walletService.getWallet(1L);

        assertNotNull(res);
        assertEquals(wallet, res);

        verify(walletRepository, times(1)).findByIdQuery(1L);
    }

    @Test
    void testGetWalletThrowsException() {
        when(walletRepository.findByIdQuery(100L)).thenReturn(Optional.empty());

        assertThrows(NonexistentEntityException.class, () -> walletService.getWallet(100L));

        verify(walletRepository, times(1)).findByIdQuery(100L);
    }

    @Test
    void testCreateWallet() {
        Wallet res = walletService.createWallet(wallet.getUserId(), wallet.getName());

        assertNotNull(res);
        assertEquals(wallet.getUserId(), res.getUserId());
        assertEquals(wallet.getName(), res.getName());
        assertEquals(Status.ACTIVE, res.getStatus());

        verify(walletRepository, times(1)).save(res);
    }

    @Test
    void testDepositValidAmount() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        boolean res = walletService.deposit(1L, BigDecimal.TEN);

        assertTrue(res);
        assertEquals(BigDecimal.valueOf(110), wallet.getBalance());
        assertEquals(Status.ACTIVE, wallet.getStatus());

        assertFalse(wallet.getTransactions().isEmpty());
        Transaction transaction = wallet.getTransactions().getFirst();
        assertEquals(Type.DEPOSIT, transaction.getType());
        assertEquals(BigDecimal.TEN, transaction.getAmount());

        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testDepositInvalidAmountReturnsFalse() {
        boolean res = walletService.deposit(1L, BigDecimal.valueOf(-10));

        assertFalse(res);

        verify(walletRepository, never()).save(wallet);
    }

    @Test
    void testWithdrawSufficientBalance() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        boolean res = walletService.withdraw(1L, BigDecimal.TEN);

        assertTrue(res);
        assertEquals(BigDecimal.valueOf(90), wallet.getBalance());
        assertEquals(Status.ACTIVE, wallet.getStatus());

        assertFalse(wallet.getTransactions().isEmpty());
        Transaction transaction = wallet.getTransactions().getFirst();
        assertEquals(Type.WITHDRAWAL, transaction.getType());
        assertEquals(BigDecimal.TEN, transaction.getAmount());

        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testWithdrawInsufficientBalanceReturnsFalse() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        boolean res = walletService.withdraw(1L, BigDecimal.valueOf(200));

        assertFalse(res);

        verify(walletRepository, never()).save(wallet);
    }

    @Test
    void testUpdateWallet() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        String newName = "New Wallet";
        Wallet res = walletService.updateWallet(wallet.getUserId(), newName);

        assertNotNull(res);
        assertEquals(newName, res.getName());
        assertEquals(wallet.getUserId(), res.getUserId());
        assertEquals(Status.ACTIVE, res.getStatus());

       verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    void testDeleteWallet() {
        when(walletRepository.findByIdQuery(1L)).thenReturn(Optional.of(wallet));

        Wallet res = walletService.deleteWallet(1L);

        assertEquals(Status.DELETED, res.getStatus());

        verify(walletRepository, times(1)).save(wallet);
    }
}
