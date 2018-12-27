package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class TimeData02 implements Runnable{
    private  double[] data2 = new double[4096];
//    JFrame frame = TimeData01.frame;
    JPanel jPanel = TimeData01.jPanel;

//    public TimeData02(double[] data){
//        this.data2 = data;
//    }
    public void setData(double[] data){
        this.data2 = data;
    }


    @Override
    public void run() {
        TimeChart02 jz = new TimeChart02();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(jz.getCPUJFreeChart());
        TimeChart02.dynamicRun(data2);
    }
}
