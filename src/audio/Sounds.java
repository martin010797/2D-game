package audio;

import java.util.ArrayList;
import java.util.HashMap;

public class Sounds {

    public static final int SIZE_OF_ENEMY_SOUND_BUFFER = 10;

    public static HashMap<String, ArrayList<AudioPlayer>> sounds;
    public static AudioPlayer pickUpBoost, menu_button_hovering, menu_button_click;

    public static void init(){
        sounds = new HashMap<String, ArrayList<AudioPlayer>>();

        sounds.put("enemy_death", new ArrayList<AudioPlayer>());
        for (int i = 0; i < SIZE_OF_ENEMY_SOUND_BUFFER; i++){
            sounds.get("enemy_death").add(new AudioPlayer("/sounds/enemy_death.mp3"));
        }

        pickUpBoost = new AudioPlayer("/sounds/pick_up_boost.mp3");
        menu_button_hovering = new AudioPlayer("/sounds/menu_button_hover.mp3");
        menu_button_click = new AudioPlayer("/sounds/menu_button_click.mp3");
        menu_button_click.higherVolume();
    }

    public static AudioPlayer getSound(String name){
        ArrayList<AudioPlayer> al = sounds.get(name);
        for (int i = 0; i < al.size(); i++){
            AudioPlayer ap = al.get(i);
            if (!ap.isPlaying()){
                return ap;
            }
        }
        return al.get(al.size()-1);
    }
}
