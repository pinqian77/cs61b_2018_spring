package synthesizer;
import java.util.Iterator;

import javax.management.RuntimeErrorException;

//TODO: Make sure to make this class and all of its methods public
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first; // stores the index of the least recently inserted item
    /* Index for the next enqueue. */
    private int last; // stores the index one beyond the most recently inserted item
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb =  (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (isFull()){
            throw new RuntimeException("Ring buffer overflow");
        }
        fillCount += 1;
        rb[last] = x;
        last -= 1;
        if (last < 0){
            last = capacity() - 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update first
        if (isEmpty()){
            throw new RuntimeException("Ring buffer underflow");
        }
        fillCount -= 1;
        T out = rb[first];
        rb[first] = null;
        first -= 1;
        if (first < 0){
            first = capacity() - 1;
        }
        return out;

    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (isEmpty()){
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    @Override
    public Iterator<T> iterator() {
        return new ArrayBufferIterator();
    }

    private class ArrayBufferIterator implements Iterator<T> {
        private int wizPos;
        private int nextCount;

        public ArrayBufferIterator() {
            wizPos = first;
            nextCount = 0;
        }

        public boolean hasNext() {
            return nextCount < fillCount;
        }

        public T next() {
            T returnItem = rb[wizPos];
            wizPos -= 1;
            nextCount += 1;
            if (wizPos < 0) {
                wizPos = rb.length - 1;
            }
            return returnItem;
        }
    }
}
