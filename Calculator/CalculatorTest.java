package com.bham.pij.assignments.calculator;

public class CalculatorTest {

	public static void main(String[] args) {
		Calculator test = new Calculator();
		test.setExpression("0 * 2 + (3 * (8 - 4))");
		test.run();
		float result = test.getCurrentValue();
	}

}
