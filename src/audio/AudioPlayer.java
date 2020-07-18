package audio;

import ui.ClickListener;

import javax.sound.sampled.*;

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String nameOfAudioFile){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(nameOfAudioFile));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
                    );
            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(decodedAis);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        if(clip == null)
            return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public boolean isPlaying(){
        return clip.isRunning();
    }

    public void lowerVolume(){
        FloatControl gainControl =
              (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10.0f);
    }

    public void stop(){
        if (clip.isRunning()){
            clip.stop();
        }
    }

    public void loop() {
        stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void close(){
        stop();
        clip.close();
    }
}
