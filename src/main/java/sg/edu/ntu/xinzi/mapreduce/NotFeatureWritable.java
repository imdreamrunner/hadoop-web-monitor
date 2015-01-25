package sg.edu.ntu.xinzi.mapreduce;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NotFeatureWritable implements Writable {

    private String feature = "empty feature";

    public NotFeatureWritable() {

    }

    public NotFeatureWritable(String feature) {
        this.feature = feature;
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        feature = Text.readString(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, feature);
    }

    public String toString() {
        return feature;
    }

}
