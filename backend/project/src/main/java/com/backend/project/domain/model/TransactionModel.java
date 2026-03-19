package com.backend.project.domain.model;

import java.util.UUID;

public class TransactionModel {

    private UUID id;
    private UUID user_id;
    private UUID asset_id;
    private UUID batch_id;
    private String operation_type;
    private int quantity;
    private double unit_price;
    private String operation_date;
    private String created_at;
    private String updated_at;
}
