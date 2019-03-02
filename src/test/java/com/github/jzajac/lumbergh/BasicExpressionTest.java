package com.github.jzajac.lumbergh;

import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicExpressionTest {

	private RuleEvaluator<Boolean> ruleEvaluator = new RuleEvaluator<>();

	@Test
	public void testSpacesAreIgnored() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 20);
		this.ruleEvaluator.withContext(context);

		String expr = "(age         >               15)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

}
