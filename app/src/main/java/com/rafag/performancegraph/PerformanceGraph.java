package com.rafag.performancegraph;

import java.util.List;

/**
 * Created by Rafael Garcia on 06/09/15.
 */
public class PerformanceGraph {

    //List of skills
    List<Skill> skillList;
    //Graph color
    int color;

    public PerformanceGraph(List<Skill> skillList, int color) {
        this.skillList = skillList;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
}
