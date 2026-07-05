package com.backend.project.interfaces.controllers;

import static com.backend.project.interfaces.controllers.utils.Normalizer.errorResponse;



import com.backend.project.application.service.AssetService;
import com.backend.project.application.Result;
import com.backend.project.interfaces.dto.asset.AssetActualValueRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetCalculationResponseDTO;
import com.backend.project.interfaces.dto.asset.AssetQuantityCalculationRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;

@RestController
@RequestMapping("/asset")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AssetRequestDTO asset) {
        Result<AssetResponseDTO> result = assetService.create(asset);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<?> update(@PathVariable String symbol, @RequestBody AssetRequestDTO asset) {
        Result<AssetResponseDTO> result = assetService.update(symbol, asset);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBySymbol(@RequestParam String symbol) {
        Result<Void> result = assetService.deleteBySymbol(symbol);
        if (result.isOk()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PostMapping("/calculate-quantity")
    public ResponseEntity<?> calculateQuantityByInvestment(@RequestBody AssetQuantityCalculationRequestDTO dto) {
        Result<BigDecimal> result = assetService.calculateQuantityByInvestment(dto.symbol(), dto.investedValue());
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PostMapping("/calculate-actual-value")
    public ResponseEntity<?> calculateActualValue(@RequestBody AssetActualValueRequestDTO dto) {
        Result<BigDecimal> result = assetService.calculateActualValue(dto.symbol());
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PostMapping("/calculate-profit-percentage")
    public ResponseEntity<?> calculateProfitPercentage(@RequestBody AssetActualValueRequestDTO dto) {
        Result<BigDecimal> result = assetService.calculateProfitPercentage(dto.symbol());
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PostMapping("/calculate-profit-value")
    public ResponseEntity<?> calculateProfitValue(@RequestBody AssetActualValueRequestDTO dto) {
        Result<BigDecimal> result = assetService.calculateProfitValue(dto.symbol());
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @GetMapping("/calculate-total-profit-percentage")
    public ResponseEntity<?> calculateTotalProfitPercentage() {
        Result<BigDecimal> result = assetService.calculateTotalProfitPercentage();
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @GetMapping("/calculate-total-profit-value")
    public ResponseEntity<?> calculateTotalProfitValue() {
        Result<BigDecimal> result = assetService.calculateTotalProfitValue();
        if (result.isOk()) {
            return ResponseEntity.ok(new AssetCalculationResponseDTO(result.getValue()));
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }
}
