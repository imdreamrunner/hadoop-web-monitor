package sg.edu.ntu.xinzi.mapreduce;

import org.apache.commons.logging.Log;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import sg.edu.ntu.xinzi.util.Logger;

import java.io.IOException;
import java.util.logging.Level;

public class NotDriver {
    static enum RecordCounters { IMAGE_SUBMITTED, IMAGE_PROCESSED };

    private static Log log = Logger.getLogger();

    public static void run() {
        log.info("Configuring Hadoop job.");
        try {
            Configuration conf = new Configuration();

//            conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
//            conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
//            conf.set("fs.default.name", "hdfs://localhost:9000");

            Job job = Job.getInstance(conf, "not a job");

            // job.setJarByClass(WordCount2.class);

            job.setJarByClass(NotMapper.class);
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

            log.info("Start running Hadoop job.");
            if (job.waitForCompletion(true)) {
                log.info("Job completed.");
            } else {
                log.info("Job error.");
            }
            // or
            // RunningJob runningJob = runJob(job); // or use submitJob()

        /*
        int imageSubmitted = RunningJob.getCounters(RecordCounters.IMAGE_SUBMITTED);
        int imageProcessed = RunningJob.getCounters(RecordCounters.IMAGE_PROCESSED);
        */
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            log.error("Exception during execution.");
            e.printStackTrace();
        }
    }
}
