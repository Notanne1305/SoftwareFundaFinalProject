package org.example.softfun_funsoft.lang;

public class LangCheck {

    private static Language language = Language.ENGLISH;

    private LangCheck(){

    }

    public static void setLang(Language language) { LangCheck.language = language; }
    public static boolean isEnglish(){
        return  language == Language.ENGLISH;
    }

    public static boolean isCebuano(){
        return language == Language.CEBUANO;
    }

    public static boolean isTagalog(){
        return language == Language.TAGALOG;
    }

    public static Language getLanguage(){
        return language;
    }

}
