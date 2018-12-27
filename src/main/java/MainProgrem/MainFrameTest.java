//package MainProgrem;
//
//import RealTimeDisplay.TimeAndFFTDisplayProcess.*;
//import Utils.DataConvert;
//import Utils.FFTCalculate;
//import org.apache.commons.math3.transform.DftNormalization;
//import org.apache.commons.math3.transform.TransformType;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.InetAddress;
//import java.net.MulticastSocket;
//
//
//@SuppressWarnings("serial")
//public class MainFrameTest extends javax.swing.JFrame {
//    private JMenuBar MainMenu;
//    private JMenu jMenu1;
//    private JMenu jMenu2;
//    private JMenu jMenu3;
//    private JButton jButton1;
//    private JPanel jPanel1;
//    //二级菜单
//    private JMenuItem jMenuItem1;
//    static TimeData01 timeData01 = new TimeData01();
//    static TimeData02 timeData02 = new TimeData02();
//    static TimeData03 timeData03 = new TimeData03();
//    static TimeData04 timeData04 = new TimeData04();
//    static FrequencyData01 frequencyData01 = new FrequencyData01();
//    static FrequencyData02 frequencyData02 = new FrequencyData02();
//    static FrequencyData03 frequencyData03 = new FrequencyData03();
//    static FrequencyData04 frequencyData04 = new FrequencyData04();
////    static double limitNum;
////    static String path;
//
//    /**
//     * 数据实时显示主程序窗口
//     * @author gz
//     * @param args
//     * @throws IOException
//     */
//    public static void main(String[] args) throws IOException {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                MainFrameTest inst = new MainFrameTest();
//                inst.setLocationRelativeTo(null);
//                inst.setVisible(true);
//                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            }
//        });
//        byte[] flagBuf = new byte[1];
//        int flag = 0;
//        byte[] buf = new byte[10002];
//        byte[] subbuf = new byte[10000];
//        byte[] tempbuf = new byte[2];
//        double[] AfterConvertData = new double[4096];
//        double conversionFactor = 1.0 / 8192 * 180 / Math.PI;
//        InetAddress group = InetAddress.getByName("224.10.10.10");
//        int port = 50000;
//        MulticastSocket msr = null;
//        msr = new MulticastSocket(port);
//        msr.joinGroup(group);
//
//        DatagramPacket packet = new DatagramPacket(buf,
//                buf.length);
//
//
//        FFTCalculate fft = new FFTCalculate(DftNormalization.STANDARD);
//        while (true) {
//
//            //UDP协议数据接收
//            msr.receive(packet);
//            buf = packet.getData();
//            System.arraycopy(buf, 0, flagBuf, 0, 1);
//            flag = (int) flagBuf[0];
//            System.arraycopy(buf, 2, subbuf, 0, 8192 );
//            for (int i=2; i<8192; i+=2){
//                System.arraycopy(subbuf, i, tempbuf, 0, 2);
////                System.out.println(DataConvert.byteToShort(tempbuf));
//                if (flag % 2 != 0) {
////                    if(DataConvert.byteToShort(tempbuf) > 2){
//
//                        //解析并需要更新的数据
//                        AfterConvertData[i/2-1] = DataConvert.byteToShort(tempbuf);
//
////                        System.out.println(AfterConvertData[i/2-1]);
////                    }
//
////                }else{
////                    AfterConvertData[i/2-1] = DataConvert.byteToShort(tempbuf)*conversionFactor;
//                }
//            }
//            switch (flag) {
//                case 1:
//                    timeData01.setData(AfterConvertData);
//                    double[] result = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData01.setData(result);
//                    break;
//                case 3:
//                    timeData02.setData(AfterConvertData);
//                    double[] result2 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData02.setData(result2);
//                    break;
//                case 5:
//                    timeData03.setData(AfterConvertData);
//                    double[] result3 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData03.setData(result3);
//                    break;
//                case 7:
//                    timeData04.setData(AfterConvertData);
//                    double[] result4 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData04.setData(result4);
//                    break;
//            }
//        }
//
//    }
//
//    public MainFrameTest() {
//        super();
//        initGUI();
//    }
//
//    private void initGUI() {
//        try {
//            jPanel1 = new JPanel();
//            getContentPane().add(jPanel1, BorderLayout.CENTER);
//            {
////                jButton1 = new JButton("11");
////                jPanel1.add(jButton1);
////                jButton1.addActionListener(new ActionListener() {
////                    public void actionPerformed(ActionEvent e) {
////                        String buttonName = e.getActionCommand();
////                        if (buttonName.equals("jButton1"))
////                            jButton1.setText("1121");
////                    }
////                });
//                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//                getContentPane().setLayout(null);
//                this.setFocusable(false);
//                this.setFont(new java.awt.Font("Arial", 1, 10));
//                jButton1 = new JButton();
//
//                getContentPane().add(jButton1);
//
//                {
//                    MainMenu = new JMenuBar();
//                    setJMenuBar(MainMenu);
//                    {
//                        //电压菜单栏
//                        jMenu1 = new JMenu();
//                        MainMenu.add(jMenu1);
//                        jMenu1.setText("数据接收");
//                        {
//                            jMenuItem1 = new JMenuItem();
//                            jMenu1.add(jMenuItem1);
//                            jMenuItem1.setText("FFT波形显示");
//                            jMenuItem1.addActionListener(new ActionListener() {
//                                public void actionPerformed(ActionEvent evt) {
//                                    //启动时域显示线程
//                                    (new Thread(timeData01, "pass1_AMP")).start();
//                                    (new Thread(timeData02, "pass2_AMP")).start();
//                                    (new Thread(timeData03, "pass3_AMP")).start();
//                                    (new Thread(timeData04, "pass4_AMP")).start();
//                                    //启动频域显示线程
//                                    (new Thread(frequencyData01, "AMP")).start();
//                                    (new Thread(frequencyData02, "AMP")).start();
//                                    (new Thread(frequencyData03, "AMP")).start();
//                                    (new Thread(frequencyData04, "AMP")).start();
//                                }
//                            });
//
//
//                        }
//                    }
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
//                }
//                pack();
//                setSize(600, 300);
//            }
//        } catch (Exception e) {
//            // add your error handling code here
//            e.printStackTrace();
//        }
//    }
//
//}
//
