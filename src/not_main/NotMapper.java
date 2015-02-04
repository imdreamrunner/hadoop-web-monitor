package not_main;

public class NotMapper extends MapReduceBase implements Mapper<Text, BytesWritable, Text, NotFeatureWritable> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

    }
    
    @Override
    public void map(Text key, BytesWritable value, Context context, Reporter reporter) throws IOException {
        reporter.incrCounter(NotDriver.RecordCounters.IMAGE_SUBMITTED, 1);

        byte[] tempValue = serialize(value);
        InputStream in = new ByteArrayInputStream(tempValue);
        BufferedImage image = ImageIO.read(in);
        in.close();

        NotFeatureWritable result = /* ... */(value);

        reporter.incrCounter(NotDriver.RecordCounters.IMAGE_PROCESSED, 1);
        context.write(key, result);
    }
}

public class NotInputFormat extends FileInputFormat<Text, BytesWritable> {
    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        NotRecordReader reader = new NotRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}

public class NotRecordReader extends RecordReader<Text, BytesWritable> {
    // <key, value> <file name, file contents in bytes>

    private FileSplit fileSplit;
    private Configuration conf;
    private boolean processed = false;
    private Text key;
    private BytesWritable value = new BytesWritable();

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        this.fileSplit = (FileSplit) split;
        this.conf = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!processed) {
            key = fileSplit.getFileName();

            byte[] contents = new byte[(int) fileSplit.getLength()];
            Path file = fileSplit.getPath();
            FileSystem fs = file.getFileSystem(conf);
            FSDataInputStream in = null;

            try {
                in = fs.open(file);
                IOUtils.readFully(in, contents, 0, contents.length);
                value.set(contents, 0, contents.length);
            } finally {
                IOUtils.closeStream(in);
            }

            processed = true;
            return true;
        }
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException {
        return processed ? 1.0f : 0.0f;
    }

    @Override
    public void close() throws IOException {

    }
}
