package RealTimeDisplayWithRange;

import RealTimeDisplayWithRange.TimeAndFFTDisplayProcess.*;
import RealTimeDisplayWithRange.util.AverageDataCalculate;
import RealTimeDisplayWithRange.util.DataConvert;
import Utils.Constant;
import Utils.FFTCalculate;
import org.apache.commons.math3.transform.DftNormalization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


@SuppressWarnings("serial")
public class MainFrame extends javax.swing.JFrame {
    private JMenuBar MainMenu;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JButton jButton1;
    private JPanel jPanel1;
    //second menu
    private JMenuItem jMenuItem1;
    static TimeData01 timeData01 = new TimeData01();
    static TimeData02 timeData02 = new TimeData02();
    static TimeData03 timeData03 = new TimeData03();
    static TimeData04 timeData04 = new TimeData04();

    static TimeDataPhase1 timeDataPhase1 = new TimeDataPhase1();
    static TimeDataPhase2 timeDataPhase2 = new TimeDataPhase2();
    static TimeDataPhase3 timeDataPhase3 = new TimeDataPhase3();
    static TimeDataPhase4 timeDataPhase4 = new TimeDataPhase4();
//    static FrequencyData01 frequencyData01 = new FrequencyData01();
//    static FrequencyData02 frequencyData02 = new FrequencyData02();
//    static FrequencyData03 frequencyData03 = new FrequencyData03();
//    static FrequencyData04 frequencyData04 = new FrequencyData04();
    static double Pass1AmpAvg;
    static double Pass1PhaseAvg;
    static double Pass2AmpAvg;
    static double Pass2PhaseAvg;
    static double Pass3AmpAvg;
    static double Pass3PhaseAvg;
    static double Pass4AmpAvg;
    static double Pass4PhaseAvg;
//    static String path;


