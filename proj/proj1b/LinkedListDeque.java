public class LinkedListDeque<T> implements Deque<T>{
    /**Node to implement linked list based Deque */
    private class DeNode{
        public T item;
        public DeNode prev;
        public DeNode next;

        public DeNode(DeNode prev, T item, DeNode next){
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private int size;
    private DeNode sentFront;
    private DeNode sentBack;

    public LinkedListDeque(){
        this.size = 0;
        this.sentFront = new DeNode(null, null, null);
        this.sentBack = new DeNode(null, null, null);
        this.sentFront.next = sentBack;
        this.sentBack.prev = sentFront;
    }

    /**Returns true if deque is empty, false otherwise. */
    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    /**Returns the number of items in the deque. */
    @Override
    public int size(){
        return size;
    }

    /**Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item){
        size += 1;
        DeNode curNext = sentFront.next;
        DeNode newNode = new DeNode(sentFront, item, curNext);
        sentFront.next = newNode;
        curNext.prev = newNode;
    }

    /**Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item){
        size += 1;
        DeNode curPrev = sentBack.prev;
        DeNode newNode = new DeNode(curPrev, item, sentBack);
        sentBack.prev = newNode;
        curPrev.next = newNode;
    }

    /**Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst(){
        if (size == 0) return null;
        size --;
        DeNode curNext = sentFront.next;
        sentFront.next = curNext.next;
        curNext.next.prev = sentFront;
        return curNext.item;
    }
    
    /**Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast(){
        if (size == 0) return null;
        size--;
        DeNode curPrev = sentBack.prev;
        sentBack.prev = curPrev.prev;
        curPrev.prev.next = sentBack;
        return curPrev.item;
    }

    /**Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null.  */
    @Override
    public T get(int index){
        if (size == 0 || index < 0 || index > size - 1) return null;
        int cnt = 0;
        DeNode ptr = sentFront.next;
        while (cnt < index){
            ptr = ptr.next;
            cnt++;
        }
        return ptr.item;
    }

    /**help function to get next DeNode */
    private DeNode getNode(DeNode curNode, int index){
        if (index == 0) return curNode.next;
        curNode = getNode(curNode.next, index - 1);
        return curNode;
    }

    /**using recursive method to get item */
    public T getRecursive(int index){
        if (size == 0 || index < 0 || index > size - 1) return null;
        return getNode(sentFront, index).item;
    }

    /**Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque(){
        DeNode ptr = sentFront.next;
        while (ptr != sentBack){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.println('\n');
    }

}
