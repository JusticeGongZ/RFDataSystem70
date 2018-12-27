package RealTimeDisplayWithRange.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class FrequencyData03 implements Runnable{
    private  double[] data = new double[4096];

//    JFrame frame = TimeData01.frame;
    JPanel jPanel = FrequencyData01.jPanel2;
//    public FrequencyData03(double[] data){
//        this.data = data;
//    }
       public void setData(double[] data){
        this.data = data;
    }


    @Override
    public void run() {
        FrequencyChart03 frequencyChart03 = new FrequencyChart03();
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(frequencyChart03.getCPUJFreeChart());
//        frame.add(jPanel);
        FrequencyChart03.dynamicRun(data);
    }
}
