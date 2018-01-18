package domain.entity;

import domain.item.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * TestSuite for all test classes in {@link domain.item}
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    IEntityTest.class, PersonTest.class, PersonTestInvalid.class, GameObjectiveTest.class, EpisodeTest.class
})
public class Domain_EntityTestSuite {}
