package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class TimeData04 implements Runnable{
    private  double[] data2 = new double[4096];
//    JFrame frame = TimeData01.frame;
    JPanel jPanel = TimeData01.jPanel;
//    public TimeData04(double[] data){
//        this.data2 = data;
//    }
    public void setData(double[] data){
        this.data2 = data;
    }

    @Override
    public void run() {
        TimeChart04 jz = new TimeChart04();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(jz.getCPUJFreeChart());
        TimeChart04.dynamicRun(data2);
    }
}
