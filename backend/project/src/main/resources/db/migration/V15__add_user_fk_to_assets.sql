ALTER TABLE assets
    ADD COLUMN IF NOT EXISTS fk_user UUID;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'fk_assets_user'
    ) THEN
        ALTER TABLE assets
            ADD CONSTRAINT fk_assets_user
            FOREIGN KEY (fk_user)
            REFERENCES users(id)
            ON DELETE CASCADE;
    END IF;
END $$;

