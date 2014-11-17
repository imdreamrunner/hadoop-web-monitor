package sg.edu.ntu.xinzi.mapreduce;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class NotMapper extends Mapper<Text, BytesWritable, Text, NotFeatureWritable> {

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

    }

    @Override
    public void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
        context.getCounter(NotDriver.RecordCounters.IMAGE_SUBMITTED).increment(1);

        byte[] tempValue = serialize(value);
        InputStream in = new ByteArrayInputStream(tempValue);
        BufferedImage image = ImageIO.read(in);
        in.close();

        NotFeatureWritable result = new NotFeatureWritable();

        context.getCounter(NotDriver.RecordCounters.IMAGE_PROCESSED).increment(1);
        context.write(key, result);
    }

    public static byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);
        writable.write(dataOut);
        dataOut.close();
        return out.toByteArray();
    }

}
