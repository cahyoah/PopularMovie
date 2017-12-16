package wkwkw.asek.cataloguemovie.Model;

import org.json.JSONObject;

/**
 * Created by ASUS on 16/12/2017.
 */

public class Review {
    private String author;
    private String content;

    public Review(JSONObject review) {

        try {
            String author = review.getString("author");
            String content = review.getString("content");
            this.author = author;
            this.content = content;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
