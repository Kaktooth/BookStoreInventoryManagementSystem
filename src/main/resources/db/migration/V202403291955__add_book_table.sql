CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE book
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v1(),
    title    VARCHAR(100)       NOT NULL,
    author   VARCHAR(80)        NOT NULL,
    genre_id INTEGER
        CONSTRAINT fk_genre_id REFERENCES book_genre (id),
    isbn     VARCHAR(13) UNIQUE NOT NULL,
    quantity INTEGER
);