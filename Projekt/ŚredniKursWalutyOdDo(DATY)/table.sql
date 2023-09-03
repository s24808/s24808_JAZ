CREATE TABLE currency_query (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency TINYINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    calculated_rate DOUBLE NOT NULL,
    query_date DATE NOT NULL,
    query_time TIME NOT NULL
);
