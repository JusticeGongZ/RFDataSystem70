package MainProgrem;

import Utils.DataConvert;
import Utils.DataSave;
import Utils.GetData;

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
public class DataConserveMP extends javax.swing.JFrame {
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private static double AmplimitNum = 100000;
    private static double PhaseUplimitNum = 100000;
    private static double PhaseDownlimitNum = 100000;
    static double[] AfterConvertData = new double[2048];

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DataConserveMP inst = new DataConserveMP();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });


        byte[] flagBuf = new byte[1];
        int flag = 0;
        byte[] buf = new byte[10002];
        byte[] subbuf = new byte[10000];
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
        double[] AfterConvertData1 = new double[5000];
        double[] AfterConvertData2 = new double[1024];
        double[] AfterConvertData3 = new double[1024];
        double[] AfterConvertData4 = new double[1024];
        double[] AfterConvertData5 = new double[5000];
        double[] AfterConvertData6 = new double[1024];
        double[] AfterConvertData7 = new double[1024];
        double[] AfterConvertData8 = new double[1024];

        while (true) {

            DatagramPacket packet = new DatagramPacket(buf,
                    buf.length);
            msr.receive(packet);
            buf = packet.getData();
            System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) flagBuf[0];

            System.arraycopy(buf, 2, subbuf, 0, 10000);

//            if (flag % 2 == 0) {
//                AfterConvertData = DataConvert.ByteArray2ShortArrayPhaseData(subbuf);
//
//            } else {
//                AfterConvertData = DataConvert.ByteArray2DoubleArray(subbuf);
//            }
            if (flag == 1) {

                AfterConvertData1 = DataConvert.ByteArray2DoubleArray_pass1Amp(subbuf);
                AllDataConserve.DataConserve(flag, AfterConvertData1);
//                sign = DataConvert.isOver(flag, AfterConvertData1, PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
            }
//            if (flag == 3) {
//                AfterConvertData2 = DataConvert.ByteArray2DoubleArray_pass2Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData2, PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 5) {
//                AfterConvertData3 = DataConvert.ByteArray2DoubleArray_pass3Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData3,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 7) {
//                AfterConvertData4 = DataConvert.ByteArray2DoubleArray_pass4Amp(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData4,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }
//
            if (flag == 0) {
                AfterConvertData5 = DataConvert.ByteArray2DoubleArray_pass1Phase(subbuf);
                AllDataConserve.DataConserve(flag, AfterConvertData5);
//                sign = DataConvert.isOver(flag, AfterConvertData5,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
            }
//            if (flag == 2) {
//                AfterConvertData6 = DataConvert.ByteArray2DoubleArray_pass2Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData6,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 4) {
//                AfterConvertData7 = DataConvert.ByteArray2DoubleArray_pass3Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData7,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }
//            if (flag == 6) {
//                AfterConvertData8 = DataConvert.ByteArray2DoubleArray_pass4Phase(subbuf);
//                sign = DataConvert.isOver(flag, AfterConvertData8,  PhaseUplimitNum,PhaseDownlimitNum, AmplimitNum);
//            }


//            if (sign == true) {
//                isSave = true;
//                end = System.currentTimeMillis() + 60 * 1000; //  seconds * 1000 ms/sec
//            }
//            if (System.currentTimeMillis() >= end){
//                isSave =false;
//            }
//            if (isSave == true) {
//                AllDataConserve.DataConserve(flag, AfterConvertData);
//            }
        }
    }

    public DataConserveMP() {
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
                    jLabel1.setText("幅度下限值输入");
                    jLabel1.setBounds(40, 80, 200, 20);
                }
                {
                    jLabel2 = new JLabel();
                    getContentPane().add(jLabel2);
                    jLabel2.setText("相位上限值输入");
                    jLabel2.setBounds(40, 160, 200, 20);
                }
                {
                    jLabel3 = new JLabel();
                    getContentPane().add(jLabel3);
                    jLabel3.setText("相位下限值输入");
                    jLabel3.setBounds(40, 240, 200, 20);
                }
                getContentPane().add(getJTextField1());
                getContentPane().add(getJTextField2());
                getContentPane().add(getJTextField3());
                pack();
                setSize(600, 400);
            }
        } catch (Exception e) {
            // add your error handling code here
            e.printStackTrace();
        }
    }


//    private JButton saveData() {
//
//        JFrame frame = new JFrame("鐢靛帇娉㈠舰");
//        if (jButton6 == null) {
//            jButton6 = new JButton();
//            jButton6.setText("鐢靛帇娉㈠舰");
//            jButton6.setBounds(764, 616, 74, 27);
//            jButton6.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    鐐瑰嚮ActionPerformed(evt);
//                    JFrame frame1 = new JFrame("鐢靛帇娉㈠舰");
//
//                    frame1.add(new PieChart().getChartPanel());
//
//                    frame1.setVisible(true);
//                    frame1.setSize(400, 400);
//                }
//            });
//        }
//
//        return jButton6;
//    }

    //AMP limlit num enter
    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
//            String str = jTextField1.getText();
//            System.out.println(str);
            jTextField1.setBounds(150, 160, 100, 30);
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
            jTextField2.setBounds(150, 77, 100, 30);
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
            jTextField3.setBounds(150, 77, 100, 30);
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


}
