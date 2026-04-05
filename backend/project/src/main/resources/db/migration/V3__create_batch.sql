CREATE TABLE batches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    file_name VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT chk_import_status
    CHECK (status IN ('PENDENTE', 'PROCESSANDO', 'CONCLUIDO', 'ERRO'))
);
