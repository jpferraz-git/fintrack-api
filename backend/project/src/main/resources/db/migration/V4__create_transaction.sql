CREATE TABLE transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    fk_user UUID NOT NULL,

    symbol VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    quantity NUMERIC(18,8) NOT NULL,
    price NUMERIC(18,2) NOT NULL,

    CONSTRAINT fk_transaction_user
        FOREIGN KEY (fk_user)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_operation_type
        CHECK (type IN ('BUY, SELL')),

    CONSTRAINT chk_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_unit_price
        CHECK (price >= 0)
);
