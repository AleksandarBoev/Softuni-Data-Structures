import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import structure_contracts.MyList;
import structure_implementations.ArrayList;

public class ArrayListTest {
    private static final Integer FIRST_ADD = 5;
    private static final Integer SECOND_ADD = 10;
    private static final Integer THIRD_ADD = 10;

    private MyList<Integer> myList;

    @Before
    public void init() throws NoSuchFieldException, IllegalAccessException {
        myList = new ArrayList<>();
    }

    @Test
    public void getCount_whenNoElements_expectZero() {
        Assert.assertEquals(0, myList.getCount());
    }

    @Test
    public void getCount_whenOneElement_expectOne() {
        myList.add(FIRST_ADD);
        Assert.assertEquals(1, myList.getCount());
    }

    @Test
    public void addElement() {
        myList.add(FIRST_ADD);
        Assert.assertEquals(FIRST_ADD, myList.get(0));
    }

    @Test
    public void addElement_whenAddingTwoElements_expectSecondElementToBeLast() {
        myList.add(FIRST_ADD);
        myList.add(SECOND_ADD);

        Assert.assertEquals(SECOND_ADD, myList.get(1));
    }

    @Test
    public void addElement_whenAddingMoreElementsThanCapacity_expectNoErrors() {
        for (int i = 0; i <= ArrayList.INITIAL_CAPACITY + 1; i++) {
            myList.add(0);
        }
    }

    @Test
    public void getCount_whenAddingOneHundredElements_expectValidCount() {
        for (int i = 1; i <= 100; i++) {
            myList.add(0);
            Assert.assertEquals(i, myList.getCount());
        }
    }

    @Test
    public void getElement_whenAddingPneHundredElements_expectValidElements() {
        for (Integer i = 0; i < 100; i++) {
            myList.add(i);
            Assert.assertEquals(i, myList.get(i));
        }
    }

    @Test
    public void removeAt_whenRemovingLastElement_expectElementToBeGone() {
        myList.add(FIRST_ADD);
        myList.add(SECOND_ADD);

        myList.removeAt(1);
        Assert.assertEquals(FIRST_ADD, myList.get(0));
        try {
            myList.get(1);
            Assert.fail("Expected an 'IndexOutOfBoundsException' error to be thrown!");
        } catch (IndexOutOfBoundsException iobe) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeAt_whenRemovingFirstElement_expectElementsRearrange() {
        myList.add(FIRST_ADD);
        myList.add(SECOND_ADD);
        myList.add(THIRD_ADD);

        myList.removeAt(0);
        Assert.assertEquals(SECOND_ADD, myList.get(0));
        Assert.assertEquals(THIRD_ADD, myList.get(1));
    }

    @Test
    public void removeAt_whenRemovingMiddleElement_expectElementsRearrange() {
        myList.add(FIRST_ADD);
        myList.add(SECOND_ADD);
        myList.add(THIRD_ADD);

        myList.removeAt(1);
        Assert.assertEquals(FIRST_ADD, myList.get(0));
        Assert.assertEquals(THIRD_ADD, myList.get(1));
    }

    @Test
    public void removeAt_whenRemovingElements_expectAppropriateCountChange() {
        myList.add(FIRST_ADD);
        myList.add(SECOND_ADD);

        myList.removeAt(0);
        Assert.assertEquals(1, myList.getCount());

        myList.removeAt(0);
        Assert.assertEquals(0, myList.getCount());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenPassingBiggerThanCapacityIndex_throwIndexOutOfBoundsException() {
        myList.get(myList.getCount() + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_whenPassingNegativeIndex_throwIndexOutOfBoundsException() {
        myList.get(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAt_whenPassingBiggerThanCapacityIndex_throwIndexOutOfBoundsException() {
        myList.removeAt(myList.getCount() + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeAt_whenPassingNegativeIndex_throwIndexOutOfBoundsException() {
        myList.get(-1);
    }
}
