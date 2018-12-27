package Utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;

/**
 * �鲥�Ŀͻ���
 *
 * @author Bird
 */
public class MulticastReceive {


    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        InetAddress group = InetAddress.getByName("224.10.10.10");
        int port = 50000;
        double conversionFactor = 1.0 / 8192 * 180 / Math.PI;
        MulticastSocket msr = null;
        try {
            msr = new MulticastSocket(port);
            msr.joinGroup(group);
//            byte[] flagBuf = new byte[1];
            byte[] subbuf = new byte[1024];
            byte[] buffer = new byte[10002];
            byte[] temp = new byte[2];

            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            double Num = 0;
            while (true) {

                msr.receive(dp);
                buffer = dp.getData();
                System.arraycopy(buffer, 2, subbuf, 0, 1024);
//                DataConvert.ByteArray2ShortArray(subbuf);
                int flag = (int) buffer[0];
//                System.out.println(flag);
                if (flag == 1) {
                    for (int i = 0; i < subbuf.length; i += 2) {

                        System.arraycopy(subbuf, i, temp, 0, 2);
                        double num = DataConvert.byteToShort(temp) ;
                        if(num != 0){
                        	
                        	 Num = num;
                        }
//                    if (num>3){

                        System.out.println(Num);
//                    }
                    }
                }
//                }else{
//                    for (int i = 0; i < subbuf.length; i += 2) {
//
//                        System.arraycopy(subbuf, i, temp, 0, 2);
//                        short num = DataConvert.byteToShort(temp);
//                        double result = num * conversionFactor;
////                    if (num>3){
//
//                        System.out.println(result);
////                    }
//                    }
//                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (msr != null) {
                try {
                    msr.leaveGroup(group);
                    msr.close();
                } catch (Exception e2) {
                    // TODO: handle exception  
                }
            }
        }
    }


}  
