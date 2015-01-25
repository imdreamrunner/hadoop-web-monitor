package sg.edu.ntu.xinzi.mapreduce;

import org.apache.commons.logging.Log;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;
import sg.edu.ntu.xinzi.util.Logger;
import sg.edu.ntu.xinzi.util.MyImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;

public class NotMapper extends Mapper<Text, BytesWritable, Text, NotFeatureWritable> {

    public static final String LABEL = "%%%% NotMapper : ";

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

    }

    @Override
    public void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {
        BufferedImage tempImage = getBufferedImage(value);
        //////////////////////////////////////////////////////////
        // do processing here

        System.out.println(LABEL + "System::java.library.path : " + System.getProperty("java.library.path"));
        System.out.println(LABEL + "Job::java.library.path : " + context.getConfiguration().get("java.library.path"));
        MyImage aImg = new MyImage(tempImage, "jpg");

        int ret = -1;
//        ret = new BFLoG_API().Extract(aImg.data, aImg.width, aImg.height);
        NotFeatureWritable result = new NotFeatureWritable("I am a result of #" + key.toString() + "# feature: " + ret);
        System.out.println(LABEL + result.toString());
        //////////////////////////////////////////////////////////

        context.write(key, result);
    }

    public static byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);

        writable.write(dataOut);
        dataOut.close();

        byte[] result = out.toByteArray();
        return result;
    }

    public static BufferedImage getBufferedImage(BytesWritable value) throws IOException {
        InputStream in = new ByteArrayInputStream(value.getBytes());
        BufferedImage image = ImageIO.read(in);
        in.close();
        return image;
    }

}
