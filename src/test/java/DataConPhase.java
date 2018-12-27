
import Utils.ExportTxt;

import java.io.IOException;

/**
 * 数据存储工具
 *
 * @author gz
 */
public class DataConPhase implements Runnable {


    private double[] data;

    public DataConPhase(double[] data) {
        this.data = data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public void SaveData() throws IOException {
        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                ExportTxt.exportTxt("" + data[i], "C:\\Users\\LLRF\\Desktop\\errorData\\PhaseRecord_17_21.txt");
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
