package com.investment.portfolio.repository;

import com.investment.portfolio.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUserId(Long userId);

    @Query("SELECT i.type, SUM(i.quantity * i.currentPrice) FROM Investment i WHERE i.user.id = :userId GROUP BY i.type")
    List<Object[]> getAssetAllocationByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(i.quantity * i.currentPrice) FROM Investment i WHERE i.user.id = :userId")
    BigDecimal getTotalPortfolioValue(@Param("userId") Long userId);

    @Query("SELECT SUM(i.quantity * i.currentPrice - i.quantity * i.purchasePrice) FROM Investment i WHERE i.user.id = :userId")
    BigDecimal getTotalReturn(@Param("userId") Long userId);
}