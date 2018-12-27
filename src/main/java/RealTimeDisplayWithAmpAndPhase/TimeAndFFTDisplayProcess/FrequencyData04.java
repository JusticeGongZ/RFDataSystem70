package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class FrequencyData04 implements Runnable{
    private double[] data = new double[4096];

//    JFrame frame = TimeData01.frame;
    JPanel jPanel = FrequencyData01.jPanel2;
//    public FrequencyData04(double[] data){
//        this.data = data;
//    }
    public void setData(double[] data){
        this.data = data;
    }


    @Override
    public void run() {
        FrequencyChart04 frequencyChart04 = new FrequencyChart04();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(frequencyChart04.getCPUJFreeChart());
//        frame.add(jPanel);
        FrequencyChart04.dynamicRun(data);
    }
}
