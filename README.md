# Lumbergh

A relatively straightforward framework for defining and parsing business rules. Built on top of [ANTLR](https://github.com/antlr/antlr4).

Business rules are defined using a format that's similar to SQL (see the Examples section below). These business rules are passed into an instance of `RuleEvaluator`, optionally with a context object (of type `Map<String, object`) for insertion of dynamic values.

The `RuleEvaluator` will use ANTLR to provide a parse tree, walked using the `CustomLumberghVisitor`, and rules transformed into Groovy. The Groovy code is then passed into a `ScriptEngine` for evaluation. 

Depending on how the `RuleEvaluator` is initialized, Lumbergh will either return a boolean or an integer.

## In short: 

✅ Will evaluate business rules

❌ Will not steal your stapler

## Motivation

I've had to write a lot of rule based systems. Many times, they start off pretty simple: a few if/else statements peppered throughout the code, high test coverage, everything's great.

Over time, the complexity grows -- features are added/removed, laws are enacted, and the organization's risk profile changes. What started out as a few if/else statements has now become if/elseif/else statements nested many layers deep. It's now significantly more difficult to maintain these rules and understand _what_ the rules are actually doing.

Lumbergh is an attempt to solve that.

I wanted something that's a middle ground between a ton of if/else statements and rolling out Drools. I also didn't want to tightly couple rules with execution of code -- i.e., if a given rule evaluates to true, then call `MyService#doSomething()` ([like so](https://github.com/j-easy/easy-rules#or-using-a-rule-descriptor)). 

Rather, I just want to know what a rule evaluates to and then I'll decide what to do. That may sound unusual, but in highly regulated industries (i.e., financial services) I have seen this come up time and again. 

For example, let's say there's a property & casualty insurance company implementing Lumbergh. The company has a set of actuarial tables that define what premium to charge for home insurance, collectively called a 'rating program'. The rating program currently in effect is 'Triangle'. Starting on July 1st, 2019 a new rating program called 'Octagon' will be introduced, which offers a $1 discount if your house is painted with zebra stripes.   

```java
Boolean isEligibleForZebraStripedDiscount = ruleEvaluator.evaluator("(homeColor = 'zebra_striped')")

// New rating program goes into effect on July 1st
LocalDate octagonEffectiveDate = LocalDate.of(2019, Month.JULY, 1);

if (LocalDate.now().isAfter(octagonEffectiveDate)) {
    // Call the new Octagon rating service to get a premium
    this.octagonRatingService.determinePremium(quote, isEligibleForZebraStripedDiscount);
}
else {
    // Call the legacy Triangle rating service to get a premium
    this.triangleRatingService.determinePremium(quote, isEligibleForZebraStripedDiscount);
}

``` 

## Why not just write rules directly in Groovy, Javascript, or some other scripting language?

It's possible, but the value add of Lumbergh is an intentionally limited set of tools in the toolbox. Rules are meant to be terse, and by constraining the types of operators and syntax that can be used, should be easy to quickly understand.

By using a scripting language directly, ensuring rules conform to a specific style becomes more difficult and testing becomes more challenging. Some sets of business rules are exceptionally complicated to model, and these probably aren't a great fit for Lumbergh.

## Rule examples

Check that the user's zip code is in your delivery area: 

`(zipCode IN (03801, 03802, 03820))`

Check that a user is between 18 and 21 (inclusive):

`(age >= 18 AND age <= 21)`

Check if a user is in a state with online sales tax:

`(state NOT IN ('AK, 'DE', 'MT', 'NH', 'OR'))`

Check that a user's billing address state matches their shipping address state:

`(billingAddress.state = shippingAddress.state)`

Segment customers based on attributes:

`(avgOrderAmount >= 100000 AND numberOfOrders >= 10) OR (avgOrderAmount >= 50000 AND numberOfOrders >= 20)`

Determine when to send a drip email to a prospect:

`(lastEmailSentInDays >= 3) OR (lastVisitToWebsiteInDays <= 2)`

## End to end example

```java

public class MyBusinessLogic {
	
	private RuleEvaluator<Boolean> ruleEvaluator = new RuleEvaluator<>();
	
	public void doSomething() {
		
		// Set up the context
		Map<String, Object> context = new HashMap<>();
        context.put("age", 20);
        this.ruleEvaluator.withContext(context);
        
        // Define the business rule -- this could be defined externally (i.e., in a yml file) and loaded in
        String expr = "(age >= 18 AND age <= 21)";
        
        try {
        	// Evaluate the rule
        	Boolean result = this.ruleEvaluator.evaluate(expr);
        } catch (ScriptException e) {
        	// Handle the exception
        }

		
	}
	
}

```

