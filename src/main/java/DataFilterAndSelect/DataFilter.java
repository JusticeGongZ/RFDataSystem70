package DataFilterAndSelect;


import DataFilterAndSelect.util.AverageDataCalculate;
import DataFilterAndSelect.util.DataConvert;
import DataFilterAndSelect.util.GetData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * conserve data main program
 */
public class DataFilter extends JFrame {
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JButton jButton5;
    private static double AmplimitNumPass1 = Double.POSITIVE_INFINITY;
    private static double AmplimitNumPass2 = Double.POSITIVE_INFINITY;
    private static double AmplimitNumPass3 = Double.POSITIVE_INFINITY;
    private static double AmplimitNumPass4 = Double.POSITIVE_INFINITY;
    private static double AmplimitNum = Double.POSITIVE_INFINITY;
    private static double PhaseUplimitNum = Double.POSITIVE_INFINITY;
    private static double PhaseDownlimitNum = Double.POSITIVE_INFINITY;
    private static double avgAmp = 0;


    static double[] AfterConvertDataAmp = new double[4096];
    static double[] AfterConvertDataPhase = new double[2048];
    static double AmpAvg;
    static double PhaseAvg;

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DataFilter inst = new DataFilter();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });


        byte[] flagBuf = new byte[1];
        int flag = 0;
        byte[] buf = new byte[10002];
        byte[] subbuf = new byte[8192];
        byte[] tempbuf = new byte[2];
        /**
         * 数据查询线程，分别监听不同的端口号
         */
        GetData dataSelect_pass1 = new GetData("192.168.8.182", "*1*AMP*", "*1*PHASE*", 50001, 60001);
        (new Thread(dataSelect_pass1, "pass1_select")).start();
        GetData dataSelect_pass2 = new GetData("192.168.8.182", "*2*AMP*", "*2*PHASE*", 50002, 60002);
        (new Thread(dataSelect_pass2, "pass2_select")).start();
        GetData dataSelect_pass3 = new GetData("192.168.8.182", "*3*AMP*", "*3*PHASE*", 50003, 60003);
        (new Thread(dataSelect_pass3, "pass3_select")).start();
        GetData dataSelect_pass4 = new GetData("192.168.8.182", "*4*AMP*", "*4*PHASE*", 50004, 60004);
        (new Thread(dataSelect_pass4, "pass4_select")).start();
        InetAddress group = InetAddress.getByName("224.10.10.10");
        int port = 50000;

        MulticastSocket msr = null;
        msr = new MulticastSocket(port);
        msr.joinGroup(group);
        long end = 0;
        boolean isSave = false;
        boolean sign = false;
        double[] AmpDataPass1 = new double[4096];
        double[] AfterConvertData2 = new double[4096];
        double[] AfterConvertData3 = new double[4096];
        double[] AfterConvertData4 = new double[4096];
        double[] AfterConvertData5 = new double[4096];
        double[] AfterConvertData6 = new double[4096];
        double[] AfterConvertData7 = new double[4096];
        double[] AfterConvertData8 = new double[4096];
        double[] pass1AmpAvg = new double[512];
        double[] pass1PhaseAvg = new double[512];

        // get avg
        DatagramPacket packet = new DatagramPacket(buf,
                buf.length);
        int count = 5;
        while(count > 0){

            msr.receive(packet);
            buf = packet.getData();
//        System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) buf[0];
            if(flag == 0){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                pass1PhaseAvg = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
//            AverageDataCalculate.AmpAvgData(pass1PhaseAvg);
                PhaseAvg = AverageDataCalculate.PhaseAvgData(pass1PhaseAvg);
                System.out.println(PhaseAvg);
            }
            if(flag == 1){
                System.arraycopy(buf, 2, subbuf, 0, 1024);
                pass1AmpAvg = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
//            AverageDataCalculate.AmpAvgData(pass1AmpAvg);
                AmpAvg = AverageDataCalculate.AmpAvgData(pass1AmpAvg);
                System.out.println(AmpAvg);
            }
            count--;
        }


        while (true) {

            msr.receive(packet);
            buf = packet.getData();
            System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) flagBuf[0];

            System.arraycopy(buf, 2, subbuf, 0, 8192);
//

            if (flag == 1) {

                DataConvert.isOverLimitAmpPass1("pass1_Amp",subbuf, AmplimitNum);
//                avgAmp = AverageDataCalculate.AmpAvgData(AmpDataPass1);
//                sign = DataConvert.isOver(flag, AmpDataPass1, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
            }

