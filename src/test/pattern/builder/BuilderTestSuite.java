package test.pattern.builder;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.daotest.DaoTestSuite;
import test.gameenginetest.GameEngineTestSuite;
import test.gametest.GameTestSuite;
import test.mapworkstest.MapWorksTestSuite;
import test.pattern.observer.ViewTestSuite;
import test.pattern.strategy.StrategyTestSuite;

@RunWith(Suite.class)
@SuiteClasses({LoadGameBuilderTest.class, SaveGameBuilderTest.class})
public class BuilderTestSuite {

}
