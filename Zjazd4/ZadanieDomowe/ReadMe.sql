//Dodawanie danych

INSERT INTO Movie (id, name, category)
VALUES (1, 'Film 1', 'Kategoria 1'),
       (2, 'Film 2', 'Kategoria 2'),
       (3, 'Film 3', 'Kategoria 3');

//Wyświetlanie

SELECT * FROM Movie;

//Typ string to inaczej varchar w mysqlWorkbench

//Kompletna instrukcja w razie problemów

1.Uruchom MySQL Server:

Po zainstalowaniu MySQL Server, uruchom aplikację MySQL Server, która powinna automatycznie rozpocząć nasłuchiwanie na domyślnym porcie 3306.
Sprawdź, czy serwer MySQL działa poprawnie.

2.Połącz się z MySQL Server za pomocą MySQL Workbench:

Uruchom MySQL Workbench na swoim komputerze.
Kliknij przycisk "+", aby dodać nowe połączenie.
Wprowadź nazwę połączenia, host (zwykle "localhost"), port (domyślnie 3306) oraz dane uwierzytelniające (nazwa użytkownika i hasło) dla swojego serwera MySQL.
Kliknij "Test Connection", aby sprawdzić połączenie. Jeśli test jest pomyślny, kliknij "OK", aby dodać połączenie.

3.Utwórz bazę danych dla tabeli Movie:

Po nawiązaniu połączenia z MySQL Server za pomocą MySQL Workbench, kliknij prawym przyciskiem myszy na "SCHEMAS" w panelu bocznym i wybierz "Create Schema...".
Wprowadź nazwę bazy danych (np. "movies") i kliknij "Apply" lub "OK", aby utworzyć bazę danych.

4.Utwórz tabelę Movie i dodaj rekordy:

Kliknij dwukrotnie na utworzoną bazę danych "movies", aby ją otworzyć.
Kliknij prawym przyciskiem myszy na "Tables" w panelu bocznym i wybierz "Create Table...".
Zdefiniuj nazwy kolumn (np. "id", "name", "category") i ich typy danych zgodnie z modelem klasy Movie.
Kliknij "Apply" lub "OK", aby utworzyć tabelę Movie.
Wybierz utworzoną tabelę Movie i użyj polecenia "Insert Records" lub podobnego, aby dodać kilka rekordów do tabeli.
