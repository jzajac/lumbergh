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
	public String visitExpr_list(Expr_listContext ctx) {
		StringBuilder sb = new StringBuilder();

		ctx.expr()
				.forEach(expr -> sb.append(visit(expr)));

		return sb.toString();
	}

	@Override
	public String visitExpr(ExprContext ctx) {
		StringBuilder sb = new StringBuilder();

		ctx.condition()
				.forEach(cond -> sb.append(visit(cond)));

		return sb.toString();
	}

	@Override
	public String visitAndExpression(AndExpressionContext ctx) {
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
	public String visitPredicateAddExpression(PredicateAddExpressionContext ctx) {
		return super.visitPredicateAddExpression(ctx);
	}

	@Override
	public String visitPredicateExpression(PredicateExpressionContext ctx) {
		return visit(ctx.predicate());
	}

	@Override
	public String visitPredicateSubExpression(PredicateSubExpressionContext ctx) {
		return super.visitPredicateSubExpression(ctx);
	}

	@Override
	public String visitOrExpression(OrExpressionContext ctx) {
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
		return super.visitMathPredicate(ctx);
	}

	@Override
	public String visitInPredicate(InPredicateContext ctx) {
		return ctx.getText();
	}

	@Override
	public String visitTerm(TermContext ctx) {
		return "'" + ctx.getText() + "'";
	}

	@Override
	public String visitOperator(OperatorContext ctx) {
		if (ctx.getText().equals(ctx.EQ().getText())) {
			return " == ";
		}

		return "";
	}

	@Override
	public String visitMathoperator(MathoperatorContext ctx) {
		return super.visitMathoperator(ctx);
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
