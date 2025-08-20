public class Note implements FYENote{
    private byte note;
    private int timing;
    public Note(byte note, int timing){
        this.note = note;
        this.timing = timing;
    }
    @Override
    public byte getNote() {
        return note;
    }

    @Override
    public int getTiming() {
        return timing;
    }
    @Override
    public  String toString(){
        return "Note " + getNote() + " Timing: " + getTiming();
    }
}
