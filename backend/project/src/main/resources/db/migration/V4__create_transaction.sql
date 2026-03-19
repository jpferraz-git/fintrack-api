CREATE TABLE movimentacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,
    asset_id UUID NOT NULL,
    batch_id UUID,

    operation_type VARCHAR(10) NOT NULL,
    quantity INTEGER NOT NULL,
    unit_price NUMERIC(15,2) NOT NULL,
    operation_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_transaction_user
        FOREIGN KEY (user_id)
        REFERENCES usuario(user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_transaction_asset
        FOREIGN KEY (asset_id)
        REFERENCES ativo(asset_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_transaction_batch
        FOREIGN KEY (batch_id)
        REFERENCES lote_importacao(batch_id)
        ON DELETE SET NULL,

    CONSTRAINT chk_operation_type
        CHECK (operation_type IN ('COMPRA', 'VENDA')),

    CONSTRAINT chk_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_unit_price
        CHECK (unit_price >= 0)
);
