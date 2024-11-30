package org.example.softfun_funsoft.lang;

public enum Language {

    CEBUANO, TAGALOG, ENGLISH;

    @Override
    public String toString(){
        switch (this){
            case CEBUANO:
                return "Cebuano";
            case TAGALOG:
                return "Tagalog";
            case ENGLISH:
                return "English";
            default:
                return super.toString();
        }
    }
}
//+++ HERE