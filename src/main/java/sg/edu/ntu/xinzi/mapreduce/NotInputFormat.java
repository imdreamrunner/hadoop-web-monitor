package sg.edu.ntu.xinzi.mapreduce;

import org.apache.commons.logging.Log;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import sg.edu.ntu.xinzi.util.Logger;

import java.io.IOException;
import java.util.logging.Level;

public class NotInputFormat extends FileInputFormat<Text, BytesWritable> {
    private static Log log = Logger.getLogger();

    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }

    @Override
    public RecordReader<Text, BytesWritable> createRecordReader(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        log.info("Create record reader.");
        NotRecordReader reader = new NotRecordReader();
        reader.initialize(split, context);
        return reader;
    }
}
