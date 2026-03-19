CREATE TABLE assets (
    asset_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    ticker VARCHAR(10) NOT NULL UNIQUE,
    asset_type VARCHAR(20) NOT NULL,
    company_name VARCHAR(150) NOT NULL,

    CONSTRAINT chk_asset_type
    CHECK (asset_type IN ('ACAO', 'FII'))
);
