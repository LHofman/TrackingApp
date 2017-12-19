package domain.item;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for all test classes in {@link domain.item}
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ItemTest.class, ItemTestInvalid.class
})
public class Domain_ItemTestSuite {}
