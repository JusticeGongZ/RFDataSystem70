import Utils.DataConvert;
import Utils.DataSave;
import Utils.FFTCalculate;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.TransformType;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


@SuppressWarnings("serial")
public class NewJFrame extends javax.swing.JFrame {
    private JMenuBar MainMenu;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenu jMenu5;
    private JMenu jMenu6;
    private JMenu jMenu7;

    private JTextField jTextField1;
    private JTextField jTextField2;

    private JTable jTable1;
    private JTable jTable2;
    private JTable jTable3;
    private JTable jTable4;

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;

    private JPanel jPanel1;

    private JLabel jLabel1;
    private JLabel jLabel2;
    //二级菜单
    private JMenuItem jMenuItem1;
//    static TimeData01 timeData01 = new TimeData01();
//    static TimeData02 timeData02 = new TimeData02();
//    static TimeData03 timeData03 = new TimeData03();
//    static TimeData04 timeData04 = new TimeData04();
//    static FrequencyData01 frequencyData01 = new FrequencyData01();
//    static FrequencyData02 frequencyData02 = new FrequencyData02();
//    static FrequencyData03 frequencyData03 = new FrequencyData03();
//    static FrequencyData04 frequencyData04 = new FrequencyData04();
//    static double limitNum;
//    static String path;
    /**
     * Auto-generated main method to display this JFrame
     */
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                NewJFrame inst = new NewJFrame();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
                inst.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        byte[] flagBuf = new byte[1];
        int flag = 0;
        byte[] buf = new byte[10002];
        byte[] subbuf = new byte[8192];

        InetAddress group = InetAddress.getByName("224.10.10.10");
        int port = 50000;
        MulticastSocket msr = null;
        msr = new MulticastSocket(port);
        msr.joinGroup(group);

        DatagramPacket packet = new DatagramPacket(buf,
                buf.length);


        FFTCalculate fft = new FFTCalculate(DftNormalization.STANDARD);
        while (true) {

            //UDP协议数据接收
            msr.receive(packet);
            buf = packet.getData();
            System.arraycopy(buf, 0, flagBuf, 0, 1);
            flag = (int) flagBuf[0];
            System.arraycopy(buf, 2, subbuf, 0, buf.length - 1810);
            double[] AfterConvertData = new double[4096];
            //解析并需要更新的数据
            AfterConvertData = DataConvert.ByteArray2DoubleArray(subbuf);
            switch (flag) {
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
            }
        }

    }

    public NewJFrame() {
        super();
        initGUI();
    }

    private void initGUI() {
        try {
//            getContentPane().add(getJButton1(), BorderLayout.CENTER);
            jPanel1 = new JPanel();
            getContentPane().add(jPanel1, BorderLayout.CENTER);
            {
                jButton1 = new JButton("11");
                jPanel1.add(jButton1);
                jButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String buttonName = e.getActionCommand();
                        if (buttonName.equals("jButton1"))
                            jButton1.setText("1121");
                    }
                });
                setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                getContentPane().setLayout(null);
                this.setFocusable(false);
                this.setFont(new java.awt.Font("Arial", 1, 10));

//                {
//                    TableModel jTable1Model = new DefaultTableModel(
//                            new String[][] { { "电压", "电流", "功率因数", "三相不平衡度" },
//                                    { "110KV", "50A", "0.7", "0.1" },
//                                    { "110KV", "50A", "0.7", "0.1" },
//                                    { "110KV", "50A", "0.7", "0.1" },
//                                    { "110KV", "50A", "0.7", "0.1" } },
//                            new String[] { "Column 1", "Column 2", "Column 3",
//                                    "Column 4" });
//                    jTable1 = new JTable();
//                    getContentPane().add(jTable1);
//                    jTable1.setBounds(30, 154, 693, 100);
//                    jTable1.setColumnSelectionAllowed(true);
//                    jTable1.setModel(jTable1Model);
//                }
                {

                    jLabel1 = new JLabel();
                    getContentPane().add(jLabel1);
                    jLabel1.setText("门限值输入");
                    jLabel1.setBounds(112, 80, 60, 20);
                }
                {
                    jLabel2 = new JLabel();
                    getContentPane().add(jLabel2);
                    jLabel2.setText("路径输入");
                    jLabel2.setBounds(465, 87, 60, 21);
                }
