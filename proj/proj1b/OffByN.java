public class OffByN implements CharacterComparator{
    private int delta;

    public OffByN(int N){
        this.delta = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (diff == delta || diff == -delta) {
            return true;
        }
        return false;
    }
}
