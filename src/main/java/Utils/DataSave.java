package Utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;

/**
 * 数据存储工具
 * @author gz
 */
public class DataSave implements Runnable{
    Jedis jedis = JedisPoolTool.getJedis();
    Pipeline pipeline = jedis.pipelined();


    private double[] data;
    private String keyNameTitle;

    public DataSave(double[] data, String keyNameTitle){
        this.data = data;
        this.keyNameTitle = keyNameTitle;
    }
    public void setData(double[] data, String keyNameTitle){
        this.data = data;
        this.keyNameTitle = keyNameTitle;
    }

    public void SaveData() throws IOException {
        for (int i=0; i<data.length;i++){
            String keyName2 =  DateUtil.DateConvert2hour(System.currentTimeMillis());
//                if(limitNum != null && data[i] > limitNum){
                    ExportTxt.exportTxt(keyNameTitle + " " +data[i] + " " + DateUtil.DateConvert2second(System.currentTimeMillis()),"C:/Users/LLRF/Desktop/errorData//errorDataRecord.txt");
//                }else {
                    pipeline.zadd(keyNameTitle + "_" +keyName2, System.currentTimeMillis(),data[i] + " " + System.currentTimeMillis());
                    pipeline.expire(keyNameTitle + "_" +keyName2,604800);
//                }
        }
        JedisPoolTool.returnResource(jedis);
//        JedisPoolTool.returnResource(jedis);
    }
    @Override
    public void run() {

        try {
            SaveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        JedisPoolTool.returnResource(jedis);
    }


}
