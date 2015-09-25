package com.rafag.performancegraph;

/**
 * Created by Rafael Garcia on 06/09/15.
 */
public class Skill {

    int pointsScore;
    int graphScore;
    String name;
    int color;

    public Skill(int pointsScore, int graphScore, String name, int color) {
        this.pointsScore = pointsScore;
        this.graphScore = graphScore;
        this.name = name;
        this.color = color;
    }

    public int getPointsScore() {
        return pointsScore;
    }

    public void setPointsScore(int pointsScore) {
        this.pointsScore = pointsScore;
    }

    public int getGraphScore() {
        return graphScore;
    }

    public void setGraphScore(int graphScore) {
        this.graphScore = graphScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
