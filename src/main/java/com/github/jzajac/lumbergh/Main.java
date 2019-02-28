package com.github.jzajac.lumbergh;

import com.github.jzajac.lumbergh.LumberghLexer;
import com.github.jzajac.lumbergh.LumberghParser;
import com.github.jzajac.lumbergh.LumberghVisitor;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	public static void main(String[] args) {

		// current_timestamp + lifespan - expireWarn

		System.out.println("Enter the expression:");

		Scanner scan = new Scanner(System.in);

		String newQuery = scan.nextLine();

		//String newQuery = "(firstName = 'john' AND lastName = 'smith')";

		LumberghLexer lexer = new LumberghLexer(CharStreams.fromString(newQuery));
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		com.github.jzajac.lumbergh.LumberghParser parser = new LumberghParser(tokens);

		LumberghParser.ParseContext tree = parser.parse();

		LumberghVisitor visitor = new CustomLumberghVisitor();

		Object visitedExpression = visitor.visit(tree);

		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("groovy");

		Fields fname = new Fields();
		fname.name = "firstName";
		fname.value = "john";
		engine.put("fields", fname);
		engine.put("firstName", "john");
		engine.put("lastName", "smith");

		boolean evaluatedExpression = false;

		try {
			evaluatedExpression = (Boolean) engine.eval(visitedExpression.toString());

		} catch (ScriptException e) {
			e.printStackTrace();
		}

		System.out.println("Resulting groovy expression:");
		System.out.println(visitedExpression + " > " + evaluatedExpression);


	}

	static class Fields {
		private String name;

		private String value;
	}


}
