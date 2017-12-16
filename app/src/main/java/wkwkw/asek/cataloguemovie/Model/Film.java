package wkwkw.asek.cataloguemovie.Model;

import org.json.JSONObject;



public class Film {



    private int idFilm;
    private String urlPosterFilm;
    private String judulFilm;
    private String deskripsiFilm;
    private String tanggalRilis;

    public Film(int idFilm, String urlPosterFilm, String judulFilm, String deskripsiFilm, String tanggalRilis) {
        this.idFilm = idFilm;
        this.urlPosterFilm = urlPosterFilm;
        this.judulFilm = judulFilm;
        this.deskripsiFilm = deskripsiFilm;
        this.tanggalRilis = tanggalRilis;
    }

    public Film(JSONObject film) {

        try {
            int id = film.getInt("id");
            String judulFilm = film.getString("title");
            String deskripsiFilm = film.getString("overview");
            String tanggalRilis = film.getString("release_date");
            String pathPoster = film.getString("poster_path");

            this.idFilm = id;
            this.judulFilm= judulFilm;
            this.deskripsiFilm = deskripsiFilm;
            this.tanggalRilis = tanggalRilis;
            this.urlPosterFilm ="http://image.tmdb.org/t/p/w185"+pathPoster;


        }catch (Exception e){

            e.printStackTrace();

        }
    }

    public String getDeskripsiFilm() {
        return deskripsiFilm;
    }

    public String getUrlPosterFilm() {
        return urlPosterFilm;
    }

    public String getJudulFilm() {
        return judulFilm;
    }

    public String getTanggalRilis() {
        return tanggalRilis;
    }

    public int getIdFilm() {
        return idFilm;
    }


}
