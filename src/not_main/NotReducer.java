package not_main;

public class NotReducer extends MapReduceBase
                        implements Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key,
                        Iterator<IntWritable> values,
                        OutputCollector<Text, IntWritable> output,
                        Reporter reporter) throws IOException {

        // use key
        // and (Iterator)values

        // to get new key and value

        // .collect will make a copy of word and one
        output.collect(key, new IntWritable(777));
    }
}

class NotOutputFormat extends ... implements ... {

}

class RecordWriter extends ... implements ... {

}

class NotCombiner extends ... implements ... {
    
}