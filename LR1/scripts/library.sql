CREATE DATABASE library_db;

\c library_db;

CREATE TABLE Author (
                        author_id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        country VARCHAR(50) NOT NULL
);

CREATE TABLE Book (
                      book_id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(200) NOT NULL,
                      author_id BIGINT REFERENCES Author(author_id) ON DELETE CASCADE
);

CREATE TABLE Reader (
                        reader_id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        phone_number VARCHAR(20) NOT NULL
);

CREATE TABLE Borrowing (
                           borrowing_id BIGSERIAL PRIMARY KEY,
                           reader_id BIGINT REFERENCES Reader(reader_id) ON DELETE CASCADE,
                           book_id BIGINT REFERENCES Book(book_id) ON DELETE CASCADE,
                           borrow_date DATE NOT NULL DEFAULT CURRENT_DATE,
                           UNIQUE(book_id) -- книга может быть выдана только одному читателю
);

INSERT INTO Author (name, country) VALUES
                                       ('Лев Толстой', 'Россия'),
                                       ('Федор Достоевский', 'Россия'),
                                       ('Эрнест Хемингуэй', 'США'),
                                       ('Джоан Роулинг', 'Великобритания');

INSERT INTO Book (title, author_id) VALUES
                                        ('Война и мир', 1),
                                        ('Анна Каренина', 1),
                                        ('Преступление и наказание', 2),
                                        ('Идиот', 2),
                                        ('Старик и море', 3),
                                        ('Прощай, оружие!', 3),
                                        ('Гарри Поттер и философский камень', 4),
                                        ('Гарри Поттер и Тайная комната', 4);

INSERT INTO Reader (name, phone_number) VALUES
                                            ('Иван Иванов', '+7-999-123-45-67'),
                                            ('Петр Петров', '+7-999-987-65-43'),
                                            ('Мария Сидорова', '+7-916-555-44-33'),
                                            ('Анна Козлова', '+7-925-111-22-33');

INSERT INTO Borrowing (reader_id, book_id, borrow_date) VALUES
                                                            (1, 1, '2024-01-15'),
                                                            (1, 2, '2024-01-16'),
                                                            (2, 3, '2024-01-16'),
                                                            (3, 5, '2024-01-17'),
                                                            (4, 7, '2024-01-18');

ALTER TABLE Book
ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'В библиотеке'
CHECK (status IN ('В библиотеке', 'На руках'));

UPDATE Book
SET status = 'На руках'
WHERE book_id IN (SELECT book_id FROM Borrowing);