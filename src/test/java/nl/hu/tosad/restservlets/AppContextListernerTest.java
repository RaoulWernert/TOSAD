package nl.hu.tosad.restservlets;
import org.junit.Test;
public class AppContextListernerTest {
	@Test
	public void contextDestroyedTEst(){
		System.out.println("Inside AppCOntextListerner.contextDestroyed()");

	}
	@Test
	public void contextInitializedTest(){
		System.out.println("Inside AppCOntextListerner.contextInitializedTest()");
	}

}
