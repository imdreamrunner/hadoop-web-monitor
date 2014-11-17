package sg.edu.ntu.xinzi.mapreduce;

import org.apache.hadoop.mapreduce.*;

import java.io.IOException;

public class NotOutputFormat<Text, NotFeatureWritable> extends OutputFormat {

    @Override
    public RecordWriter getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return null;
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return null;
    }
}
