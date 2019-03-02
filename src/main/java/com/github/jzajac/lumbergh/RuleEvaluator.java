package com.github.jzajac.lumbergh;

import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import com.github.jzajac.lumbergh.LumberghParser;
import com.github.jzajac.lumbergh.LumberghVisitor;
import com.github.jzajac.lumbergh.LumberghLexer;

public class RuleEvaluator<T> {

	private Map<String, Object> context = new HashMap<>();

	private ScriptEngine engine;

	public RuleEvaluator() {
		ScriptEngineManager factory = new ScriptEngineManager();

		this.engine = factory.getEngineByName("groovy");
	}

	public RuleEvaluator withContext(Map<String, Object> contextToAdd) {
		this.context.putAll(contextToAdd);
		return this;
	}

	public T evaluate(String expression) throws ScriptException {

		LumberghLexer lexer = new LumberghLexer(CharStreams.fromString(expression));

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		LumberghParser parser = new LumberghParser(tokens);

		LumberghParser.ParseContext tree = parser.parse();

		LumberghVisitor visitor = new CustomLumberghVisitor();

		Object visitedExpression = visitor.visit(tree);

		if (!this.context.isEmpty()) {
			this.context.forEach((key, value) -> engine.put(key, value));
		}

		return (T) engine.eval(visitedExpression.toString());

	}

}
