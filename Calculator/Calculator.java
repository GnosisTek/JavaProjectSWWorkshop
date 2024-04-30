package com.bham.pij.assignments.calculator;

/* Martin Golding-Quigley
 */

import java.util.ArrayList;

public class Calculator {
	String expression;
	float result, mem = 0;
	
	public Calculator() {
	}
	
	
	public float evaluate(String expression) 
	{	
		float result;
		if (checkvalid(expression) == false) {
			result = Float.MIN_VALUE;
		}
		else {
			ArrayList<String> revpol = reversepolishgenerator(expression);
			result = reversepolishsolver(revpol);
		}
		
		
		if (result == Float.MIN_VALUE) {
			System.out.println("Invalid input.");
		}
		else {
			System.out.println(result);
		}
		
		return result;
	}
	
	
	
	
	
	public boolean checkvalid(String expression) {
		
		char startchar = expression.charAt(0);
		char endchar = expression.charAt(expression.length()-1);
		boolean result = true;
		
		if ((!Character.isDigit(startchar)) && (startchar != '+') && (startchar != '-') 
				&& (startchar != '*') && (startchar != '/') && (startchar != '(')) {
			result = false;
		}
		else {
			if((!Character.isDigit(endchar)) && (endchar != ')')) {
				result = false;	
			}
			else {
				for (int count = 0; count < expression.length()-2; count++) {
					
					
					if ((!Character.isDigit(expression.charAt(count))) && (expression.charAt(count) != '+') && (expression.charAt(count) != '-') 
							&& (expression.charAt(count) != '*') && (expression.charAt(count) != '/') && (expression.charAt(count) != '(')
							 && (expression.charAt(count) != ')') && (expression.charAt(count) != '.') && (!Character.isWhitespace(expression.charAt(count)))) {
						result = false;
					}
					else {
						switch(expression.charAt(count)) {
							case '.':{
								if (!Character.isDigit(expression.charAt(count+1))) {
									result = false;
								}
							}
							/*case '+':{
								if (!Character.isWhitespace(expression.charAt(count+1))) {
									result = false;
								}
							}
							case '-':{
								if (!Character.isWhitespace(expression.charAt(count+1))) {
									result = false;
								}
							}*/
							/*case '/':{
								if (!Character.isWhitespace(expression.charAt(count+1))) {
									result = false;
								}
							}*/
							/*case '*':{
								if (!Character.isWhitespace(expression.charAt(count+1))) {
									result = false;
								}
							}*/
							case '(':{
								if (!Character.isDigit(expression.charAt(count+1))) {
									result = false;
								}
							}
							/*case ')':{
								if (!Character.isWhitespace(expression.charAt(count+1))) {
									result = false;//Can have second )
								}
							}*/
							case ' ':{
								if ((expression.charAt(count+1) != '+') && (expression.charAt(count+1) != '-') && (expression.charAt(count+1) != '*')
										 && (expression.charAt(count+1) != '/') && (expression.charAt(count+1) != '(') 
										 && (!Character.isDigit(expression.charAt(count+1)))) {
									result = false;
								}
							}
							default:{
								if (Character.isDigit(expression.charAt(count))) {
									if (Character.isWhitespace(expression.charAt(count+1))) {
										if (Character.isDigit(expression.charAt(count+2))) {
											result = false;
										}
									}
									else {
										if ((!Character.isDigit(expression.charAt(count+1))) && (expression.charAt(count+1) != ' ') 
											&& (expression.charAt(count+1) != '.')  && (expression.charAt(count+1) != ')')) {
										result = false;	
										}
									}					
								}
							}
						}
					}
					if (result == false) {
						break;
					}
				}	
			}
		}
		
		
		
		
				
		return result;
		
	}
	
	
	
	
	
	
	public ArrayList<String> reversepolishgenerator(String expression){
		
		ArrayList<String> revpol = new ArrayList<String>();
		ArrayList<String> operands = new ArrayList<String>();
		
		if ((expression.charAt(0) == '+') || (expression.charAt(0) == '-') || (expression.charAt(0) == '*') || (expression.charAt(0) == '/')) {
			expression = mem + " " + expression;
		}
		
		for (int count = 0; count<expression.length(); ++count) 
		{			
			if (Character.isDigit(expression.charAt(count))) 
			{
				String num = generatenumstring(expression, count);
				revpol.add(num);
				count+=num.length()-1;
				
			}
			else 
			{
				if ((expression.charAt(count) == '+') || (expression.charAt(count) == '-') ||
						(expression.charAt(count) == '*') || (expression.charAt(count) == '/') || 
						(expression.charAt(count) == '(') || (expression.charAt(count) == ')')) {
					
					if ((operands.size()==0) && (expression.charAt(count) != ')')) {
						operands.add(0, Character.toString(expression.charAt(count)));
					}
					else {
						switch (expression.charAt(count)) {
						
							case '+':{
								int remove = 0;
								for (int index = 0; index<operands.size(); index++) {
									if ((operands.get(index).equals("-")) || (operands.get(index).equals("*")) || (operands.get(index).equals("/"))) {
										revpol.add(operands.get(index));
										remove+=1;
										
									}
									else {
										break;
									}								}
								
								for (int index = 0; index<remove; index++) {
									operands.remove(0);
								}
								
								operands.add(0, Character.toString(expression.charAt(count)));
								break;
							}
							
							case '-':{
								int remove = 0;
								for (int index = 0; index<operands.size(); index++) {
									if ((operands.get(index).equals("+")) || (operands.get(index).equals("*")) || (operands.get(index).equals("/"))) {
										revpol.add(operands.get(index));
										remove+=1;
									}
									else {
										break;
									}
								}
							
								for (int index = 0; index<remove; index++) {
									operands.remove(0);
								}
								operands.add(0, Character.toString(expression.charAt(count)));
								break;
							}
							
							case '*':{
								int remove = 0;
								for (int index = 0; index<operands.size(); index++) {
									if (operands.get(index).equals("/")) {
										revpol.add(operands.get(index));
										remove+=1;
									}
									else {
										break;
									}
								}
								
								for (int index = 0; index<remove; index++) {
									operands.remove(0);
								}								
								operands.add(0, Character.toString(expression.charAt(count)));
								break;
							}
							
							case '/':{
								int remove = 0;
								for (int index = 0; index<operands.size(); index++) {
									if (operands.get(index).equals("*")) {
										revpol.add(operands.get(index));
										remove+=1;
									}
									else {
										break;
									}
								}
								
								for (int index = 0; index<remove; index++) {
									operands.remove(0);
								}
								operands.add(0, Character.toString(expression.charAt(count)));
								break;
							}
							
							case '(':{
								operands.add(0, Character.toString(expression.charAt(count)));
								break;
							}
							
							case ')':{
								int remove = 0;
								for (int index = 0; index<operands.size(); index++) {
									if (operands.get(index).equals("(")) {
										break;
									}
									else {
										revpol.add(operands.get(index));
										remove+=1;
									}
								}
								
								for (int index = 0; index<=remove; index++) {
									operands.remove(0);
								}
								break;
							}
							
						}					
					}
				}	
			}
			
		}
		for (int count = 0; count<operands.size();count++) {
			revpol.add(operands.get(count));
		}
		
		return revpol;
	}
	
	
	
	
	
	
	
	
	public float reversepolishsolver(ArrayList<String> revpol) {
		
		float finalresult = 0;
		boolean invalid = false;
		
		for (int count = 2; count<revpol.size(); count++) {
			float val1, val2, result = 0;
			
			if ((revpol.get(count).equals("+")) || (revpol.get(count).equals("-")) ||
					(revpol.get(count).equals("*")) || (revpol.get(count).equals("/"))) {
				
				val1 = Float.parseFloat(revpol.get(count-2));
				val2 = Float.parseFloat(revpol.get(count-1));
				
				switch (revpol.get(count))
				{
					case "+": {
						result = val1 + val2;
						break;
					}
					case "-": {
						result = val1 - val2;
						break;
					}
					case "*": {
						result = val1 * val2;
						break;
					}
					case "/": {
						if(val2 == 0) {
							invalid = true;
						}
						else {
							result = val1 / val2;
						}
						break;
					}
				
				}
				if (invalid == true) {
					finalresult = Float.MIN_VALUE;
					break;
				}
				else {
					revpol.set(count, Float.toString(result));
					for (int index = count-3; index>=0; index--) {
						revpol.set(index+2, revpol.get(index));
					}
				}
			}
			if (invalid != true) {
				finalresult = Float.parseFloat(revpol.get(count));
			}
		}
		return finalresult;
	}
	
	
	
	
	


	public String generatenumstring(String expression, int count) {
		String numstring = "";
		char character = expression.charAt(count);
		while ((!Character.isWhitespace(character)) && (character != ')') && (count < expression.length())) 
		{
			numstring += character;
			count+=1;
			if (count != expression.length()) {
				character =  expression.charAt(count);
			}
		}
		return numstring;
		
	}
	
	
	
	
	public float getMemoryValue() {
		return this.mem;
	}
	
	public void setMemoryValue(float memval) {
		this.mem = memval;
	}
	
	public void clearMemory() {
		this.mem = 0;
	}
	
	
	
	
	
	
	public void run() {
		result = evaluate(expression);
	}
	
	public String getExpression() {
		return expression;
	}


	public void setExpression(String expression) {
		this.expression = expression;
	}


	public float getCurrentValue() {
		float currentvalue;
		if (result == Float.MIN_VALUE) {
			currentvalue = 0;
		}
		else {
			currentvalue = result;
		}
		return currentvalue;
	}

}
