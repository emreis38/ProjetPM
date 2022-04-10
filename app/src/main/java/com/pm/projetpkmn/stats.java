package com.pm.projetpkmn;

public class stats {
    //Dans cette classe nous instantions toutes les valeurs que nous allons essayer de récupérer dans la page Stats
    //nous générons des méthodes de getters et de setters afin de modifier la valeur lors de la récupération
    //des informations, et ensuite pour la récupérer cette valeur pour l'affichage.

    private String dpName = ""; //Display name
    private String experience =""; //Experience
    private String bedwarsplg = ""; // bedwars played games
    private String bedwarsWin =""; // Bedwars win
    private String bedwarsLvl =""; //Bedwars level
    private String language = ""; //language
    private String UuId = ""; //UUid
    private String skwins = ""; //Skywars solo wins
    private String skwint = ""; //Skywars team wins

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
