package com.pm.projetpkmn;

public class stats {
    private String dpName = "";
    private String experience ="";
    private String bedwarsplg = "";
    private String bedwarsWin ="";
    private String bedwarsLvl ="";
    private String language = "";
    private String UuId = "";
    private String skwins = "";
    private String skwint = "";

    public String getSkwint() {
        return skwint;
    }

    public void setSkwint(String skwint) {
        this.skwint = skwint;
    }

    public String getBedwarsplg() {
        return bedwarsplg;
    }

    public void setBedwarsplg(String bedwarsplg) {
        this.bedwarsplg = bedwarsplg;
    }

    public String getSkwins() {
        return skwins;
    }

    public void setSkwins(String skwins) {
        this.skwins = skwins;
    }

    public stats() {    }

    public String getLanguage() {
        return language;
    }

    public String getUuId() {
        return UuId;
    }

    public void setUuId(String uuId) {
        UuId = uuId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

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
