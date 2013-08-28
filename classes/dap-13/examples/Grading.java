
public class Grading {
	
	private static String scoreToGrade(double finalScore) {
		String result;
		if ( finalScore >= 90.0) {
			result =  "A";
		} else if ( finalScore >= 80.0 ) {
			result  = "B";
		} else {
			result = "C";
		}
		return result;
	}
	
	public static String computeGrades(String rawGradeString) {
		String result = "";
		int x;
		String[] lines = rawGradeString.split("\n");
		for ( int i = 0 ; i < lines.length; i++ ) {
			String line = lines[i];
			String [] fields = line.split(":");
			double t1 = Double.valueOf(fields[2]);
			double t2 = Double.valueOf(fields[3]);
			double t3 = Double.valueOf(fields[4]);
			double finalScore = 0.25 * t1 + 0.3 * t2 + 0.45 * t3; 
			String grade = scoreToGrade(finalScore);			
			result += fields[1] + " " + fields[0] + " " + grade + "\n";
		}
		return result;
	}
	
	public static void main(String[] args) {
		String t1 = "Doe:John:100:90:0" 
						+ "\n"
						+ "Brayton:Robert:10:10:15" 
						+ "\n";
		String r1 = computeGrades(t1);
		System.out.println(r1);
	}
}
