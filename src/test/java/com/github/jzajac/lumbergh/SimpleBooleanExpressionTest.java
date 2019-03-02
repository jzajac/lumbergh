package com.github.jzajac.lumbergh;

import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests simple boolean expressions, i.e. (age > 21)
 */
public class SimpleBooleanExpressionTest {

	private RuleEvaluator<Boolean> ruleEvaluator = new RuleEvaluator<>();

	@Test
	public void basicTrueBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("firstName", "john");
		this.ruleEvaluator.withContext(context);

		String expr = "(firstName = john)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void basicFalseBooleanExpression() throws ScriptException {
		Map<String, Object> context = new HashMap<>();
		context.put("firstName", "jane");
		this.ruleEvaluator.withContext(context);

		String expr = "(firstName = john)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

	@Test
	public void gtBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 30);
		this.ruleEvaluator.withContext(context);

		String expr = "(age > 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void gtFalseBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 21);
		this.ruleEvaluator.withContext(context);

		String expr = "(age > 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

	@Test
	public void gteBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 25);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void gteFalseBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 24);
		this.ruleEvaluator.withContext(context);

		String expr = "(age >= 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

	@Test
	public void ltBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 18);
		this.ruleEvaluator.withContext(context);

		String expr = "(age < 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void ltFalseBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 30);
		this.ruleEvaluator.withContext(context);

		String expr = "(age < 25)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}

	@Test
	public void lteBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 21);
		this.ruleEvaluator.withContext(context);

		String expr = "(age <= 21)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertTrue(result);
	}

	@Test
	public void lteFalseBooleanExpression() throws ScriptException {

		Map<String, Object> context = new HashMap<>();
		context.put("age", 35);
		this.ruleEvaluator.withContext(context);

		String expr = "(age <= 21)";

		Boolean result = this.ruleEvaluator.evaluate(expr);

		assertFalse(result);
	}
}
