
import Utils.ExportTxt;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;

/**
 * 数据存储工具
 *
 * @author gz
 */
public class DataCon implements Runnable {


    private double[] data;

    public DataCon(double[] data) {
        this.data = data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public void SaveData() throws IOException {
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                ExportTxt.exportTxt("" + data[i], "C:\\Users\\LLRF\\Desktop\\errorData\\AmpRecord17_21.txt");
            }
        }
    }

    @Override
    public void run() {

        try {
            SaveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
