package org.vital.domain;

import java.util.ArrayList;

public class RegionalBloc{
    public String acronym;
    public String name;
    public ArrayList<String> otherNames;
    public ArrayList<String> otherAcronyms;

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(ArrayList<String> otherNames) {
        this.otherNames = otherNames;
    }

    public ArrayList<String> getOtherAcronyms() {
        return otherAcronyms;
    }

    public void setOtherAcronyms(ArrayList<String> otherAcronyms) {
        this.otherAcronyms = otherAcronyms;
    }
}