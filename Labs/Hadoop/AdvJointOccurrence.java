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

public class JointOccurrence {

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
		job.setJarByClass(JointOccurrence.class);
		job.setMapperClass(TokenizerMapper.class);
		// job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(MapWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
