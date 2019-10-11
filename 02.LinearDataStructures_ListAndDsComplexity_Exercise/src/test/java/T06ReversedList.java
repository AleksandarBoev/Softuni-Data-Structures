import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import t06_reversed_list.ReversedList;

public class T06ReversedList {
    private static final Integer FIRST_ELEMENT = 1;
    private static final Integer SECOND_ELEMENT = 2;
    private static final Integer THIRD_ELEMENT = 3;
    private static final Integer FOURTH_ELEMENT = 4;

    ReversedList<Integer> reversedList;

    @Before
    public void init() {
        reversedList = new ReversedList<>();
    }

    @Test
    public void get_whenSingleElement_validResult() {
        reversedList.add(FIRST_ELEMENT);

        Assert.assertEquals(FIRST_ELEMENT, reversedList.get(0));
    }

    @Test
    public void get_whenOddNumberOfElements_validResults() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);

        Assert.assertEquals(THIRD_ELEMENT, reversedList.get(0));
        Assert.assertEquals(SECOND_ELEMENT, reversedList.get(1));
        Assert.assertEquals(FIRST_ELEMENT, reversedList.get(2));
    }

    @Test
    public void get_whenEvenNumberOfElements_validResults() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);
        reversedList.add(FOURTH_ELEMENT);

        Assert.assertEquals(FOURTH_ELEMENT, reversedList.get(0));
        Assert.assertEquals(THIRD_ELEMENT, reversedList.get(1));
        Assert.assertEquals(SECOND_ELEMENT, reversedList.get(2));
        Assert.assertEquals(FIRST_ELEMENT, reversedList.get(3));
    }

    @Test
    public void removeAt_whenSingleElement_validResult() {
        reversedList.add(FIRST_ELEMENT);
        Integer removedElement = reversedList.removeAt(0);

        Assert.assertEquals(FIRST_ELEMENT, removedElement);
        Assert.assertEquals(0, reversedList.getCount());
    }

    @Test
    public void removeAt_whenRemovingFirstElementFromOddNumberOfElements_validResult() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);

        reversedList.removeAt(0);
        Assert.assertEquals(SECOND_ELEMENT, reversedList.get(0));
        Assert.assertEquals(FIRST_ELEMENT, reversedList.get(1));
    }

    @Test
    public void removeAt_whenRemovingFirstElementFromEvenNumberOfElements_validResult() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);
        reversedList.add(FOURTH_ELEMENT);

        reversedList.removeAt(0);
        Assert.assertEquals(THIRD_ELEMENT, reversedList.get(0));
        Assert.assertEquals(SECOND_ELEMENT, reversedList.get(1));
        Assert.assertEquals(FIRST_ELEMENT, reversedList.get(2));
    }

    @Test
    public void removeAt_whenRemovingLastElementFromOddNumberOfElements_validResult() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);

        reversedList.removeAt(reversedList.getCount() - 1);
        Assert.assertEquals(THIRD_ELEMENT, reversedList.get(0));
        Assert.assertEquals(SECOND_ELEMENT, reversedList.get(1));
    }

    @Test
    public void iterateElements() {
        reversedList.add(FIRST_ELEMENT);
        reversedList.add(SECOND_ELEMENT);
        reversedList.add(THIRD_ELEMENT);

        int counter = 0;
        Integer[] integers = new Integer[3];

        for (Integer number: reversedList)
            integers[counter++] = number;

        Assert.assertEquals(THIRD_ELEMENT, integers[0]);
        Assert.assertEquals(SECOND_ELEMENT, integers[1]);
        Assert.assertEquals(FIRST_ELEMENT, integers[2]);
    }

}
