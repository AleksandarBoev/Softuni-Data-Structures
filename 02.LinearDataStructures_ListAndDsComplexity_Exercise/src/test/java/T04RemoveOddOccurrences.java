import org.junit.Assert;
import org.junit.Test;
import t04_remove_odd_occurences.RemoveOddOccurrences;

import java.util.List;

public class T04RemoveOddOccurrences {
    @Test
    public void zeroTest01() {
        test("1 2 3 4 1", "1 1");
    }

    @Test
    public void zeroTest02() {
        test("1 2 3 4 5 3 6 7 6 7 6", "3 3 7 7");
    }

    @Test
    public void zeroTest03() {
        test("1 2 1 2 1 2", "");
    }

    @Test
    public void zeroTest04() {
        test("3 7 3 3 4 3 4 3 7", "7 4 4 7");
    }

    @Test
    public void zeroTest05() {
        test("1 1", "1 1");
    }

    void test(String input, String expectedOutput) {
        List<Integer> inputNumbersList = TestMethods.stringToIntegerList(input);
        RemoveOddOccurrences.removeOddOccurrences(inputNumbersList);
        String actualOutput = TestMethods.integerListToString(inputNumbersList);
        Assert.assertEquals(expectedOutput, actualOutput);
    }
}
