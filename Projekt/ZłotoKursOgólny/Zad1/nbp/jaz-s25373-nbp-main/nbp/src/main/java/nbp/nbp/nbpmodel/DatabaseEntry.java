package nbp.nbp.nbpmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


    @Entity(name = "java")
    @Table(name = "java")
    public class DatabaseEntry {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @Column(name = "walute")
        private String walute;

        @Column(name = "DATE_START")
        @Schema(name = "Title", description ="Początkowa data")
                private String date_start;
        @Column(name = "DATE_END")
        @Schema(name = "Title", description ="Końcowa data")
        private String date_end;
        @Column(name = "WYNIK")
        @Schema(name = "Title", description ="Mediana z wybranego zakresu dni")
        private double wynik;
        @Column(name = "DATA_ZAPISU")
        @Schema(name = "Title", description ="Data zapisu")
        private String data_zapisu;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getWalute() {
            return walute;
        }

        public void setWalute(String walute) {
            this.walute = walute;
        }

        public String getDate_start() {
            return date_start;
        }

        public void setDate_start(String date_start) {
            this.date_start = date_start;
        }

        public String getDate_end() {
            return date_end;
        }

        public void setDate_end(String date_end) {
            this.date_end = date_end;
        }

        public double getWynik() {
            return wynik;
        }

        public void setWynik(double wynik) {
            this.wynik = wynik;
        }

        public String getData_zapisu() {
            return data_zapisu;
        }

        public void setData_zapisu(String data_zapisu) {
            this.data_zapisu = data_zapisu;
        }

        public DatabaseEntry(){

        }

        public DatabaseEntry(Integer id, String walute, String date_start, String date_end, double wynik, String data_zapisu) {
            this.id = id;
            this.walute = walute;
            this.date_start = date_start;
            this.date_end = date_end;
            this.wynik = wynik;
            this.data_zapisu = data_zapisu;
        }

        @Override
        public String toString() {
            return "DatabaseEntry{" +
                    "id=" + id +
                    ", walute='" + walute + '\'' +
                    ", date_start='" + date_start + '\'' +
                    ", date_end='" + date_end + '\'' +
                    ", wynik=" + wynik +
                    ", data_zapisu='" + data_zapisu + '\'' +
                    '}';
        }
    }
