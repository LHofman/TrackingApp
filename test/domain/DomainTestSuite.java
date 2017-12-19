package domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for all test classes in {@link domain}
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    domain.MyDateTest.class, domain.IEntityTest.class, domain.DomainHelperTest.class,
    PersonTest.class, PersonTestInvalid.class
})
public class DomainTestSuite {}
