INSERT INTO roles (id, name)
VALUES (1,'ROLE_ADMIN')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles (id, name)
VALUES (2,'ROLE_COOK')
ON DUPLICATE KEY UPDATE name = name;

INSERT INTO roles (id, name)
VALUES (3,'ROLE_BASIC_USER')
ON DUPLICATE KEY UPDATE name = name;
