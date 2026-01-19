-- Вставляем 10 случайных авторов
INSERT INTO author (name)
SELECT
    'Author_' || trunc(random() * 1000)::int
FROM generate_series(1, 10);

-- Вставляем 20 случайных книг
INSERT INTO book (title, publication_year, author_id)
SELECT
    'Book_' || trunc(random() * 1000)::int,       -- случайное название
    1990 + trunc(random() * 30)::int,            -- случайный год между 1990 и 2020
    (SELECT id FROM author ORDER BY random() LIMIT 1)  -- случайный автор
FROM generate_series(1, 20);