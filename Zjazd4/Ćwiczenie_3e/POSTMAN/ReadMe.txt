http://localhost:8080/movies/1

1 to id filmu, który chcesz zaktualizować.

W sekcji "Body" wybierz opcję "raw" i format danych "JSON".

W polu tekstowym wprowadź dane filmu, które chcesz zaktualizować. Na przykład:
{
  "name": "Nowa nazwa filmu",
  "category": "Nowa kategoria"
}

NIE PRZESYŁAMY POLA ID, BO JEST OKREŚLONE W ŚCIEŻCE
