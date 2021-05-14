public class Palindrome {
    /**Given a String, return a Deque where the characters appear in the same order as in the String. */
    public Deque<Character> wordToDeque(String word){
        LinkedListDeque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++){
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    /** Judge a given word is a palindrome or not. */
    public boolean isPalindrome(String word){
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1){
            Character first = deque.removeFirst();
            Character last = deque.removeLast();
            if (first != last) { return false; }
        }
        return true;
    }

    /** Judge a given word is a palindrome or not using recursive method. */
//    public boolean isPalindromeRecursive(String word){
//        Deque<Character> deque = wordToDeque(word);
//        if (deque.size() < 2) { return true; };
//        if (deque.removeFirst() != deque.removeLast()) { return false; }
//        return isPalindromeRecursive(word.substring(1, word.length() - 1));
//    }

    /** Judge a given word is a palindrome or not. */
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1){
            if (!cc.equalChars(deque.removeFirst(), deque.removeLast())) { return false; }
        }
        return true;
    }
}
