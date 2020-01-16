package text_editor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class TextEditorTests {
    @Before
    public void init() {

    }

    @Test
    public void test01() {
        String[] inputTokens = getInputAsArray("login pesho\n" +
                "pesho prepend \"hello\"\n" +
                "pesho print\n" +
                "pesho length\n" +
                "end\n");

        StringBuilder result = new StringBuilder();

        int counter = 0;
        while (counter < inputTokens.length && !inputTokens[counter].equals("end")) {
            String[] wat = getInputAsArray(inputTokens[counter]);



            counter++;
        }
    }

    private String[] getInputAsArray(String input) {
        return input.split("\n");
    }
}
