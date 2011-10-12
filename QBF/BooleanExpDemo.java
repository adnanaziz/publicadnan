import org.antlr.runtime.*;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class BooleanExpDemo {

	public static void main(String[] args) {
		String ex1 = "Forall x Exists y Exists w Exists z (x*!y + !x*y)";
		assert (true == testFormula(ex1));
		String ex2 = "Exists x Forall y Exists w Exists z (z + w + (x*!y + !x*y))";
		assert (true == testFormula(ex2));
		String ex3 = "Exists x Forall y Exists w Exists z (x*!y + !x*y)";
		assert (false == testFormula(ex3));
	}

	// aFormula is a formula assumed to be over x, y, z, w, with all variables
	// quantified
	public static boolean testFormula(String aFormula) {
		boolean result = false;
		try {
			ANTLRStringStream in = new ANTLRStringStream(aFormula);
			BooleanExpLexer lexer = new BooleanExpLexer(in);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			BooleanExpParser parser = new BooleanExpParser(tokens);

			Node parserResult = parser.eval();
			System.out.println("AST for " + aFormula + " = " + parserResult);

			// get the inner unquantified formula
			Node unquantFormula = parserResult;
			LinkedHashMap<String, NodeType> quantVars = new LinkedHashMap<String, NodeType>();
			while (unquantFormula.type == NodeType.FORALL
					|| unquantFormula.type == NodeType.EXISTS) {
				quantVars.put(unquantFormula.varName, unquantFormula.type);
				unquantFormula = unquantFormula.children.get(0);
			}

			/*
			 * // useful for debugging the eval function HashMap<String,Boolean>
			 * assignment = new HashMap<String,Boolean>(); assignment.put( "x",
			 * Boolean.TRUE ); assignment.put( "z", Boolean.TRUE );
			 * assignment.put( "y", Boolean.FALSE ); assignment.put( "w",
			 * Boolean.TRUE );
			 * 
			 * // System.out.println("For assignment" // + assignment.toString()
			 * + "\nValue is " + unquantFormula.eval(assignment) );
			 */

			// create a list of the variables in the formula
			List<String> varList = new ArrayList<String>();
			varList.add("x");
			varList.add("y");
			varList.add("z");
			varList.add("w");

			// used for quantifier evaluation, makes yucky use of globals...
			EvalQuant eq = new EvalQuant();
			eq.setVarList(varList);

			// get all assignments that satisfy the unquantified part of the
			// formula
			// start with all assignments
			List<HashMap<String, Boolean>> allAssignments = eq
					.createAllAssignments(varList);
			List<HashMap<String, Boolean>> unquantSatAssignments = new ArrayList<HashMap<String, Boolean>>();
			for (HashMap<String, Boolean> a : allAssignments) {
				// System.out.println("For assignment"
				// + a.toString() + "\nUnquant Part's Value is " +
				// unquantFormula.eval(a) );
				if (unquantFormula.eval(a) == true) {
					unquantSatAssignments.add(a);
				}
			}

			// need to process quantified variables from the inside out
			// ie process the variables closest to the inner (unquantified)
			// formula first
			// this code gets the variables; since quantVars is a LinkedHashSet,
			// the order is from outer to inner.
			ArrayList<String> varArray = new ArrayList<String>();
			for (String qv : quantVars.keySet()) {
				varArray.add(qv);
			}

			// initialize quantifiedResult to assignments satisfying the
			// unquantified formula
			List<HashMap<String, Boolean>> quantifiedResult = unquantSatAssignments;
			// process quantifier variables in reverse order
			for (int i = varArray.size() - 1; i >= 0; i--) {
				String qv = varArray.get(i);
				// perform FORALL/EXISTS
				quantifiedResult = (quantVars.get(qv) == NodeType.EXISTS) ? eq
						.exists(qv, quantifiedResult) : eq.forall(qv,
						quantifiedResult);
				// System.out.println("quantifier : " + qv + " " +
				// quantVars.get( qv ) );
				// System.out.println("quantified output is : " +
				// quantifiedResult.toString() );
			}
			// System.out.println("quantified output is : " +
			// quantifiedResult.toString() );

			// fully quantified formula is false iff there are no assignments
			// in the final set (there will be exactly 2^n if it's true, where n
			// = number of vars)
			result = (quantifiedResult.size() == 0) ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
