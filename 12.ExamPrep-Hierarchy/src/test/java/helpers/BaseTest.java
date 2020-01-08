package helpers;

import initializer.HierarchyStructureInitializer;
import org.junit.Before;
import tasks.IHierarchy;

public class BaseTest {

    protected static final int DefaultRootValue = 5;
    protected IHierarchy<Integer> Hierarchy;

    @Before
    public void setUp() {
        this.Hierarchy = HierarchyStructureInitializer.create(DefaultRootValue);
    }
}
