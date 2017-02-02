package nl.hu.tosad;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import nl.hu.tosad.businessruleservice.BusinessRuleServiceTest;
import nl.hu.tosad.restservlets.AppContextListernerTest;
import nl.hu.tosad.restservlets.RuleGeneratorResourceTest;
@RunWith(Suite.class)

@Suite.SuiteClasses({
   BusinessRuleServiceTest.class,
   AppContextListernerTest.class,
   RuleGeneratorResourceTest.class,
})

public class TestSuite {   
} 