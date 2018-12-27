package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class TimeDataPhase3 implements Runnable{

    private  double[] data2 = new double[4096];

    JPanel jPanel = TimeDataPhase1.jPanel2;
//    public TimeData01(double[] data){
//        this.data2 = data;
//    }
    public void setData(double[] data){
        this.data2 = data;
    }

    @Override
    public void run() {
        TimeChartPhase03 jz = new TimeChartPhase03();
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(jz.getCPUJFreeChart());
//        frame.add(jPanel);
        TimeChartPhase03.dynamicRun(data2);
    }
}
