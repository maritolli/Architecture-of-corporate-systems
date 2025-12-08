CREATE TABLE change_log (
    id BIGSERIAL PRIMARY KEY,
    entity_name VARCHAR(100) NOT NULL,
    operation VARCHAR(20) NOT NULL,
    change_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    old_value TEXT,
    new_value TEXT
);
