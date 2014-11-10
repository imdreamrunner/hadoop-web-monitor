package not_main;

public class NotDriver {
    static enum RecordCounters { IMAGE_SUBMITTED, IMAGE_PROCESSED };

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

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
        job.setOutputFormatClass(NotOutputFormat.class);

        
        job.setNumReduceTasks(0); // directly write to file system, without calling reducer
        job.setSpeculativeExecution(true);

        FileInputFormat.addInputPath(job, new Path(args[0])); // provide input directory
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // System.exit(job.waitForCompletion(true) ? 0 : 1); // .waitFor... will submit the job
        // or 
        RunningJob runningJob = runJob(job); // or use submitJob()

        int imageSubmitted = RunningJob.getCounters(RecordCounters.IMAGE_SUBMITTED);
        int imageProcessed = RunningJob.getCounters(RecordCounters.IMAGE_PROCESSED);
    }
}

// set mapreduce.framework.name to "local" / "classic" / "yarn"