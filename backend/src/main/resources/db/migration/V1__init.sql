CREATE TABLE policies (
    id BIGSERIAL PRIMARY KEY,
    policy_name VARCHAR(255) NOT NULL,
    policy_type VARCHAR(255) NOT NULL,
    description TEXT,
    severity VARCHAR(100),
    status VARCHAR(100),
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);