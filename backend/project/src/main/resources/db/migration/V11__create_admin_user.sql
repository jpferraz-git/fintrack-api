INSERT INTO users (name, email, password, role_id)
SELECT'admin',
    'admin@email.com',
    '$2a$12$h/CCVi/UxEUMgbx7tE0dlez3holVwkXgDwsWJBWxsNlFizObU53xm',
    r.role_id
FROM roles r
WHERE r.name = 'ADMIN'
  AND NOT EXISTS (
    SELECT 1 FROM users u WHERE u.email = 'admin@email.com'
    );