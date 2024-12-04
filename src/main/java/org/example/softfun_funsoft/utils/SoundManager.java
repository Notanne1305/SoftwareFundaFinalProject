package org.example.softfun_funsoft.utils;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.softfun_funsoft.lang.LangCheck;

import java.util.Objects;

public class SoundManager {

    private static MediaPlayer soundPlayer;

    public static void playSound(String soundPath) {
        try {
            Media sound = new Media(Objects.requireNonNull(SoundManager.class.getResource(soundPath)).toString());
            if (soundPlayer != null) {
                soundPlayer.stop();
            }
            soundPlayer = new MediaPlayer(sound);
            soundPlayer.play();
        } catch (NullPointerException e) {
            showError("Sound file not found at: " + soundPath);
        }
    }

//    public static void playSelectPlaceSound() {
//        String soundPath = LangCheck.isEnglish() ? "/sounds/place_Eng.mp3" : "/sounds/place_Tag.mp3";
//        playSound(soundPath);
//    } Pag ma naa na Language

    public static void playSelectPlaceSound() {
        String soundPath = "/sounds/place_Eng.mp3";
        playSound(soundPath);
    }


    public static void playProceedMenuSound() {
        String soundPath = "/sounds/menu_Eng.mp3";
        playSound(soundPath);
    }

    public static void playPaymentType(){
        String soundPath = "/sounds/payEng.mp3";
        playSound(soundPath);
    }

    public static void playClick(){
        String soundPath = "/sounds/click.mp3";
        playSound(soundPath);
    }

    public static void playRemove(){
        String soundPath = "/sounds/remove.mp3";
        playSound(soundPath);
    }

    public static void playAddandSubt(){
        String soundPath = "/sounds/click_.mp3";
        playSound(soundPath);
    }

    public static void playStartSound(){
        String soundPath = "/sounds/start_Eng.mp3";
        playSound(soundPath);
    }


    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

