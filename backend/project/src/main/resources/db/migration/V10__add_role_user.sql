ALTER TABLE users
    ADD COLUMN role_id UUID,
    ADD CONSTRAINT fk_role
    FOREIGN KEY (role_id) REFERENCES roles(role_id);