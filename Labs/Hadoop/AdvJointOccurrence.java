/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.mapreduce.Partitioner;

public class AdvJointOccurrence {

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, MapWritable> { 

    @Override
    protected void setup(Context context) throws IOException,
      InterruptedException {
        System.out.println("in mapper setup");
    }

    @Override
    protected void cleanup(Context context) throws IOException,
            InterruptedException {
        System.out.println("in mapper cleanup");
    }

    @Override
		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			// System.out.println("mapper  " + value.toString() );
			String prev = null;
			String current = null;
			MapWritable resultMap = new MapWritable();
			while (itr.hasMoreTokens()) {
				current = itr.nextToken();
        String s = current.toLowerCase();
        if ( Helper.isStopWord( s ) || Helper.isPunctuated( s ) ) {
          continue;
        }
				if (prev != null) {
					// System.out.println("mapper processing " + current );
					MapWritable map = (MapWritable) resultMap
							.get(new Text(prev));
					if (map == null) {
						map = new MapWritable();
						Text tmpWord = new Text(prev);
						resultMap.put(tmpWord, map);
					}
					if (!map.containsKey(new Text(current))) {
						map.put(new Text(current), new IntWritable(0));
					}
					int count = ((IntWritable) map.get(new Text(current)))
							.get();
					// System.out.println("mapper putting " + new Text(current)
					// + "," + new IntWritable(count + 1 ));
					map.put(new Text(current), new IntWritable(count + 1));
				}
				prev = current;
			}
			for (java.util.Map.Entry<Writable, Writable> tmp : resultMap
					.entrySet()) {
				// System.out.println("mapper emiting " + tmp.getKey() + ", " +
				// tmp.getValue());
				context.write((Text) tmp.getKey(), (MapWritable) tmp.getValue());
			}
		}
	}

	public static class IntSumReducer extends
			Reducer<Text, MapWritable, Text, IntWritable> {

		public void reduce(Text key, Iterable<MapWritable> values,
				Context context) throws IOException, InterruptedException {
			// System.out.println("reduce: " + key.toString());
			MapWritable result = new MapWritable();
			for (MapWritable val : values) {
				System.out.println("in outer iteration");
				for (java.util.Map.Entry<Writable, Writable> tmp : val
						.entrySet()) {
					System.out.println("in inner iteration");
					IntWritable count = null;
					if (((count = (IntWritable) result.get(tmp.getKey())) == null)) {
						result.put(tmp.getKey(), tmp.getValue());
					} else {
						IntWritable i = (IntWritable) tmp.getValue();
						result.put(tmp.getKey(), new IntWritable(i.get()
								+ count.get()));
					}
				}
			}
			System.out.println("writing " + key + " " + result);
			for (java.util.Map.Entry<Writable, Writable> cotextToCount : result
					.entrySet()) {
				String keyValPair = key + "," + (Text) cotextToCount.getKey();
				context.write(new Text(keyValPair),
						(IntWritable) cotextToCount.getValue());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
			System.exit(2);
		}
		Job job = new Job(conf, "joint occ");
		job.setJarByClass(AdvJointOccurrence.class);
		job.setMapperClass(TokenizerMapper.class);
		// job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setPartitionerClass(FirstCharTextPartitioner.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);

    // http://riccomini.name/posts/hadoop/2009-11-13-sort-reducer-input-value-hadoop/
    job.setOutputKeyComparatorClass(SortReducerByValuesKeyComparator.class);
    job.setOutputValueGroupingComparator(SortReducerByValuesValueGroupingComparator.class);
    // job.setPartitionerClass(SortReducerByValuesPartitioner.class);

		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

class FirstCharTextPartitioner extends Partitioner<Text, MapWritable> {
  public int getPartition(Text key, MapWritable value, int numReduceTasks) {
    System.out.println("in partitioner");
    return Math.abs((key.toString().charAt(0))) % numReduceTasks; 
  }
}

class SortReducerByValuesKeyComparator implements RawComparator {
    public int compare(byte[] text1, int start1, int length1, byte[] text2, int start2, int length2) {
      // hadoop gives you an extra byte before text data. get rid of it.
      byte[] trimmed1 = new byte[2];
      byte[] trimmed2 = new byte[2];
      System.arraycopy(text1, start1+1, trimmed1, 0, 2);
      System.arraycopy(text2, start2+1, trimmed2, 0, 2);
      
      char char10 = (char)trimmed1[0];
      char char20 = (char)trimmed2[0];
      char char11 = (char)trimmed1[1];
      char char21 = (char)trimmed2[1];
      
      // first enforce the same rules as the value grouping comparator
      // (first letter of key)
      int compare = new Character(char10).compareTo(char20);
      
      if(compare == 0) {
        // ONLY if we're in the same reduce aggregate should we try and
        // sort by value (second letter of key)
        return -1 * new Character(char11).compareTo(char21);
      }
      
      return compare;
    }
 
    public int compare(Text o1, Text o2) {
      // reverse the +1 since the extra text byte is not passed into
      // compare() from this function
      return compare(o1.getBytes(), 0, o1.getLength() - 1, o2.getBytes(), 0, o2.getLength() - 1);
    }
}


class SortReducerByValuesValueGroupingComparator implements RawComparator {
    public int compare(byte[] text1, int start1, int length1, byte[] text2, int start2, int length2) {
      // look at first character of each text byte array
      return new Character((char)text1[0]).compareTo((char)text2[0]);
    }
 
    public int compare(Text o1, Text o2) {
      return compare(o1.getBytes(), 0, o1.getLength(), o2.getBytes(), 0, o2.getLength());
    }
}

  

class Helper {
    private static Set<String> stopWords = new HashSet<String>( 
                                                  Arrays.asList(
"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours ", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves" ) );
   public static boolean isStopWord(String s) {
     return stopWords.contains(s);
   }
   public static boolean isPunctuated(String s) {
     return 
      s.contains("\"") || s.contains("'") || s.contains(",") || s.contains(".") || 
      s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || 
      s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || 
      s.contains("8") || s.contains("9") || s.contains("*") || s.contains("(") || 
      s.contains(")") || s.contains("[") || s.contains("]") || s.contains(".") ||
      s.contains("&") || s.contains("-") || s.contains("=") || s.contains("_") ||
      s.contains("+") || s.contains("%") || s.contains("!") || s.contains("/") ||
      s.contains("|");
   }
}
