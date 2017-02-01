package nl.hu.tosad.businessruleservice;
import static org.junit.Assert.*;
import org.junit.Test;

public class BusinessRuleServiceTest {
	
	BusinessRuleServiceTest ruleservlet = new BusinessRuleServiceTest();
	BusinessRuleService instance1 = new BusinessRuleService();
	BusinessRuleService instance2 = null;
	@Test
	public void getInstanceTest(){
		System.out.println("Inside getInstance()");
		assertNotSame(instance1, BusinessRuleService.getInstance());
	}
	@Test
	public void generateTest(){
		System.out.println("Inside generate()");
	}
	@Test
	public void checkTargetTest(){
		System.out.println("Inside checkTarget()");
	}
	@Test
	public void deleteTest(){
		System.out.println("Inside delete()");
	}
	@Test
	public void getColumnsTest(){
		System.out.println("Inside getColumns()");
	}
	@Test
	public void getControllerTest(){
		System.out.println("Inside getController()");
	}
	@Test
	public void getTablesTest(){
		System.out.println("Inside getTables()");
	}



	}