//            if (flag == 3) {
//                AfterConvertData2 = DataConvert.ByteArray2DoubleArray_pass2Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData2, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 5) {
//                AfterConvertData3 = DataConvert.ByteArray2DoubleArray_pass3Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData3, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 7) {
//                AfterConvertData4 = DataConvert.ByteArray2DoubleArray_pass4Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData4, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//
            if (flag == 0) {
                try {
                    DataConvert.isOverLimitPhasePass1("pass1_Phase",subbuf, PhaseUplimitNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                sign = DataConvert.isOver(flag, AfterConvertData5, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
            }
//            if (flag == 2) {
//                AfterConvertData6 = DataConvert.ByteArray2DoubleArray_pass2Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData6, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 4) {
//                AfterConvertData7 = DataConvert.ByteArray2DoubleArray_pass3Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData7, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 6) {
//                AfterConvertData8 = DataConvert.ByteArray2DoubleArray_pass4Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData8, PhaseUplimitNum, PhaseDownlimitNum, AmplimitNum);
//            }
//            sign = DataConvert.isOver(flag, AfterConvertData8,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);

//            if (sign == true) {
//                isSave = true;
//                end = System.currentTimeMillis() + 60 * 1000; //  seconds * 1000 ms/sec
//            }
//            if (System.currentTimeMillis() >= end) {
//                isSave = false;
//            }
//            if (isSave == true) {
//                AllDataConserve.DataConserve(flag, AfterConvertDataAmp);
//            }
        }
    }

    public DataFilter() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
//            getContentPane().add(getJButton1(), BorderLayout.CENTER);
            {
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                getContentPane().setLayout(null);
                this.setFocusable(false);
                this.setFont(new java.awt.Font("Arial", 1, 10));
                {

                    jLabel1 = new JLabel();
                    getContentPane().add(jLabel1);
                    jLabel1.setText("幅度门限值输入");
                    jLabel1.setBounds(40, 80, 200, 20);
                }
//                {
//
//                    jLabel1 = new JLabel();
//                    getContentPane().add(jLabel1);
//                    jLabel1.setText("通道2幅度下限值输入");
//                    jLabel1.setBounds(50, 80, 200, 20);
//                }
//                {
//
//                    jLabel1 = new JLabel();
//                    getContentPane().add(jLabel1);
//                    jLabel1.setText("通道3幅度下限值输入");
//                    jLabel1.setBounds(60, 80, 200, 20);
//                }
//                {
//
//                    jLabel1 = new JLabel();
//                    getContentPane().add(jLabel1);
//                    jLabel1.setText("通道4幅度下限值输入");
//                    jLabel1.setBounds(70, 80, 200, 20);
//                }


                {
                    jLabel2 = new JLabel();
                    getContentPane().add(jLabel2);
                    jLabel2.setText("相位门限值输入");
                    jLabel2.setBounds(40, 160, 200, 20);
                }
//                {
//                    jLabel3 = new JLabel();
//                    getContentPane().add(jLabel3);
//                    jLabel3.setText("相位下限值输入");
//                    jLabel3.setBounds(40, 240, 200, 20);
//                }
                getContentPane().add(getJTextField1());
                getContentPane().add(getJTextField2());
//                getContentPane().add(getJTextField3());
//                getContentPane().add(getJTextField4());
//                getContentPane().add(getJButton5());
                pack();
                setSize(600, 400);
            }
        } catch (Exception e) {
            // add your error handling code here
            e.printStackTrace();
        }
    }


//    private JButton getJButton5() {
//        if (jButton5 == null) {
//            jButton5 = new JButton();
//            jButton5.setText("获取幅度平均值");
//            jButton5.setBounds(100, 302, 150, 27);
//            jButton5.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    JFrame frame1 = new JFrame("平均值");
//                    frame1.setVisible(true);
//                    frame1.setSize(200, 400);
//                    JTextField JTextFieldAmp = new JTextField();
//                    JLabel jLabel1 = new JLabel();
//                    getContentPane().add(jLabel1);
//                    jLabel1.setText("平均值");
//                    jLabel1.setBounds(40, 80, 200, 20);
//                    JTextFieldAmp.setBounds(40, 160, 100, 30);
//                    JTextFieldAmp.setText(avgAmp + "");
//                    frame1.add(jLabel1);
//                    frame1.add(JTextFieldAmp);
//                }
//            });
//        }
//        return jButton5;
//    }

    //AMP limlit num enter
    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
//            String str = jTextField1.getText();
//            System.out.println(str);
            jTextField1.setBounds(150, 77, 100, 30);
            jTextField1.addActionListener(new ActionListener() { //鐩戝惉鍣?
                public void actionPerformed(ActionEvent e) {
                    double initialValue = Double.parseDouble(jTextField1.getText());
                    AmplimitNum = initialValue;
                    System.out.println(AmplimitNum);
                }
            });
        }
        return jTextField1;
    }

    //Phase limlit num enter
    private JTextField getJTextField2() {
        if (jTextField2 == null) {
            jTextField2 = new JTextField();
            jTextField2.setBounds(150, 160, 100, 30);
            jTextField2.addActionListener(new ActionListener() { //鐩戝惉鍣?
                public void actionPerformed(ActionEvent e) {
                  double initialValue = Double.parseDouble(jTextField2.getText());
                    PhaseUplimitNum = initialValue;
                    System.out.println(PhaseUplimitNum);
                }
            });
        }
        return jTextField2;
    }

    //Phase limlit num enter
    private JTextField getJTextField3() {
        if (jTextField3 == null) {
            jTextField3 = new JTextField();
            jTextField3.setBounds(150, 240, 100, 30);
            jTextField3.addActionListener(new ActionListener() { //鐩戝惉鍣?
                public void actionPerformed(ActionEvent e) {
                    double initialValue = Double.parseDouble(jTextField3.getText());
                    PhaseDownlimitNum = initialValue;
                    System.out.println(PhaseDownlimitNum);
                }
            });
        }
        return jTextField3;
    }

//    private JTextField getJTextField4() {
//        if (jTextField4 == null) {
//            jTextField4 = new JTextField();
//            jTextField4.setBounds(250, 240, 100, 30);
//            jTextField4.addActionListener(new ActionListener() { //鐩戝惉鍣?
//                public void actionPerformed(ActionEvent e) {
//                   jTextField3.setText();
//                }
//            });
//        }
//        return jTextField3;
//    }


}
