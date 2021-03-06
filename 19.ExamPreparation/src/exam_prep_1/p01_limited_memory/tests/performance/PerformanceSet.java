package exam_prep_1.p01_limited_memory.tests.performance;

import exam_prep_1.p01_limited_memory.main.LimitedMemoryCollection;
import exam_prep_1.p01_limited_memory.tests.helpers.BaseTest;
import exam_prep_1.p01_limited_memory.tests.types.PerformanceTests;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class PerformanceSet extends BaseTest {
    @Category(PerformanceTests.class)
    @Test
    public void PerformanceSet_With100000CallsOnExistingElements()
    {
        LimitedMemoryCollection<String,Integer> collection = new LimitedMemoryCollection<>(100000);

        for (int i = 1; i <= 100000; i++) {
            collection.set(i+"",i);
        }

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 100000; i++) {
            collection.set(i+"", 100000 - i);
        }

        long end = System.currentTimeMillis();
        Assert.assertTrue(end - start < 200);

        for (int i = 1; i < 100000; i++) {
            Assert.assertEquals("Expected Value did not match!",(Integer)(100000 - i),collection.get(i+""));
        }
    }

    @Category(PerformanceTests.class)
    @Test
    public void PerformanceSet_Called100000Times()
    {
        LimitedMemoryCollection<String,Integer> collection = new LimitedMemoryCollection<>(100000);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 100000; i++) {
            collection.set(i + "", i);
        }

        long end = System.currentTimeMillis();
        Assert.assertTrue(end - start < 200);

        for (int i = 1; i <= 100000; i++) {
            collection.set(i + "", 100000 - i);
        }

        for (int i = 1; i < 100000; i++) {
            Assert.assertEquals("Expected Value did not match!", (Integer) (100000 - i), collection.get(i + ""));
        }
    }

    @Category(PerformanceTests.class)
    @Test
    public void PerformanceSet_With200000ElementsWith100000Capacity()
    {
        LimitedMemoryCollection<String,Integer> collection = new LimitedMemoryCollection<>(100000);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 200000; i++) {
            collection.set(i + "", i);
        }

        long end = System.currentTimeMillis();
        Assert.assertTrue(end - start < 500);
    }
}
