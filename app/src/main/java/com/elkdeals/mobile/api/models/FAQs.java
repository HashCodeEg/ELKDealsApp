package com.elkdeals.mobile.api.models;

import com.google.gson.annotations.SerializedName;

public class FAQs {
    @SerializedName("id")    String id;
    @SerializedName("title")    String title;
    @SerializedName("titlear")    String titlear;
    @SerializedName("answer")    String answer;
    @SerializedName("answerar")    String answerar;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlear() {
        return titlear;
    }

    public void setTitlear(String titlear) {
        this.titlear = titlear;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerar() {
        return answerar;
    }

    public void setAnswerar(String answerar) {
        this.answerar = answerar;
    }
}
