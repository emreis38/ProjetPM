package com.pm.projetpkmn;

public class stats {
    private String dpName;
    private String experience;
    private String bedwarsWin;
    private String bedwarsLvl;
    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public stats() {    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBedwarsWin() {
        return bedwarsWin;
    }

    public void setBedwarsWin(String bedwarsWin) {
        this.bedwarsWin = bedwarsWin;
    }

    public String getBedwarsLvl() {
        return bedwarsLvl;
    }

    public void setBedwarsLvl(String bedwarsLvl) {
        this.bedwarsLvl = bedwarsLvl;
    }
}
