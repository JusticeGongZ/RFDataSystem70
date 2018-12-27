package Utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 广播模拟数据发送工具
 * @author gz
 *
 */
public class MulticastSender {

    public static void server() throws Exception{
        long end = 0;
        InetAddress group = InetAddress.getByName("224.10.10.10");
        int port = 50000;
        MulticastSocket mss = null;
        try {
            mss = new MulticastSocket(port);
            mss.joinGroup(group);

            byte[] buffer = new byte[10002];
            int[] nums = {0,1,2,3,4,5,6,7};
//            int[] nums2 = {10,100,1000,10000};
            short num = 0;
            byte[] temp = new byte[2];
            byte[] subbuf = new byte[2];
//            end = System.currentTimeMillis() + 30 * 1000;
            int count = 0;
            while(true){

                int randomFlag = (int) (Math.random() * nums.length);
                buffer[0] = (byte) randomFlag;
                for(int i=2; i<buffer.length; i+=2){
                    num = (short) ( Math.random() * 100);
//                    if (System.currentTimeMillis() >= end){
//                        num = 5000;
//                        count++;
//                        if (count>5){
//                           num = 1;
////                           break;
//                        }
//
//                    }
//                    System.out.println(num);
                    temp =  DataConvert.shortToByte(num);
//                    for(int j=0; j<temp.length;j++){
//                        System.out.println(temp[j]);
//                    }
                    System.arraycopy(temp,0,buffer,i,temp.length);

                }
//                for(int i =2; i<buffer.length;i+=2){
//
//                    System.arraycopy(buffer, i, subbuf, 0, 2);
//                    System.out.println(DataConvert.byteToShort(subbuf));
//                }
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length,group,port);
                mss.send(dp);
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(mss!=null){
                    mss.leaveGroup(group);
                    mss.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
    }

    public static void main(String[] args) throws Exception {
        server();
    }
}  
