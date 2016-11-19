package com.portiq.www.portiq.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Question implements Serializable {
    public String username;
    public String body;
    public String answer;
    public int rating;

    public Map<String, Boolean> stars = new HashMap<>();

    public Question() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Question(String username, String body, String answer, int rating) {
        this.username = username;
        this.body = body;
        this.answer = answer;
        this.rating = rating;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("body", body);
        result.put("answer", answer);
        result.put("rating", rating);
        return result;
    }

}
