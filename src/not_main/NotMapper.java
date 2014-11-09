package not_main;

public class NotMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
    
    // map(KEYIN key, VALUEIN value, org.apache.hadoop.mapreduce.Mapper.Context context)
    public void map(LongWritable key,
                    Text value,
                    Context context,
                    Reporter reporter) throws IOException {

        String line = value.toString();

        // process key and value
        // process key and value
        // process key and value

        // .collect will make a copy of word and one
        // output.collect(word, one); // old api
        context.write(word, one);
    }
}

class NotInputFormat extends ... implements ... {

}

class NotInputSplits extends ... implements ... {

}

class RecordReader extends ... implements ... {
    
}