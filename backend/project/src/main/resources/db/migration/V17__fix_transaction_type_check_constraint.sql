ALTER TABLE transactions
    DROP CONSTRAINT IF EXISTS chk_operation_type;

ALTER TABLE transactions
    ADD CONSTRAINT chk_operation_type
    CHECK (type IN ('BUY', 'SELL'));
