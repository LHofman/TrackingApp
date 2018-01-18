package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for all test classes
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    domain.DomainTestSuite.class, domain.item.Domain_ItemTestSuite.class, exceptions.ExceptionsTestSuite.class, 
    domain.entity.Domain_EntityTestSuite.class
})
public class TestSuite {}
