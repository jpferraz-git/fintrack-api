CREATE TABLE IF NOT EXISTS binance_klines (
    symbol VARCHAR(20) NOT NULL,
    open_time BIGINT NOT NULL,
    open DECIMAL(28,8),
    high DECIMAL(28,8),
    low DECIMAL(28,8),
    close DECIMAL(28,8),
    volume DECIMAL(28,8),
    close_time BIGINT,
    quote_asset_volume DECIMAL(28,8),
    number_of_trades INTEGER,
    taker_buy_base_asset_volume DECIMAL(28,8),
    taker_buy_quote_asset_volume DECIMAL(28,8),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (symbol, open_time)
);