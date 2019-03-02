package com.github.jzajac.lumbergh;

import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests multiple boolean expressions, i.e. (age >= 18 AND age <= 21) OR (firstName = frank)
 */
public class MultiBooleanExpressionTest {

	private RuleEvaluator<Boolean> ruleEvaluator = new RuleEvaluator<>();

	@Test
	public void multiExpressionSingleTrueCondition() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("firstName", "frank");
		context.put("age", 45);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 18 AND age <= 21) OR (firstName = frank)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void multiExpressionMultiTrueCondition() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("firstName", "frank");
		context.put("age", 20);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 18 AND age <= 21) OR (firstName = frank)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void multiExpressionNoTrueCondition() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("firstName", "david");
		context.put("age", 45);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 18 AND age <= 21) OR (firstName = frank)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

}
