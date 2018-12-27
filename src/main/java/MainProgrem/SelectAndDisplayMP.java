//package MainProgrem;
//
//import RealTimeDisplay.TimeAndFFTDisplayProcess.*;
//import Utils.DataConvert;
//import Utils.FFTCalculate;
//import Utils.GetData;
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
//public class SelectAndDisplayMP {
//
//
//    public static void main(String[] args) throws IOException {
//
//
//        byte[] flagBuf = new byte[1];
//        int flag = 0;
//        byte[] buf = new byte[10002];
//        byte[] subbuf = new byte[8192];
//
//        InetAddress group = InetAddress.getByName("224.10.10.10");
//        int port = 50000;
//        MulticastSocket msr = null;
//        msr = new MulticastSocket(port);
//        msr.joinGroup(group);
//        final JTextField jTextField = new JTextField("输入值");
//        JFrame jFrame = new JFrame("操作界面");
//        jFrame.setVisible(true);
//        jFrame.setSize(600, 400);//窗体大小
//        jFrame.setLayout(new BorderLayout()); //边界布局
//        jFrame.add(BorderLayout.NORTH, jTextField);//文本框边界顶部放置
//
//        jTextField.addActionListener(new ActionListener() { //监听器
//            public void actionPerformed(ActionEvent e) {
//                double initialValue = Double.parseDouble(jTextField.getText());
//                System.out.println(initialValue);
//            }
//        });
//        //启动时域显示线程
//        TimeData01 timeData01 = new TimeData01();
//        (new Thread(timeData01, "pass1_PHASE")).start();
//        TimeData02 timeData02 = new TimeData02();
//        (new Thread(timeData02, "pass2_PHASE")).start();
//        TimeData03 timeData03 = new TimeData03();
//        (new Thread(timeData03, "pass3_PHASE")).start();
//        TimeData04 timeData04 = new TimeData04();
//        (new Thread(timeData04, "pass4_PHASE")).start();
//        //启动频域显示线程
//        FrequencyData01 frequencyData01 = new FrequencyData01();
//        (new Thread(frequencyData01, "Phase")).start();
//        FrequencyData02 frequencyData02 = new FrequencyData02();
//        (new Thread(frequencyData02, "Phase")).start();
//        FrequencyData03 frequencyData03 = new FrequencyData03();
//        (new Thread(frequencyData03, "Phase")).start();
//        FrequencyData04 frequencyData04 = new FrequencyData04();
//        (new Thread(frequencyData04, "Phase")).start();
//
//        /**
//         * 数据查询线程，分别监听不同的端口号
//         */
//        GetData dataSelect_pass1 = new GetData("192.168.1.101", "*1*AMP*", "*1*PHASE*", 50001, 60001);
//        (new Thread(dataSelect_pass1, "pass1_select")).start();
//        GetData dataSelect_pass2 = new GetData("192.168.1.101", "*2*AMP*", "*2*PHASE*", 50002, 60002);
//        (new Thread(dataSelect_pass2, "pass2_select")).start();
//        GetData dataSelect_pass3 = new GetData("192.168.1.101", "*3*AMP*", "*3*PHASE*", 50003, 60003);
//        (new Thread(dataSelect_pass3, "pass2_select")).start();
//        GetData dataSelect_pass4 = new GetData("192.168.1.101", "*4*AMP*", "*4*PHASE*", 50004, 60004);
//        (new Thread(dataSelect_pass4, "pass2_select")).start();
//        //创建傅里叶方法实例
//        FFTCalculate fft = new FFTCalculate(DftNormalization.STANDARD);
//        DatagramPacket packet = new DatagramPacket(buf,
//                buf.length);
//
//        while (true) {
//
//            //UDP协议数据接收
//            msr.receive(packet);
//            buf = packet.getData();
//            System.arraycopy(buf, 0, flagBuf, 0, 1);
//            flag = (int) flagBuf[0];
//            System.arraycopy(buf, 2, subbuf, 0, buf.length - 1810);
//            double[] AfterConvertData = new double[4096];
//            //解析并需要更新的数据
////            AfterConvertData = DataConvert.ByteArray2ShortArrayPhaseData(subbuf);
//            switch (flag) {
//                case 0:
//                    timeData01.setData(AfterConvertData);
//                    double[] result = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData01.setData(result);
//                    break;
//                case 2:
//                    timeData02.setData(AfterConvertData);
//                    double[] result2 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData02.setData(result2);
//                    break;
//                case 4:
//                    timeData03.setData(AfterConvertData);
//                    double[] result3 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData03.setData(result3);
//                    break;
//                case 6:
//                    timeData04.setData(AfterConvertData);
//                    double[] result4 = fft.transform(AfterConvertData, TransformType.FORWARD);
//                    frequencyData04.setData(result4);
//                    break;
//            }
//        }
//
//
//    }
//}
