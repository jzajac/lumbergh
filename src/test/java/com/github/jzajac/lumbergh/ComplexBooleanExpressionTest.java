package com.github.jzajac.lumbergh;

import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests complex boolean expressions, i.e. (age >= 18 AND age <= 21)
 */
public class ComplexBooleanExpressionTest {

	private RuleEvaluator<Boolean> ruleEvaluator = new RuleEvaluator<>();

	@Test
	public void multiConditionTrueBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 20);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 18 AND age <= 21)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void multiConditionFalseBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 22);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 18 AND age <= 21)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

}
