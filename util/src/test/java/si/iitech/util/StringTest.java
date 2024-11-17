package si.iitech.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class StringTest {

	@Test
	public void testDuplicateCharacters() {
		System.out.println(Arrays.toString("irenakrumpak".chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
				.entrySet().stream().filter(entry -> entry.getValue() > 1).map(Map.Entry::getKey)
				.toArray(Character[]::new)));
		

	}
	
	@Test
	public void testFunctionalMethod() {
		
		//naredi kovanec
		executeInTransaction(() -> System.out.print(""));
		
		
		
		
		executeInTransaction(new TestInterface() {
			@Override
			public void executeInBlockCode() {
				System.out.print("");
			}
		});
		
		List<String> names = Arrays.asList("bob", "josh", "megan");
		names.replaceAll(each -> each.toUpperCase());
	}
	
	//Transaction
	private void executeInTransaction(TestInterface t) {
		//odpret transakcijo
		t.executeInBlockCode();
		//zapret transakcijo
	}

}
