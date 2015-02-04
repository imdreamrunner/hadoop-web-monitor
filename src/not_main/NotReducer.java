package not_main;

public class NotReducer extends MapReduceBase implements Reducer<Text, NotFeatureWritable, Text, NotFeatureWritable> {

    private MultipleOutputs<NullWritable, Text> multipleOutputs;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        multipleOutputs = new MultipleOutputs<NullWritable, Text>(context);
    }

    @Override
    public void reduce(Text key, Iterator<NotFeatureWritable> values, OutputCollector<Text, NotFeatureWritable> output, Reporter reporter) throws IOException {

        // context.write(key, values);

        for (NotFeatureWritable eachFeature : values)
            multipleOutputs.write(NullWritable.get(), eachFeature.toString(), key.toString());
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}

// public class NotPartitioner extends Partitioner<Text, NotFeatureWritable> {
//     @Override
//     public int getPartition(Text key, NotFeatureWritable value, int numPartitions) {
//         ...
//         ...
//         ...
//         return /* partition index */; // [0, numPartitions)
//     }
// }

public class NotOutputFormat<Text, NotFeatureWritable> extends FileOutputFormat {

    @Override
    public RecordWriter<Text, NotFeatureWritable> getRecordWriter(FileSystem ignored, JobConf job, String name, Progressable progress) throws IOException {
        Path file = FileOutputFormat.getTaskOutputPath(job, name);
        FileSystem fs = file.getFileSystem(job);
        FSDataOutputStream fileOut = fs.create(file, progress);
        return new NotOutputFormat<Text, NotFeatureWritable>(fileOut);
    }

    protected static class XmlRecordWriter<K, V> implements RecordWriter<K, V> {
        private DataOutputStream out;

        public void XmlRecordWriter(DataOutputStream out) throws IOException {
            this.out = out;
        }

        @Override
        public synchronized void write(Text key, NotFeatureWritable value) throws IOException {
            if (key == null || value == null)
                return ;

            value.write((DataOutput) out);
        }

        @Override
        public synchronized void close(Reporter reporter) throws IOException {
            out.close();
        }
    }
}