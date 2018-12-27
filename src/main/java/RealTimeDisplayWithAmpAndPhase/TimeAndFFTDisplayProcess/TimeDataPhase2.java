package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;

public class TimeDataPhase2 implements Runnable{

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
        TimeChartPhase02 jz = new TimeChartPhase02();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jPanel.add(jz.getCPUJFreeChart());
//        frame.add(jPanel);
        TimeChartPhase02.dynamicRun(data2);
    }
}
