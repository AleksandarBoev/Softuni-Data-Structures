package exam_prep_1.p01_limited_memory.tests.correctness;


import exam_prep_1.p01_limited_memory.main.LimitedMemoryCollection;
import exam_prep_1.p01_limited_memory.tests.helpers.BaseTest;
import exam_prep_1.p01_limited_memory.tests.types.CorrectnessTests;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class Get extends BaseTest {
    @Test
    @Category(CorrectnessTests.class)
    public void Get_ExistingKey_ShouldReturnCorrectValue()
    {
        LimitedMemoryCollection<String, Integer> collection = new LimitedMemoryCollection<>(4);

        collection.set("A", 1);
        collection.set("B", 2);

        int value = collection.get("B");

        Assert.assertEquals(2, value);
    }

    @Test(expected = IllegalArgumentException.class)
    @Category(CorrectnessTests.class)
    public void Get_MissingKey_ShouldThrow()
    {
        LimitedMemoryCollection<String, Integer> collection = new LimitedMemoryCollection<>(4);

        collection.set("A", 1);
        collection.get("B");
    }

    @Test
    @Category(CorrectnessTests.class)
    public void Get_AfterTwoTimesSet_OnSameKey_ShouldReturnSecondValue()
    {
        LimitedMemoryCollection<String, Integer> collection = new LimitedMemoryCollection<>(4);

        collection.set("A", 1);
        int firstValue = collection.get("A");
        Assert.assertEquals(1, firstValue);

        collection.set("A", 2);
        int secondValue = collection.get("A");
        Assert.assertEquals(2, secondValue);
    }
}
