package wkwkw.asek.cataloguemovie.Model;

import org.json.JSONObject;

/**
 * Created by ASUS on 16/12/2017.
 */

public class Trailer {
    private String keyTrailer;

    public Trailer(JSONObject trailer) {

        try {
            String keyTrailer = trailer.getString("key");
            this.keyTrailer = keyTrailer;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getKeyTrailer() {
        return keyTrailer;
    }
}
