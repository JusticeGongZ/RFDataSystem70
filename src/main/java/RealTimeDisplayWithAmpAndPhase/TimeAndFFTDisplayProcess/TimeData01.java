package RealTimeDisplayWithAmpAndPhase.TimeAndFFTDisplayProcess;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TimeData01 implements Runnable {

    private double[] data2 = new double[4096];

    static JFrame frame = new JFrame("实时数据显示");
    static JPanel jPanel = new JPanel();

    //    public TimeData01(double[] data){
//        this.data2 = data;
//    }
    public void setData(double[] data) {
        this.data2 = data;
    }

    @Override
    public void run() {
        TimeChart01 jz = new TimeChart01();
        frame.setLayout(new GridLayout(2, 1, 5, 5));

        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel.setBorder(new EmptyBorder(50, 10, 10, 10));
        jPanel.add(jz.getCPUJFreeChart());
        frame.add(jPanel);
        frame.setSize(1400, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // 窗口居于屏幕正中央
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TimeChart01.dynamicRun(data2);
    }
}
