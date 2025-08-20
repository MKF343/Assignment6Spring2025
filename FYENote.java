import java.util.ArrayList;
import javax.sound.midi.*;

public interface FYENote {
    byte getNote();
    int getTiming();
}

class FYEMusicException extends Exception{
    public FYEMusicException(String message){
        super(message);
    }
}

class FYEMusicPlayerException extends FYEMusicException{
    public FYEMusicPlayerException(String message){
        super(message);
    }
}

class FYEMusicPlayer{
    private static ArrayList<FYENote> notes;

    public static void loadNotes(ArrayList<FYENote> notes) throws FYEMusicPlayerException{
        for(FYENote n : notes){
            if (n.getNote() < 0             ||
                n.getTiming() < 0           ||
                n.getTiming() % 62500 != 0  ||
                n.getTiming() > 7_937_500) {
                throw new FYEMusicPlayerException("File not parsed correctly. Check the specifications and correct your code");
            }
        }

        FYEMusicPlayer.notes = notes;
    }

    public static void play() throws FYEMusicPlayerException{
        if(notes == null) throw new FYEMusicPlayerException("No melody loaded");
        MusicPlayer.play(notes);
    }

    public static void close(){
        MusicPlayer.close();
    }
}




















/*
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
    ==============================DO NOT READ PAST THIS POINT==============================
 */

class MusicPlayer{
    private static Synthesizer synth;
    private static Sequencer seq;

    private static void initialize() throws MidiUnavailableException{
        synth = MidiSystem.getSynthesizer();
        seq = MidiSystem.getSequencer();
        synth.open();
        seq.open();
    }

    public static void close(){
        if (synth != null) synth.close();
        if (seq != null) seq.close();
    }

    static void play(ArrayList<FYENote> notes) throws FYEMusicPlayerException{

        try{
            if(synth == null || seq == null) initialize();

            long latency = synth.getLatency();
            Sequence sequence = new Sequence(Sequence.SMPTE_25, 40000);
            Track t = sequence.createTrack();

            long timer = latency;

            for (FYENote note : notes) {
                if(note.getNote() != 0)
                    t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, note.getNote(), 100), timer));
                else
                    t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 0, 100), timer));

                timer += note.getTiming();
                t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, note.getNote(), 100), timer));
            }
            t.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 0, 100), timer + latency));

            seq.setSequence(sequence);

            seq.start();

            while (true) {
                if (!seq.isRunning()) break;
            }
        } catch (MidiUnavailableException | InvalidMidiDataException | SecurityException e) {
            String message = "Java is unable to make your computer play sounds. Code your solution according to specifications, as your submission will still be graded as normal.";
            throw new FYEMusicPlayerException(message);
        }
    }
}