package com.nb_netmusic.bean;

public class Score {
    private String username;
    private int id;
    private int score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", score=" + score +
                '}';
    }
}
