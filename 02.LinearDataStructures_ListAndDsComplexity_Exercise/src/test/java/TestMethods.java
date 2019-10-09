import org.junit.Assert;
import t03_longest_subsequence.LongestSubsequence;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestMethods {
    public static List<Integer> stringToIntegerList(String string) {
        return Arrays.stream(string.split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static String integerListToString(List<Integer> numbers) {
        return numbers.stream().map(Object::toString).collect(Collectors.joining(" "));
    }
}
