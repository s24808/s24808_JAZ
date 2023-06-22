Odpalamy poprzedni projekt jednocześnie z tym i wykonujemy zapytania w PSOTMANIE

//Wyświetlenie w formacie JSON filmy z listy o id 6
http://localhost:8081/rental/movies/6

//Zmiana wartości isAvailable na true dla filmu o id 6 , reutrn zmienia na true, zobacz mapowanie na MovieController
http://localhost:8081/rental/movies/6/return

//Zmiana wartości isAvailable na false dla filmu o id 1, rent zmiena na false, zobacz mapowanie na MovieController
http://localhost:8081/rental/movies/1/rent
