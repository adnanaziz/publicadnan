import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

// key idea: represent an assignment of boolean values to variables using 
// a HashMap<String,Boolean>, and sets of assignments using Lists or Sets
// TODO(AA): refactor code to use Sets everywhere, since we need them in places
// 
// because we need to call clone, we cannot use the looser type Map<String,Boolean>
public class EvalQuant {

	// highly brittle use of globals...
	// this is the set of all the variables that the formula is defined over
	static List<String> varList;

	public static void setVarList(List<String> vList) {
		varList = vList;
	}

	// creates the set of all possible assignments (2^n of them, n = number of
	// vars)
	// does this by using 1-1 correspondence between assignments and integers
	// from 0--2^n-1
	public static List<HashMap<String, Boolean>> createAllAssignments(
			List<String> vList) {
		List<HashMap<String, Boolean>> result = new ArrayList<HashMap<String, Boolean>>();
		int numVars = vList.size();
		for (int i = 0; i < Math.pow(2, numVars); i++) {
			HashMap<String, Boolean> anAssignment = new HashMap<String, Boolean>();
			result.add(anAssignment);
			for (int j = 0; j < numVars; j++) {
				anAssignment.put(vList.get(j),
						(((i >> j) % 2 == 1) ? Boolean.TRUE : Boolean.FALSE));
			}
		}
		return result;
	}

	// compute the complement of the set passed in.
	// assuming list has no repeated elements
	public List<HashMap<String, Boolean>> complement(
			List<HashMap<String, Boolean>> assignmentList) {

		List<HashMap<String, Boolean>> allAssgns = createAllAssignments(varList);
		// need set view of list passed in...
		Set<HashMap<String, Boolean>> assgnsAsSet = new HashSet<HashMap<String, Boolean>>();
		for (HashMap<String, Boolean> a : assignmentList) {
			assgnsAsSet.add(a);
		}

		List<HashMap<String, Boolean>> result = new ArrayList<HashMap<String, Boolean>>();
		for (HashMap<String, Boolean> a : allAssgns) {
			if (!assgnsAsSet.contains(a)) {
				result.add(a);
			}
		}
		return result;
	}

	// brute force computation of exists, uses the following relation
	// Exists x F <=> F_{x=0} + F_{x=1}
	public List<HashMap<String, Boolean>> exists(String var,
			List<HashMap<String, Boolean>> assignmentList) {
		Set<HashMap<String, Boolean>> result = new HashSet<HashMap<String, Boolean>>();
		for (HashMap<String, Boolean> a : assignmentList) {
			HashMap<String, Boolean> aVar0 = (HashMap<String, Boolean>) a
					.clone();
			// this is why we need the clone (shallow copies the table)
			aVar0.put(var, Boolean.FALSE);
			HashMap<String, Boolean> aVar1 = (HashMap<String, Boolean>) a
					.clone();
			aVar1.put(var, Boolean.TRUE);
			result.add(aVar0);
			result.add(aVar1);
		}
		List<HashMap<String, Boolean>> finalResult = new ArrayList<HashMap<String, Boolean>>();
		for (HashMap<String, Boolean> a : result) {
			finalResult.add(a);
		}
		return finalResult;
	}

	// compute forall using: Forall x F = ! Exists x !F
	public List<HashMap<String, Boolean>> forall(String var,
			List<HashMap<String, Boolean>> assignmentList) {
		List<HashMap<String, Boolean>> complementOfAssignmentList = complement(assignmentList);
		List<HashMap<String, Boolean>> complementOfResult = exists(var,
				complementOfAssignmentList);
		List<HashMap<String, Boolean>> result = complement(complementOfResult);
		return result;
	}

	// useful for testing the EvalQuant class
	public static void main(String[] args) {
		List<String> varList = new ArrayList<String>();
		varList.add("x");
		varList.add("y");
		varList.add("z");
		varList.add("w");
		EvalQuant eq = new EvalQuant();
		System.out.println(eq);
		System.out.println(eq.varList);
		List<HashMap<String, Boolean>> all = createAllAssignments(varList);
		List<HashMap<String, Boolean>> assignmentList = new ArrayList<HashMap<String, Boolean>>();
		HashMap<String, Boolean> m0 = new HashMap<String, Boolean>();
		m0.put("x", true);
		m0.put("y", true);
		m0.put("z", true);
		m0.put("w", true);
		assignmentList.add(m0);
		HashMap<String, Boolean> m1 = new HashMap<String, Boolean>();
		m1.put("x", false);
		m1.put("y", true);
		m1.put("z", true);
		m1.put("w", true);
		assignmentList.add(m1);
		List<HashMap<String, Boolean>> tmp = eq.forall("x", assignmentList);
		System.out.println("Output:" + tmp);
	}
}
