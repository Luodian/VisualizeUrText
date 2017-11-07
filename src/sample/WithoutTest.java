package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Without Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 7, 2017</pre>
 */
public class WithoutTest {
	Without without;
	
	@Before
	public void before () throws Exception {
		without = new Without ();
	}
	
	@After
	public void after () throws Exception {
	}
	
	/**
	 * Method: calcShortestPath(String fileName, String word1, String word2)
	 */
	@Test
	public void caseA () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/a.txt", "to", "civilizations");
		System.out.println (str);
		assertTrue (str.equals ("to -> explore -> strange -> new -> civilizations : 4\n" +
				"to -> seek -> out -> new -> civilizations : 4\n"));
	}
	
	@Test
	public void caseB () throws Exception {
		String str = without.calcShortestPath ("src/sample/Test/b.txt", "", "");
		System.out.println (str);
		assertTrue (str.equals ("空图"));
	}
	
	@Test
	public void caseC () throws Exception {
		String str = without.calcShortestPath ("src/sample/Test/c.txt", "", "");
		System.out.println (str);
		assertTrue (str.equals ("起点和终点均为空"));
	}
	
	@Test
	public void caseD () throws Exception {
		String str = without.calcShortestPath ("src/sample/Test/d.txt", "to", "");
		System.out.println (str);
		assertTrue (str.equals ("to -> explore : 1\n" +
				"to -> explore -> strange : 2\n" +
				"to -> explore -> strange -> new : 3\n" +
				"to -> seek -> out -> new : 3\n" +
				"to -> explore -> strange -> new -> worlds : 4\n" +
				"to -> seek -> out -> new -> worlds : 4\n" +
				"to -> seek : 1\n" +
				"to -> seek -> out : 2\n" +
				"to -> explore -> strange -> new -> life : 4\n" +
				"to -> seek -> out -> new -> life : 4\n" +
				"to -> explore -> strange -> new -> life -> and : 5\n" +
				"to -> seek -> out -> new -> life -> and : 5\n" +
				"to -> explore -> strange -> new -> civilizations : 4\n" +
				"to -> seek -> out -> new -> civilizations : 4\n"));
	}
	
	@Test
	public void caseE () throws Exception {
		String str = without.calcShortestPath ("src/sample/Test/e.txt", "", "civilizations");
		System.out.println (str);
		assertTrue (str.equals ("起点不能为空"));
	}
	
	@Test
	public void caseF () throws Exception {
		String str = without.calcShortestPath ("src/sample/Test/f.txt", "explore", "life");
		System.out.println (str);
		assertTrue (str.equals ("explore -> strange -> new -> life : 3\n"));
	}
	
	@Test
	public void caseG () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/g.txt", "to", "civilizations");
		System.out.println (str);
		assertTrue (str.equals ("to -> explore -> strange -> new -> civilizations : 4\n" +
				"to -> seek -> out -> new -> civilizations : 4\n"));
	}
	
	@Test
	public void caseH () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/h.txt", "to", "to");
		System.out.println (str);
		assertTrue (str.equals ("to : 0\n"));
	}
	
	@Test
	public void caseI () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/i.txt", "new", "new");
		System.out.println (str);
		assertTrue (str.equals ("new : 0\n"));
	}
	
	@Test
	public void caseJ () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/j.txt", "to", "brave");
		System.out.println (str);
		assertTrue (str.equals ("to -> explore -> strange -> new -> civilizations -> here -> is -> your -> brave : 8\n" +
				"to -> seek -> out -> new -> civilizations -> here -> is -> your -> brave : 8\n"));
	}
	
	@Test(timeout = 5000)
	public void caseK () throws Exception {
		//TODO: Test goes here...
		String str = without.calcShortestPath ("src/sample/Test/k.txt", "wish", "her");
		System.out.println (str);
		assertTrue (str.equals ("wish -> you -> tell -> her : 3\n" +
				"wish -> you -> liked -> her : 3\n" +
				"wish -> you -> love -> her : 3\n" +
				"wish -> you -> asked -> her : 3\n"));
	}
	
} 