    /**
     * real time data display main program
     *
     * @param args
     * @throws IOException
     * @author gz
     */
    public static void main(String[] args) throws IOException {

        int byteLength = 8192;
        int dataLength = 4096;


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame inst = new MainFrame();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        byte[] flagBuf = new byte[1];
        int flag = 0;
        byte[] buf = new byte[10002];
        byte[] subbuf = new byte[byteLength];

        InetAddress group = InetAddress.getByName("224.10.10.10");
        int port = 50000;
        MulticastSocket msr = null;
        msr = new MulticastSocket(port);
        msr.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(buf,
                buf.length);
        int count = 100;


        while(count > 0){

            msr.receive(packet);
            buf = packet.getData();
//        System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) buf[0];
            if(flag == 0){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] PhaseAvg  = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
                Pass1PhaseAvg = AverageDataCalculate.PhaseAvgData(PhaseAvg);
                System.out.println(Pass1PhaseAvg);
            }
            if(flag == 1){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] AmpAvg  = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
                Pass1AmpAvg = AverageDataCalculate.AmpAvgData(AmpAvg);
                System.out.println(Pass1AmpAvg);
            }
            if(flag == 2){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] PhaseAvg = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
                Pass1PhaseAvg = AverageDataCalculate.PhaseAvgData(PhaseAvg);
                System.out.println(Pass1PhaseAvg);
            }
            if(flag == 3){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] AmpAvg = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
                Pass2AmpAvg = AverageDataCalculate.AmpAvgData(AmpAvg);
                System.out.println(Pass2AmpAvg);
            }
            if(flag == 4){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] PhaseAvg = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
                Pass1PhaseAvg = AverageDataCalculate.PhaseAvgData(PhaseAvg);
                System.out.println(Pass1PhaseAvg);
            }
            if(flag == 5){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] AmpAvg = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
                Pass1AmpAvg = AverageDataCalculate.AmpAvgData(AmpAvg);
                System.out.println(Pass1AmpAvg);
            }
            if(flag == 6){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] PhaseAvg = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
                Pass1PhaseAvg = AverageDataCalculate.PhaseAvgData(PhaseAvg);
                System.out.println(Pass1PhaseAvg);
            }
            if(flag == 7){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                double[] AmpAvg = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
                Pass1AmpAvg = AverageDataCalculate.AmpAvgData(AmpAvg);
                System.out.println(Pass1AmpAvg);
            }
            count--;
        }
        double[] AfterConvertData1 = new double[dataLength];
        double[] AfterConvertData2 = new double[dataLength];
        double[] AfterConvertData3 = new double[dataLength];
        double[] AfterConvertData4 = new double[dataLength];
        double[] AfterConvertData5 = new double[dataLength];
        double[] AfterConvertData6 = new double[dataLength];
        double[] AfterConvertData7 = new double[dataLength];
        double[] AfterConvertData8 = new double[dataLength];

        FFTCalculate fft = new FFTCalculate(DftNormalization.STANDARD);

        while (true) {

            //data receive
            msr.receive(packet);
            buf = packet.getData();
//            System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) buf[0];
            System.arraycopy(buf, 2, subbuf, 0, byteLength);

            //update data
            if (flag == 1) {
                AfterConvertData1 = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
            }
            if (flag == 3) {

                AfterConvertData2 = DataConvert.ByteArray2DoubleArray_pass2Amp(subbuf);
            }
            if (flag == 5) {

                AfterConvertData3 = DataConvert.ByteArray2DoubleArray_pass3Amp(subbuf);
            }
            if (flag == 7) {

                AfterConvertData4 = DataConvert.ByteArray2DoubleArray_pass4Amp(subbuf);
            }

            if(flag == 0){
                AfterConvertData5 = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
            }
            if(flag == 2){
                AfterConvertData6 = DataConvert.ByteArray2DoubleArray_pass2Phase(subbuf);
            }
            if(flag == 4){
                AfterConvertData7 = DataConvert.ByteArray2DoubleArray_pass3Phase(subbuf);
            }
            if(flag == 6){
                AfterConvertData8 = DataConvert.ByteArray2DoubleArray_pass4Phase(subbuf);
            }

            switch (flag) {
                case Constant.PASS1_AMP:
//                    double[] result = fft.transform(AfterConvertData1, TransformType.FORWARD);
                    timeData01.setData(AfterConvertData1, Pass1AmpAvg);
//                    frequencyData01.setData(result);
                    break;
                case Constant.PASS2_AMP:
                    timeData02.setData(AfterConvertData2, Pass2AmpAvg);
//                    double[] result2 = fft.transform(AfterConvertData2, TransformType.FORWARD);
//                    frequencyData02.setData(result2);
                    break;
                case Constant.PASS3_AMP:
                    timeData03.setData(AfterConvertData3, Pass3AmpAvg);
//                    double[] result3 = fft.transform(AfterConvertData3, TransformType.FORWARD);
//                    frequencyData03.setData(result3);
                    break;
                case Constant.PASS4_AMP:
                    timeData04.setData(AfterConvertData4, Pass4AmpAvg);
//                    double[] result4 = fft.transform(AfterConvertData4, TransformType.FORWARD);
//                    frequencyData04.setData(result4);
                    break;
                case Constant.PASS1_PHASE:
                    timeDataPhase1.setData(AfterConvertData5, Pass1PhaseAvg);
//                    double[] result = fft.transform(AfterConvertData1, TransformType.FORWARD);
//                    frequencyData01.setData(result);
                    break;
                case Constant.PASS2_PHASE:
                    timeDataPhase2.setData(AfterConvertData6, Pass2PhaseAvg);
//                    double[] result2 = fft.transform(AfterConvertData2, TransformType.FORWARD);
//                    frequencyData02.setData(result2);
                    break;
                case Constant.PASS3_PHASE:
                    timeDataPhase3.setData(AfterConvertData7, Pass3PhaseAvg);
//                    double[] result3 = fft.transform(AfterConvertData3, TransformType.FORWARD);
//                    frequencyData03.setData(result3);
                    break;
                case Constant.PASS4_PHASE:
                    timeDataPhase4.setData(AfterConvertData8, Pass4PhaseAvg);
//                    double[] result4 = fft.transform(AfterConvertData4, TransformType.FORWARD);
//                    frequencyData04.setData(result4);
                    break;
            }
        }

    }

    public MainFrame() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
            jPanel1 = new JPanel();
            getContentPane().add(jPanel1, BorderLayout.CENTER);
            {
//                jButton1 = new JButton("11");
//                jPanel1.add(jButton1);
//                jButton1.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        String buttonName = e.getActionCommand();
//                        if (buttonName.equals("jButton1"))
//                            jButton1.setText("1121");
//                    }
//                });
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                getContentPane().setLayout(null);
                this.setFocusable(false);
                this.setFont(new java.awt.Font("Arial", 1, 10));
                jButton1 = new JButton();

                getContentPane().add(jButton1);

                {
                    MainMenu = new JMenuBar();
                    setJMenuBar(MainMenu);
                    {
                        jMenu1 = new JMenu();
                        MainMenu.add(jMenu1);
                        jMenu1.setText("数据接收");
                        {
                            jMenuItem1 = new JMenuItem();
                            jMenu1.add(jMenuItem1);
                            jMenuItem1.setText("FFT波形显示");
                            jMenuItem1.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    //time area thread start
                                    (new Thread(timeData01, "pass1_AMP")).start();
                                    (new Thread(timeData02, "pass2_AMP")).start();
                                    (new Thread(timeData03, "pass3_AMP")).start();
                                    (new Thread(timeData04, "pass4_AMP")).start();

                                    (new Thread(timeDataPhase1, "AMP")).start();
                                    (new Thread(timeDataPhase2, "AMP")).start();
                                    (new Thread(timeDataPhase3, "AMP")).start();
                                    (new Thread(timeDataPhase4, "AMP")).start();
                                    //frequency area thread start
//                                    (new Thread(frequencyData01, "AMP1")).start();
//                                    (new Thread(frequencyData02, "AMP2")).start();
//                                    (new Thread(frequencyData03, "AMP3")).start();
//                                    (new Thread(frequencyData04, "AMP4")).start();
                                }
                            });


                        }
                    }
//                    {
//                        jMenu2 = new JMenu();
//                        MainMenu.add(jMenu2);
//                        jMenu2.setText("数据筛选");
//                    }
//                    {
//                        jMenu3 = new JMenu();
//                        MainMenu.add(jMenu3);
//                        jMenu3.setText("数据存储");
//                    }
                }
                pack();
                setSize(600, 300);
            }
        } catch (Exception e) {
            // add your error handling code here
            e.printStackTrace();
        }
    }

}

