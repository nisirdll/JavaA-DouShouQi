package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Test{
    public static class AudioPlay2 extends Thread {
        public boolean run = true;
        String path = "";
        private AudioInputStream audioStream;
        private AudioFormat audioFormat;
        private SourceDataLine sourceDataLine;

        public AudioPlay2(String path) {
            this.path = path;
        }

        private void playMusic(String path) {
            try {
                int count;
                byte buf[] = new byte[1024];
                audioStream = AudioSystem.getAudioInputStream(new File(path));
                audioFormat = audioStream.getFormat();
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                while((count = audioStream.read(buf,0,buf.length)) != -1){ if(run){ sourceDataLine.write(buf,0,count);
                }else {
                    break;
                }
                }
                sourceDataLine.drain();
                sourceDataLine.close();
                audioStream.close();
            }
            catch(UnsupportedAudioFileException ex){ ex.printStackTrace();
            }
            catch(LineUnavailableException ex){ ex.printStackTrace();
            }
            catch(IOException ex){ ex.printStackTrace();
            }
            }
        public void run(){ playMusic(path); }
        }

    }
