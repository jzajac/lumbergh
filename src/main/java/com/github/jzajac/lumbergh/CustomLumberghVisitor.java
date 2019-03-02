package com.github.jzajac.lumbergh;

import com.github.jzajac.lumbergh.LumberghBaseVisitor;
import com.github.jzajac.lumbergh.LumberghParser;
import com.github.jzajac.lumbergh.LumberghParser.*;

public class CustomLumberghVisitor extends LumberghBaseVisitor<String> {


	@Override
	public String visitParse(ParseContext ctx) {
		return visit(ctx.expr_list(0));
	}

	@Override
	public String visitExpr(ExprContext ctx) {
		StringBuilder sb = new StringBuilder();

		ctx.condition()
				.forEach(cond -> sb.append(visit(cond)));

		return sb.toString();
	}


	@Override
	public String visitExpression(ExpressionContext ctx) {
		return visit(ctx.expr());
	}

	@Override
	public String visitAndExpression(AndExpressionContext ctx) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < ctx.expr().size(); i++) {
			sb.append(visit(ctx.expr(i)));

			if (i < ctx.expr().size() - 1) {
				sb.append(" && ");
			}
		}

		return sb.toString();
	}

	@Override
	public String visitOrExpression(OrExpressionContext ctx) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < ctx.expr().size(); i++) {
			sb.append(visit(ctx.expr(i)));

			if (i < ctx.expr().size() - 1) {
				sb.append(" || ");
			}
		}

		return sb.toString();
	}

	@Override
	public String visitAndCondition(AndConditionContext ctx) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < ctx.condition().size(); i++) {
			sb.append(visit(ctx.condition(i)));

			if (i < ctx.condition().size() - 1) {
				sb.append(" && ");
			}
		}

		return sb.toString();
	}

	@Override
	public String visitAddCondition(AddConditionContext ctx) {
		return super.visitAddCondition(ctx);
	}

	@Override
	public String visitPredicateCondition(PredicateConditionContext ctx) {
		return visit(ctx.predicate());
	}


	@Override
	public String visitOrCondition(OrConditionContext ctx) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < ctx.condition().size(); i++) {
			sb.append(visit(ctx.condition(i)));

			if (i < ctx.condition().size() - 1) {
				sb.append(" || ");
			}
		}


		return sb.toString();
	}

	@Override
	public String visitReference(ReferenceContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitElement(ElementContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitOperatorPredicate(OperatorPredicateContext ctx) {
		return "" + visit(ctx.reference()) + " " + visit(ctx.operator()) + " " + visit(ctx.term()) + " ";
	}

	@Override
	public String visitMathPredicate(MathPredicateContext ctx) {
		return "" + visit(ctx.reference()) + " " + visit(ctx.mathoperator()) + " " + visit(ctx.term()) + " ";
	}

	@Override
	public String visitInPredicate(InPredicateContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitTerm(TermContext ctx) {
		if (ctx.reference() != null) {
			return "'" + ctx.reference().getText() + "'";
		}
		else if (ctx.value() != null) {
			return "" + ctx.value().getText() + "";
		}

		return "";
	}

	@Override
	public String visitOperator(OperatorContext ctx) {
		// Equals
		if (ctx.EQ() != null) {
			return " == ";
		}
		// Not equals
		else if (ctx.NE() != null) {
			return " != ";
		}
		// Less than equal
		else if (ctx.LTE() != null) {
			return " <= ";
		}
		// Greater than equal
		else if (ctx.GTE() != null) {
			return " >= ";
		}
		// Less than
		else if (ctx.LT() != null) {
			return " < ";
		}
		// Greater than
		else if (ctx.GT() != null) {
			return " > ";
		}

		return "";
	}

	@Override
	public String visitMathoperator(MathoperatorContext ctx) {
		if (ctx.getText().equals(ctx.ADD().getText())) {
			return " + ";
		}
		else if (ctx.getText().equals(ctx.ADD().getText())) {
			return " - ";
		}

		return "";
	}

	@Override
	public String visitListoperator(ListoperatorContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitIntegerValue(IntegerValueContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitDoubleValue(DoubleValueContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitStringValue(StringValueContext ctx) {
		return ctx.getText();
	}
}
