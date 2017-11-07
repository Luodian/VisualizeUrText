package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.GraphInterface.DirectedGraph;
import sample.GraphInterface.DirectedGraphInterface;

public class MainTest {
	private DirectedGraphInterface<String> dGraph;
	
	@Before
	public void setUp () throws Exception {
		dGraph = new DirectedGraph<> ();
	}
	
	@After
	public void tearDown () throws Exception {
	}
	
	@Test
	public void main () throws Exception {
	
	}
	
}