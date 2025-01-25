package org.plamen.userwallet.dao;

import org.plamen.userwallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.id = :id AND w.status <> 'DELETED'")
    Optional<Wallet> findByIdQuery(@Param("id") Long id);
}
