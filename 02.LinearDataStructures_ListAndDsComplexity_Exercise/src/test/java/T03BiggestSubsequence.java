import org.junit.Assert;
import org.junit.Test;
import t03_longest_subsequence.LongestSubsequence;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class T03BiggestSubsequence {
    @Test
    public void zeroTest01() {
        test("12 2 7 4 3 3 8", "3 3");
    }

    @Test
    public void zeroTest02() {
        test("2 2 2 3 3 3", "2 2 2");
    }

    @Test
    public void zeroTest03() {
        test("4 4 5 5 5", "5 5 5");
    }

    @Test
    public void zeroTest04() {
        test("1 2 3", "1");
    }

    @Test
    public void zeroTest05() {
        test("0", "0");
    }

    @Test
    public void zeroTest06() {
        test("4 2 3 4 4", "4 4");
    }

    void test(String input, String expectedOutput) {
        List<Integer> inputNumbersList = TestMethods.stringToIntegerList(input);
        List<Integer> result = LongestSubsequence.longestSubSequence(inputNumbersList);
        String actualOutput = TestMethods.integerListToString(result);
        Assert.assertEquals(expectedOutput, actualOutput);
    }
}
