import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    /** test wordToDeque method */
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    /**tests the isPalindrome method */
    @Test
    public void testPalindrome(){
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("aabb"));

        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("A"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("abcba"));
        assertTrue(palindrome.isPalindrome("aa"));

        OffByOne offByOne = new OffByOne();
        OffByN offBy5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("aba", offBy5));
    }

    /** test recursive method */
//    @Test
//    public void testPalindromeRecursive() {
//        assertTrue(palindrome.isPalindromeRecursive("aba"));
//        assertTrue(palindrome.isPalindromeRecursive("aa"));
//        assertTrue(palindrome.isPalindromeRecursive(""));
//
//        assertFalse(palindrome.isPalindromeRecursive("aabb"));
//        assertFalse(palindrome.isPalindromeRecursive("cat"));
//    }
}
