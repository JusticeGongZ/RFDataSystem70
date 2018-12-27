package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrequencyData01 implements Runnable{
    private  double[] data = new double[100];

    JFrame frame = TimeData01.frame;
    static JPanel jPanel2 = new JPanel();
//    public FrequencyData01(double[] data){
//        this.data = data;
//    }
    public void setData(double[] data){
        this.data = data;
    }


    @Override
    public void run() {
        FrequencyChart01 frequencyChart01 = new FrequencyChart01();
        jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel2.setBorder(new EmptyBorder(10, 10, 50, 10));
        jPanel2.add(frequencyChart01.getCPUJFreeChart());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.add(jPanel2);

        FrequencyChart01.dynamicRun(data);
    }
}
