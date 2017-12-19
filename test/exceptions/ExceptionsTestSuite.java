package exceptions;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for all test classes in {@link domain}
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    InvalidArgumentExceptionTest.class
})
public class ExceptionsTestSuite {}
