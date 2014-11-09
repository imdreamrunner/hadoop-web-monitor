package not_main;

public class NotDriver {
    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(NotDriver.class);
        conf.setJobName("notJob");

        conf.setOutputKeyClass(/* Key.class */);
        conf.setOutputValueClass(/* Value.class */);

        conf.setMapperClass(NotMapper.class);
        conf.setCombinerClass(NotReducer.class);
        conf.setReducerClass(NotReducer.class);

        conf.setInputFormat(/* InputFormat.class */);
        conf.setOutputFormat(/* OutputFormat.class */);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }
}