//                {
//                    jPanel1 = new JPanel();
//                    getContentPane().add(jPanel1);
//                    jPanel1.setBounds(203, 478, 449, 200);
//                }
                jButton1 = new JButton();

                getContentPane().add(jButton1);
//                getContentPane().add(getJButton2());
                getContentPane().add(getJButton3());
//                getContentPane().add(getJButton4());
                getContentPane().add(getJButton5());
                getContentPane().add(getJTextField1());
                getContentPane().add(getJTextField2());
//                getContentPane().add(saveData());
//                getContentPane().add(getJTable2());
//                getContentPane().add(getJTable3());
//                getContentPane().add(getJTable4());

//                jButton1.setText("保存");
//                jButton1.setBounds(112, 571, 76, 27);

                {
                    MainMenu = new JMenuBar();
                    setJMenuBar(MainMenu);
                    {
                        //电压菜单栏
                        jMenu1 = new JMenu();
                        MainMenu.add(jMenu1);
                        jMenu1.setText("数据接收");
                        {
                            jMenuItem1 = new JMenuItem();
                            jMenu1.add(jMenuItem1);
                            jMenuItem1.setText("FFT波形显示");
                            jMenuItem1.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    //启动时域显示线程
//                                    (new Thread(timeData01, "pass1_PHASE")).start();
//                                    (new Thread(timeData02, "pass2_PHASE")).start();
//                                    (new Thread(timeData03, "pass3_PHASE")).start();
//                                    (new Thread(timeData04, "pass4_PHASE")).start();
//                                    //启动频域显示线程
//                                    (new Thread(frequencyData01, "Phase")).start();
//                                    (new Thread(frequencyData02, "Phase")).start();
//                                    (new Thread(frequencyData03, "Phase")).start();
//                                    (new Thread(frequencyData04, "Phase")).start();
                                }
                            });


                        }
                    }
                    {
                        jMenu2 = new JMenu();
                        MainMenu.add(jMenu2);
                        jMenu2.setText("数据筛选");
                    }
                    {
                        jMenu3 = new JMenu();
                        MainMenu.add(jMenu3);
                        jMenu3.setText("数据存储");
                    }
//                    {
//                        jMenu4 = new JMenu();
//                        MainMenu.add(jMenu4);
//                        jMenu4.setText("功率");
//                    }
//                    {
//                        jMenu5 = new JMenu();
//                        MainMenu.add(jMenu5);
//                        jMenu5.setText("参数设置");
//                    }
//                    {
//                        jMenu6 = new JMenu();
//                        MainMenu.add(jMenu6);
//                        jMenu6.setText("三相不平衡度");
//                    }
//                    {
//                        jMenu7 = new JMenu();
//                        MainMenu.add(jMenu7);
//                        jMenu7.setText("历史数据");
//                    }
                }
                pack();
                setSize(1000, 750);
            }
        } catch (Exception e) {
            // add your error handling code here
            e.printStackTrace();
        }
    }
//
//    private JButton getJButton1() {
//        if (jButton1 == null) {
//            jButton1 = new JButton();
//            jButton1.setText("jButton1");
//            jButton1.setBounds(658, 560, 76, 27);
//        }
//        return jButton1;
//    }

//    private JButton getJButton2() {
//        if (jButton2 == null) {
//            jButton2 = new JButton();
//            jButton2.setText("打印");
//            jButton2.setBounds(658, 560, 76, 27);
//            jButton2.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    jButton3ActionPerformed(evt);
//                    JFrame frame1 = new JFrame("打印");
//                    frame1.setVisible(true);
//                    frame1.setSize(200, 400);
//                }
//            });
//        }
//        return jButton2;
//    }

    private JButton getJButton3() {
        if (jButton3 == null) {
            jButton3 = new JButton();
            jButton3.setText("A相");
            jButton3.setBounds(764, 176, 76, 27);
            jButton3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jButton3ActionPerformed(evt);
                    JFrame frame1 = new JFrame("A相波形");
                    frame1.setVisible(true);
                    frame1.setSize(200, 400);
                    while (true){

                    }
                }
            });
        }
        return jButton3;
    }

