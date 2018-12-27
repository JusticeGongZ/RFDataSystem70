package DataFilterAndSelect.util;

import Utils.DateUtil;
import Utils.ExportTxt;

import java.io.IOException;
import java.nio.DoubleBuffer;

public class PackageDataConserve {

    private String key;
    private  DoubleBuffer doubleBuffer;
    private  double limitNum;

    public void set(String key, DoubleBuffer doubleBuffer, double limltNum){
        this.key = key;
        this.doubleBuffer = doubleBuffer;
        this.limitNum = limltNum;
    }

    public static void pass1AmpSave(String key, DoubleBuffer doubleBuffer, double limitNum) throws IOException {
        System.out.println("开始保存数据");
        byte[] tempDouble = new byte[2];
        double numDouble = 0;
        ExportTxt.exportTxt(key + " " + DateUtil.DateConvert2second(System.currentTimeMillis()), "C:/Users/LLRF/Desktop/errorData//pass1_Amp.txt");
        while (doubleBuffer.hasRemaining()) {

            double temp = doubleBuffer.get();
            if(temp != 0){

                ExportTxt.exportTxt((temp - limitNum) / limitNum * 100 + "", "C:/Users/LLRF/Desktop/errorData//pass1_Amp.txt");
            }
        }
    }

    public static synchronized void pass1PhaseSave(String key, DoubleBuffer doubleBuffer, double limitNum) throws IOException {
        System.out.println("开始保存数据");
        try {
            ExportTxt.exportTxt(key + " " + DateUtil.DateConvert2second(System.currentTimeMillis()), "C:/Users/LLRF/Desktop/errorData//pass1_Phase.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (doubleBuffer.hasRemaining()) {
            double temp = doubleBuffer.get();
            if(temp != 0){

                try {
                    ExportTxt.exportTxt((limitNum - temp) + "", "C:/Users/LLRF/Desktop/errorData//pass1_Phase.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Override
//    public void run() {
//
//        System.out.println("开始保存数据");
//        try {
//            ExportTxt.exportTxt(key + " " + DateUtil.DateConvert2second(System.currentTimeMillis()), "C:/Users/LLRF/Desktop/errorData//pass1_Phase.txt");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        while (doubleBuffer.hasRemaining()) {
//            double temp = doubleBuffer.get();
//            if(temp != 0){
//
//                try {
//                    ExportTxt.exportTxt((limitNum - temp) + "", "C:/Users/LLRF/Desktop/errorData//pass1_Phase.txt");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
