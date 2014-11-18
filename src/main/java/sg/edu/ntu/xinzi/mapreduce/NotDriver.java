package sg.edu.ntu.xinzi.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import sg.edu.ntu.xinzi.util.Log;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotDriver {
    static enum RecordCounters { IMAGE_SUBMITTED, IMAGE_PROCESSED };

    private static Logger logger = Log.getLogger();

    public static void run() {
        logger.log(Level.INFO, "Map driver starts running.");
        try {
            Configuration conf = new Configuration();

            conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
            conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
            conf.set("fs.default.name", "hdfs://localhost:9000");

            Job job = Job.getInstance(conf, "not a job");

            // job.setJarByClass(WordCount2.class);

            job.setInputFormatClass(NotInputFormat.class);
            job.setMapperClass(NotMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NotFeatureWritable.class);

            // job.setCombinerClass(NotCombiner.class);
            // job.setPartitionerClass(NotPartitioner.class);

            // job.setReducerClass(NotReducer.class);
            // job.setOutputKeyClass(Text.class);
            // job.setOutputValueClass(IntWritable.class);
            // job.setOutputFormatClass(NotOutputFormat.class);

            job.setNumReduceTasks(0); // directly write to file system, without calling reducer
            job.setSpeculativeExecution(true);

            FileInputFormat.addInputPath(job, new Path("/input/")); // provide input directory
            FileOutputFormat.setOutputPath(job, new Path("/output/"));

            logger.log(Level.INFO, "Waiting for completion.");
            if (job.waitForCompletion(true)) {
                logger.log(Level.INFO, "Job completed.");
            } else {
                logger.log(Level.SEVERE, "Job error.");
            }
            // or
            // RunningJob runningJob = runJob(job); // or use submitJob()

        /*
        int imageSubmitted = RunningJob.getCounters(RecordCounters.IMAGE_SUBMITTED);
        int imageProcessed = RunningJob.getCounters(RecordCounters.IMAGE_PROCESSED);
        */
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
