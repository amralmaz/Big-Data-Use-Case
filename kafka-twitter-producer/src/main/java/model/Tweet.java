package ml.dhoomilsheta.app.model;

import com.google.gson.annotations.SerializedName;

import org.json.*;


public class Tweet {
    private long id;
    private String text;
    private String lang;
    private User user;
    private boolean possibly_sensitive;
    private long timestamp_ms ;


    public boolean isPossibly_sensitive() {
        return possibly_sensitive;
    }

    public void setPossibly_sensitive(boolean possibly_sensitive) {
        this.possibly_sensitive = possibly_sensitive;
    }

    @SerializedName("retweet_count")
    private int retweetCount;

    @SerializedName("favorite_count")
    private int favoriteCount;

    public Tweet(long id, String text, String lang, User user,long timestamp_ms) {
        this.id = id;
        this.text = text;
        this.lang = lang;
        this.user = user;
        this.timestamp_ms = timestamp_ms;
    }

    public long getTimestamp_ms() {
        return timestamp_ms;
    }

    public void setTimestamp_ms(long timestamp_ms) {
        this.timestamp_ms = timestamp_ms;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }


    @Override
    public String toString() {


        String jsonString = new JSONObject()
                .put("id", id)
                .put("text", text)
                .put("lang", lang)
                .put("user_id", user.getId())
                .put("user_name", user.getName())
                .put("user_screenName", user.getScreenName())
                .put("user_location", user.getLocation())
                .put("user_followersCount", user.getFollowersCount())
                .put("verified", user.isVerified())
                .put("possibly_sensitive", possibly_sensitive)
                .put("timestamp_ms", timestamp_ms)
                .toString();

        return jsonString;
    }
}
