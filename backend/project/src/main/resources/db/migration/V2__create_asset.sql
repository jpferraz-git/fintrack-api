CREATE TABLE assets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    symbol VARCHAR(15) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    quantity DECIMAL(18,8) NOT NULL,
    avg_price DECIMAL(18,2) NOT NULL
);
