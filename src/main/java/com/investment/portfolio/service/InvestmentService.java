package com.investment.portfolio.service;

import com.investment.portfolio.model.Investment;
import com.investment.portfolio.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;

    public List<Investment> getUserInvestments(Long userId) {
        return investmentRepository.findByUserId(userId);
    }

    public Map<String, BigDecimal> getAssetAllocation(Long userId) {
        List<Object[]> allocation = investmentRepository.getAssetAllocationByUserId(userId);
        Map<String, BigDecimal> result = new HashMap<>();
        for (Object[] entry : allocation) {
            result.put((String) entry[0], (BigDecimal) entry[1]);
        }
        return result;
    }

    public BigDecimal getTotalPortfolioValue(Long userId) {
        BigDecimal total = investmentRepository.getTotalPortfolioValue(userId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalReturn(Long userId) {
        BigDecimal totalReturn = investmentRepository.getTotalReturn(userId);
        return totalReturn != null ? totalReturn : BigDecimal.ZERO;
    }

    public BigDecimal getReturnPercentage(Long userId) {
        BigDecimal totalValue = getTotalPortfolioValue(userId);
        BigDecimal totalReturn = getTotalReturn(userId);
        
        if (totalValue.subtract(totalReturn).compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        return totalReturn.divide(totalValue.subtract(totalReturn), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(100));
    }
}