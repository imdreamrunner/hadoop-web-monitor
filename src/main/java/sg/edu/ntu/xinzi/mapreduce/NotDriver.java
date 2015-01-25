package sg.edu.ntu.xinzi.mapreduce;

import org.apache.commons.logging.Log;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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

    public static final String LABEL = "%%%% NotDriver : ";

    public static Configuration conf;

    public static String inputPath = "/input/";
    public static String outputPath = "/output/";

    private static Log log = Logger.getLogger();

    public static void run() {
        log.info("Configuring Hadoop job.");
        try {
//            job.setJarByClass(NotMapper.class);

            conf = new Configuration();

            String uri = conf.get("fs.default.name");
            System.out.println("%%%% fs.default.name : " + uri);

            System.out.println("%%%% NotDriver : System::java.library.path : " + System.getProperty("java.library.path"));
            System.out.println("%%%% NotDriver : Job::java.library.path : " + conf.get("java.library.path"));

            Job job = Job.getInstance(conf, "not-a-job");
//        job.addCacheFile(new URI(uri + "/lib/libBFLoG.so#libBFLoG.so"));
//        job.createSymlink();
//        job.addCacheFile(new URI(uri + "/lib/libbflog_api.so#libbflog_api.so"));
//        job.createSymlink();
//    	URI temp = new URI(uri + "/lib/libbflog_api.so#libbflog_api.so");
//    	System.out.println(LABEL + temp.toString());
//    	DistributedCache.createSymlink(conf); DistributedCache.addCacheFile(temp, conf);

            job.setInputFormatClass(NotInputFormat.class);
            job.setMapperClass(NotMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NotFeatureWritable.class);

            // job.setCombinerClass(NotCombiner.class);
            // job.setPartitionerClass(NotPartitioner.class);

//         job.setReducerClass(NotReducer.class);
//         job.setOutputKeyClass(Text.class);
//         job.setOutputValueClass(NotFeatureWritable.class);
            job.setOutputFormatClass(NotOutputFormat.class);

            job.setNumReduceTasks(0); // directly write to file system, without calling reducer

            job.setSpeculativeExecution(true);



            System.out.println(LABEL + "delete old output path...");
            FileSystem fs = FileSystem.get(new Configuration());
            fs.delete(new Path(outputPath), true);

            FileInputFormat.addInputPath(job, new Path(inputPath)); // provide input directory
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            log.info("Start running Hadoop job.");

            boolean ok = job.waitForCompletion(true);
            if (ok) {
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
