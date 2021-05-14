public class ArrayDeque<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    private static int INIT_SIZE = 8;      // initial size
    private static double ENLARGE = 2.0;   // enlarge rate
    private static double SHRINK = 0.5;    // shrink rate


    /**nextFirst and nextLast will first point to middle and middle + 1 of items */
    public ArrayDeque(){
        items = (T[]) new Object[INIT_SIZE];   // why can't "items = new T[INIT_SIZE]" ?
        this.size = 0;
        this.nextFirst = INIT_SIZE / 2;
        this.nextLast = INIT_SIZE / 2 + 1;
    }

    /**Returns the number of items in the deque. */
    public int size(){
        return size;
    }

    /**Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /**help function to enlarge or shrink the list 
     * in nextFirst < nextLast situation, start index in new list is the middle - 1
     * in nextFirst > nextLast situation, left part remains left and right part remains right
    */
    private void resize(boolean enlarge){
        double RATE;
        if (enlarge) RATE = ENLARGE;
        else RATE = SHRINK;

        T[] newList = (T[]) new Object[(int) (items.length * RATE)];
        // nextFirst is left to nextLast
        if (nextFirst < nextLast){
            System.arraycopy(items, nextFirst + 1, newList, newList.length / 2 - 1, nextLast - nextFirst -1);
            nextFirst = newList.length / 2 - 1 - 1;
            nextLast = nextFirst + size + 1;
        }
        // nextFirst is right to nextLast
        else{
            System.arraycopy(items, 0, newList, 0, nextLast);
            System.arraycopy(items, nextFirst + 1, newList, newList.length - items.length + nextFirst + 1, items.length - nextFirst - 1);
            nextFirst = newList.length - items.length + nextFirst;
        }
        items = newList;
    }

    /**add element to the front of the deque */
    public void addFirst(T item){
        // For leave at least two position for nextFirft and nextLast，enlarge operation will be implemented when size == items.length - 2
        if (size == items.length - 2) resize(true);

        items[nextFirst--] = item;
        size++;
        if (nextFirst < 0) nextFirst = items.length - 1; // reach the end of left side, so go to right
    }

    /**add element to the back of the deque */
    public void addLast(T item){
        // For leave at least two position for nextFirft and nextLast，enlarge operation will be implemented when size == items.length - 2
        if (size == items.length - 2) resize(true);

        items[nextLast++] = item;
        size++;
        if (nextLast > items.length - 1) nextLast = 0; // reach the end of right side, so go to left
    }

    /**Removes and returns the item at the front of the deque. If no such item exists, returns null */
    public T removeFirst(){
        if (size == 0) return null;
        // when nextFirst at the most right position of items list
        if (nextFirst + 1 > items.length - 1){
            T out = items[0];
            items[0] = null;
            nextFirst = 0;
            size--;
            if ((items.length >= 16) && ((double) size / items.length < 0.25)) resize(false);
            return out;
        }
        // normal condition
        T out = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        nextFirst++;
        size--;
        if ((items.length >= 16) && ((double) size / items.length < 0.25)) resize(false);
        return out;
    }

    /**Removes and returns the item at the back of the deque. If no such item exists, returns null */
    public T removeLast() {
        if (size == 0) return null;
        // when nextLast at the most left position of items list
        if (nextLast - 1 < 0) {
            T out = items[items.length - 1];
            size--;
            items[items.length - 1] = null;
            nextLast = items.length - 1;
            if ((items.length >= 16) && ((double) size / items.length < 0.25)) resize(false);
            return out;
        }
        // normal condition
        T out = items[nextLast - 1];
        size--;
        items[nextLast - 1] = null;
        nextLast--;
        if ((items.length >= 16) && ((double) size / items.length < 0.25)) resize(false);
        return out;
    }

    /**Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. */
    public T get(int index){
        if (size == 0 || index < 0 || index > size - 1) return null;

        if (nextFirst + 1 + index > items.length - 1){
            return items[nextFirst + 1 + index - items.length];
        }
        return items[nextFirst + 1 + index];
    }

    /**Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        int i = 0;
        while (this.get(i) != null) {
            System.out.print(this.get(i++));
            System.out.print(" ");
        }
        System.out.println("\n");
    }        
}
