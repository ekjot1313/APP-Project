package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.daotest.DaoTestSuite;
import test.gameenginetest.GameEngineTestSuite;
import test.gametest.GameTestSuite;
import test.mapworkstest.MapWorksTestSuite;
import test.pattern.adapter.AdapterTestSuite;
import test.pattern.builder.BuilderTestSuite;
import test.pattern.observer.ViewTestSuite;
import test.pattern.strategy.StrategyTestSuite;

@RunWith(Suite.class)
@SuiteClasses({DaoTestSuite.class, MapWorksTestSuite.class, GameTestSuite.class, ViewTestSuite.class ,StrategyTestSuite.class , GameEngineTestSuite.class, BuilderTestSuite.class, AdapterTestSuite.class})
public class AllTests {
}