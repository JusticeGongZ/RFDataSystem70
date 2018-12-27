package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class FrequencyData02 implements Runnable{
    private  double[] data = new double[4096];

//    JFrame frame = TimeData01.frame;
    JPanel jPanel = FrequencyData01.jPanel2;
//    public FrequencyData02(double[] data){
//        this.data = data;
//    }
    public void setData(double[] data){
        this.data = data;
    }


    @Override
    public void run() {
        FrequencyChart02 frequencyChart02 = new FrequencyChart02();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(frequencyChart02.getCPUJFreeChart());
//        frame.add(jPanel);
        FrequencyChart02.dynamicRun(data);
    }
}
