package com.investment.portfolio.controller;

import com.investment.portfolio.model.Investment;
import com.investment.portfolio.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @GetMapping
    public ResponseEntity<List<Investment>> getUserInvestments(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(investmentService.getUserInvestments(userId));
    }

    @GetMapping("/allocation")
    public ResponseEntity<Map<String, BigDecimal>> getAssetAllocation(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        return ResponseEntity.ok(investmentService.getAssetAllocation(userId));
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getPortfolioSummary(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        Map<String, Object> summary = Map.of(
            "totalValue", investmentService.getTotalPortfolioValue(userId),
            "totalReturn", investmentService.getTotalReturn(userId),
            "returnPercentage", investmentService.getReturnPercentage(userId)
        );
        return ResponseEntity.ok(summary);
    }
}