package test.pattern.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AggressiveStrategyTest.class, BenevolentStrategyTest.class, CheaterStrategyTest.class,
		HumanStrategyTest.class , RandomStrategyTest.class })
public class StrategyTestSuite {
}

