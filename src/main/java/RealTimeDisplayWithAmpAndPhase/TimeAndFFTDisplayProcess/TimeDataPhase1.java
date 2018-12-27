package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TimeDataPhase1 implements Runnable{
    private  double[] data = new double[4096];
    JFrame frame = TimeData01.frame;
    static JPanel jPanel2 = new JPanel();
//    public TimeData01(double[] data){
//        this.data2 = data;
//    }
    public void setData(double[] data){
        this.data = data;
    }

    @Override
    public void run() {
        TimeChartPhase01 jz = new TimeChartPhase01();
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel2.setBorder(new EmptyBorder(10, 10, 50, 10));
        jPanel2.add(jz.getCPUJFreeChart());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.add(jPanel2);
        TimeChartPhase01.dynamicRun(data);
    }
}
