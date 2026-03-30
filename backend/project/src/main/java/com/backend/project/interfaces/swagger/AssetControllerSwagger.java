package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Asset", description = "Operations for managing financial assets")
public interface AssetControllerSwagger {

    @Operation(summary = "List all assets", description = "Retrieves all registered assets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Assets retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AssetResponseDTO.class))))
    })
    ResponseEntity<List<AssetResponseDTO>> findAll();

    @Operation(summary = "Create an asset", description = "Creates a new asset from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asset created successfully",
                    content = @Content(schema = @Schema(implementation = AssetResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Asset already exists", content = @Content)
    })
    ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Asset payload to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AssetRequestDTO.class)))
            AssetRequestDTO asset);

    @Operation(summary = "Update an asset", description = "Updates an existing asset by its ticker symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Asset updated successfully",
                    content = @Content(schema = @Schema(implementation = AssetResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asset not found", content = @Content)
    })
    ResponseEntity<?> update(
            @Parameter(description = "Ticker symbol used to identify the asset", example = "AAPL", required = true)
            String ticker,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated asset payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AssetRequestDTO.class)))
            AssetRequestDTO asset);

    @Operation(summary = "Delete an asset", description = "Deletes an asset by ticker symbol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Asset deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asset not found", content = @Content)
    })
    ResponseEntity<?> deleteByTicker(
            @Parameter(description = "Ticker symbol of the asset to delete", example = "AAPL", required = true)
            String ticker);
}

