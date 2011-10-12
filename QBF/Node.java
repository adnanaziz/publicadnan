import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class Node {
	public static Set<Node> nodeSet = new HashSet<Node>();
	public static StringBuffer sb = new StringBuffer();

	public static void log(String s) {
		// System.out.println( s ) ;
		sb.append(s);
	}

	public NodeType type;
	public boolean value;
	public String msg;
	public String varName;
	public List<Node> children;

	public Node(NodeType type) {
		this.type = type;
		this.children = new ArrayList<Node>();
	}

	public Node(NodeType type, Node... kids) {
		this.type = type;
		this.children = new ArrayList<Node>();
		for (Node n : kids)
			children.add(n);
	}

	public static Node rawNode(String s) {
		Node result = new Node(NodeType.UNDEF);
		result.msg = s;
		return result;
	}

	public static int indentLevel = 0;
	public static String indentString = " ";

	public static String indentString() {
		StringBuffer rsb = new StringBuffer();
		for (int i = 0; i < indentLevel; i++) {
			rsb.append(indentString);
		}
		return rsb.toString();
	}

	@Override
	public String toString() {
		String result = indentString() + type
				+ ((varName != null) ? (":" + varName) : "") + "\n";
		indentLevel++;
		for (Node n : children)
			result = result + n.toString();
		indentLevel--;
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Node))
			return false;
		Node tmp = (Node) o;
		return (type == tmp.type && children.equals(tmp.children));
	}

	public boolean eval(Map<String, Boolean> assignment) {
		if (type == NodeType.VARIABLE) {
			return assignment.get(varName);
		} else if (type == NodeType.NOT) {
			return !children.get(0).eval(assignment);
		} else if (type == NodeType.AND) {
			return children.get(0).eval(assignment)
					&& children.get(1).eval(assignment);
		} else if (type == NodeType.OR) {
			return children.get(0).eval(assignment)
					|| children.get(1).eval(assignment);
		} else {
			System.out.println("Panic, unknown type");
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 42;
	}
}
