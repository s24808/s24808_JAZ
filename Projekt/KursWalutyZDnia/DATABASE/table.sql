CREATE TABLE currency_query2 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency TINYINT NOT NULL,
    calculated_rate DOUBLE NOT NULL,
    query_date DATE NOT NULL,
    query_time TIME NOT NULL
);
