import java.util.*;
import java.io.*;
public class Assignment6 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        boolean running = true;
        ArrayList<FYENote> loadedNotes = null;
        FileInputStream fis = null;
        while(running) {
            System.out.println("[FYE Music Player]");
            System.out.println("1. Load music");
            System.out.println("2. Play music");
            System.out.println("3. Quit");
            System.out.print("Enter option: ");
            try {
                byte choice = input.nextByte();
                input.nextLine();
                switch (choice) {
                    case 1: //Loading
                        System.out.print("Enter the name of music file: ");
                        String fileName = input.nextLine();
                        try {
                            fis = new FileInputStream(fileName);
                            loadedNotes = loadMusic(fis);
                            System.out.println("Music loaded");
                            FYEMusicPlayer.loadNotes(loadedNotes);
                        } catch (IOException e) {
                            System.out.println("Unable to load file: " + e.getMessage());
                        } catch (FYEMusicPlayerException | FYEMusicReaderException e) {
                            System.out.println("Unable to load file: " + e.getMessage());
                        } finally {
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                        break;
                    case 2: //Playing
                        try {
                            System.out.println("Playing music...");
                            FYEMusicPlayer.play();
                            System.out.println("Done Playing.");
                        } catch (FYEMusicPlayerException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Shutting down...");
                        input.close();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }catch(Exception e){
                System.out.print(e.getMessage());
                input.nextLine();
            }
        }
    }
    public static ArrayList<FYENote> loadMusic(InputStream fis) throws IOException, FYEMusicReaderException{
        ArrayList<FYENote> notes = new ArrayList<>();
        int noteCount = fis.read();
        if(noteCount == -1){
            throw new FYEMusicReaderException("File is empty");
        }
        if(noteCount > 127 || noteCount < 0){
            throw new FYEMusicReaderException("Note counter is out of range");
        }
        for(int i = 0; i < noteCount; i++){
            int note = fis.read();
            if(note == -1){
                throw new FYEMusicReaderException("File ended abruptly");
            }
            if(note < 0 || note > 127){
                throw new FYEMusicReaderException("Note out of range");
            }
            int timing = fis.read();
            if(timing == -1){
                throw new FYEMusicReaderException("File ended abruptly");
            }
            if(timing < 0 || timing > 127){
                throw new FYEMusicReaderException("Timing out of range");
            }
            int timingMicro = timing * 1000000 / 16;
            notes.add(new Note((byte)note, timingMicro));
        }
        return notes;
    }
}