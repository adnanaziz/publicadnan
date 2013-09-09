import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

class Candidate implements Serializable {
		private int age;
		private String name;
		Candidate(int a, String s) {
			age = a;
			name = s;
		}
		static Candidate[] getCandidates() {
			Candidate[] result = new Candidate[3];
			result[0] = new Candidate(21, "Andy");
			result[1] = new Candidate(23,"Bob");
			result[2] = new Candidate(19, "Charley");
			return result;
		}
		@Override 
		public String toString() {
			return age + ":" + name;
		}
	}


public class Decorators {

	public static void main(String[] args) throws Exception {
		Candidate[] pw = Candidate.getCandidates();
		FileOutputStream fout = new FileOutputStream("/tmp/people.gz");
		BufferedOutputStream bout = new BufferedOutputStream(fout);
		GZIPOutputStream gout = new GZIPOutputStream(bout);
		ObjectOutputStream oout = new ObjectOutputStream(gout);
		oout.writeObject(pw);
		oout.close();
		
		FileInputStream fin = new FileInputStream("/tmp/people.gz");
		BufferedInputStream bin = new BufferedInputStream(fin);
		GZIPInputStream gin = new GZIPInputStream(bin);
		ObjectInputStream oin = new ObjectInputStream(gin);
		try {
			Candidate[] pr = (Candidate[]) oin.readObject();
			for (Candidate p : pr) {
				System.out.println("Candidate:" + p.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		oin.close();
	}

}
