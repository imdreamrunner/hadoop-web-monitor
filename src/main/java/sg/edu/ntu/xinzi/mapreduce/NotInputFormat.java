package sg.edu.ntu.xinzi.mapreduce;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import sg.edu.ntu.xinzi.util.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotInputFormat extends FileInputFormat<Text, BytesWritable> {
    private static Logger logger = Log.getLogger();

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        logger.log(Level.INFO, "Create record reader.");
        NotRecordReader reader = new NotRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}