//    private JButton getJButton4() {
//        if (jButton4 == null) {
//            jButton4 = new JButton();
//            jButton4.setText("C相");
//            jButton4.setBounds(770, 441, 76, 27);
//        }
//        return jButton4;
//    }

    private JButton getJButton5() {
        if (jButton5 == null) {
            jButton5 = new JButton();
            jButton5.setText("B相");
            jButton5.setBounds(776, 302, 76, 27);
            jButton5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {



                }
            });
        }
        return jButton5;
    }

//    private JTable getJTable2() {
//        if (jTable2 == null) {
//            TableModel jTable2Model = new DefaultTableModel(new String[][] {
//                    { "One", "Two" }, { "Three", "Four" } }, new String[] {
//                    "Column 1", "Column 2" });
//            jTable2 = new JTable();
//            jTable2.setModel(jTable2Model);
//            jTable2.setColumnSelectionAllowed(true);
//            jTable2.setBounds(30, 154, 693, 100);
//        }
//        return jTable2;
//    }

//    private JButton saveData() {
//
//        JFrame frame = new JFrame("电压波形");
//        if (jButton6 == null) {
//            jButton6 = new JButton();
//            jButton6.setText("电压波形");
//            jButton6.setBounds(764, 616, 74, 27);
//            jButton6.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent evt) {
//                    点击ActionPerformed(evt);
//                    JFrame frame1 = new JFrame("电压波形");
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

//    private JTable getJTable3() {
//        if (jTable3 == null) {
//            TableModel jTable3Model = new DefaultTableModel(new String[][] {
//                    { "One", "Two" }, { "Three", "Four" } }, new String[] {
//                    "Column 1", "Column 2" });
//            jTable3 = new JTable();
//            jTable3.setModel(jTable3Model);
//            jTable3.setBounds(40, 472, 198, -91);
//        }
//        return jTable3;
//    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        System.out.println("你好");
        // TODO add your code for jButton3.actionPerformed
    }
//路径输入
    private JTextField getJTextField1() {
        if (jTextField1 == null) {
            jTextField1 = new JTextField();
//            String str = jTextField1.getText();
//            System.out.println(str);
            jTextField1.setBounds(543, 84, 62, 27);
            jTextField1.addActionListener(new ActionListener() { //监听器
                public void actionPerformed(ActionEvent e) {
                    double initialValue = Double.parseDouble(jTextField1.getText());
                    System.out.println(initialValue);
                }
            });
        }
        return jTextField1;
    }
//门限值输入
    private JTextField getJTextField2() {
        if (jTextField2 == null) {
            jTextField2 = new JTextField();
            jTextField2.setBounds(198, 77, 43, 27);
            jTextField2.addActionListener(new ActionListener() { //监听器
                public void actionPerformed(ActionEvent e) {
                    double initialValue = Double.parseDouble(jTextField2.getText());
                    System.out.println(initialValue);
                }
            });
        }
        return jTextField2;
    }

    private void 点击ActionPerformed(ActionEvent evt) {
        System.out.println("点击.actionPerformed, event=" + evt);
        // TODO add your code for 点击.actionPerformed
    }

//    private JTable getJTable4() {
//        if (jTable4 == null) {
//            TableModel jTable4Model = new DefaultTableModel(new String[][] {
//                    { "One", "Two" }, { "Three", "Four" } }, new String[] {
//                    "Column 1", "Column 2" });
//            jTable4 = new JTable();
//            jTable4.setModel(jTable4Model);
//            jTable4.setColumnSelectionAllowed(true);
//            jTable4.setBounds(52, 322, 651, 119);
//        }
//        return jTable4;
//    }

}
