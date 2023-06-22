Komendy do SQL w razie problemów:

Usuwanie kolumny:
ALTER TABLE nazwa_tabeli
DROP COLUMN nazwa_kolumny;

Użycie bazy:
USE nazwa_bazy

Wyświetlenie wszystkich rekordów w tabeli:
SELECT * FROM nazwa_tabeli

Dodanie kolumny:
ALTER TABLE movies ADD COLUMN is_available BOOLEAN NOT NULL DEFAULT FALSE;
