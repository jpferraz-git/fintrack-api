package com.backend.project.interfaces.controllers;


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
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @PutMapping("/{symbol}")
    public ResponseEntity<?> update(@PathVariable String symbol, @RequestBody AssetRequestDTO asset) {
        Result<AssetResponseDTO> result = assetService.update(symbol, asset);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBySymbol(@RequestParam String symbol) {
        Result<Void> result = assetService.deleteBySymbol(symbol);
        if (result.isOk()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @PostMapping("/calculate-quantity")
    public ResponseEntity<?> calculateQuantityByInvestment(@RequestBody AssetQuantityCalculationRequestDTO dto) {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateQuantityByInvestment(dto.symbol(), dto.investedValue())
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }

    @PostMapping("/calculate-actual-value")
    public ResponseEntity<?> calculateActualValue(@RequestBody AssetActualValueRequestDTO dto) {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateActualValue(dto.symbol())
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }

    @PostMapping("/calculate-profit-percentage")
    public ResponseEntity<?> calculateProfitPercentage(@RequestBody AssetActualValueRequestDTO dto) {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateProfitPercentage(dto.symbol())
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }

    @PostMapping("/calculate-profit-value")
    public ResponseEntity<?> calculateProfitValue(@RequestBody AssetActualValueRequestDTO dto) {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateProfitValue(dto.symbol())
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }

    @GetMapping("/calculate-total-profit-percentage")
    public ResponseEntity<?> calculateTotalProfitPercentage() {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateTotalProfitPercentage()
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }

    @GetMapping("/calculate-total-profit-value")
    public ResponseEntity<?> calculateTotalProfitValue() {
        try {
            return ResponseEntity.ok(
                    new AssetCalculationResponseDTO(
                            assetService.calculateTotalProfitValue()
                    )
            );
        } catch (Exception ex) {
            Result<AssetCalculationResponseDTO> result = Result.fail(ex.getMessage());
            return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
        }
    }
}
