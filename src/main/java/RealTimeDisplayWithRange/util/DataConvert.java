package RealTimeDisplayWithRange.util;

import Utils.PackageDataConserve;

import java.io.IOException;
import java.nio.DoubleBuffer;

/**
 * 数据转换工具类
 *
 * @author gz
 */
public class DataConvert {

    //合并两个byte数组
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    public static double bytes2Double(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    public static byte[] double2Bytes(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    /**
     * short到字节数组的转换.
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
            temp = temp >> 8;// 向右移8位
        }
        return b;
    }


    /**
     * 字节数组到int的转换.
     */
    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s0 <<= 24;
        s1 <<= 16;
        s2 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    public static String bytes2Hex(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < src.length; i++) { // 之所以用byte和0xff相与，是因为int是32位，与0xff相与后就舍弃前面的24位，只保留后8位
            String str = Integer.toHexString(src[i] & 0xff);
            if (str.length() < 2) { // 不足两位要补0
                stringBuilder.append(0);
            }
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    //字节数组转换成短整型数组
    static byte[] temp = new byte[2];
    static short num = 0;
    static short[] convertData = new short[5000];

    public static short[] ByteArray2ShortArray(byte[] data) {

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, temp, 0, 2);
            num = byteToShort(temp);
            convertData[i / 2] = num;
            System.out.println(convertData[i / 2]);
        }
        return convertData;
    }

    static byte[] temp_pass1Amp = new byte[2];
    static double num_pass1Amp = 0;
    static DoubleBuffer doubleBufferAmpPass1 = DoubleBuffer.allocate(10000);
    static double keyNumPass1Amp = 0;
    static  boolean signPass1Amp;


    public static void isOverLimitAmpPass1(String key, byte[] data, double limitNum) throws IOException {


        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, temp_pass1Amp, 0, 2);
            num_pass1Amp = byteToShort(temp_pass1Amp);
            if (doubleBufferAmpPass1.position() < doubleBufferAmpPass1.capacity()) {
                doubleBufferAmpPass1.put(num_pass1Amp);
            } else {
                doubleBufferAmpPass1.flip();
            }
            if (num_pass1Amp != 0 && limitNum != Double.POSITIVE_INFINITY) {

//                if (convertDataDouble[i / 2] >2){
//
//                    System.out.println(convertDataDouble[i / 2]);
//                }
                if ((num_pass1Amp - limitNum) / limitNum > 0.02) {
                    keyNumPass1Amp = num_pass1Amp;
//                    end = System.currentTimeMillis() + 5 * 1000;
                    doubleBufferAmpPass1.put(keyNumPass1Amp);
                    System.out.println(keyNumPass1Amp);
//                    position = doubleBufferPhasePass1.position();

                    System.out.println(num_pass1Amp);
                    signPass1Amp = true;

                }
            }
            if (sign == true && (doubleBufferAmpPass1.position() > doubleBufferAmpPass1.capacity() - 1000) && limitNum != Double.POSITIVE_INFINITY) {
//                        doubleBufferPhasePass1.put(num_pass1Phase);
                //  seconds * 1000 ms/sec
                PackageDataConserve.pass1AmpSave(key, doubleBufferAmpPass1, limitNum);

                sign = false;
            }
        }
    }

    static double keyNum = 0;
    static byte[] temp_pass1Phase = new byte[2];
    static double num_pass1Phase = 0;
    static DoubleBuffer doubleBufferPhasePass1 = DoubleBuffer.allocate(50000);
    static boolean sign = false;
    static boolean overExport = false;
   static PackageDataConserve packageDataConserve = new PackageDataConserve();
    public static void isOverLimitPhasePass1(String key, byte[] data, double PhaseUplimitNum) throws InterruptedException {


        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, temp_pass1Phase, 0, 2);


            num_pass1Phase = byteToShort(temp_pass1Phase) * conversionFactor; //此处已经开始有0
            if (doubleBufferPhasePass1.position() < doubleBufferPhasePass1.capacity() && num_pass1Phase != 0) {

                doubleBufferPhasePass1.put(num_pass1Phase);
            }else if(doubleBufferPhasePass1.position() >= doubleBufferPhasePass1.capacity() && num_pass1Phase != 0){
                doubleBufferPhasePass1.flip();
                doubleBufferPhasePass1.put(num_pass1Phase);
            }
            if (num_pass1Phase != 0 && PhaseUplimitNum != Double.POSITIVE_INFINITY) {

                if (Math.abs(num_pass1Phase - PhaseUplimitNum) > 2) {
                    keyNum = num_pass1Phase;
                    doubleBufferPhasePass1.put(keyNum);
                    System.out.println(keyNum);
                    sign = true;
                }
            }
            if ( sign == true && (doubleBufferPhasePass1.position() > doubleBufferPhasePass1.capacity() - 100) && PhaseUplimitNum != Double.POSITIVE_INFINITY) {
//                        doubleBufferPhasePass1.put(num_pass1Phase);
                //  seconds * 1000 ms/sec

            }
        }

        if(sign == true){
            (new Thread(packageDataConserve, "pass1_AMP")).start();
            packageDataConserve.set(key, doubleBufferPhasePass1, PhaseUplimitNum);
        }
    }

//    static byte[] tempDouble_pass1Amp = new byte[2];
//    static short numDouble_pass1Amp = 0;
//    static double[] convertDataDouble_pass1Amp = new double[4096];

    public  static synchronized double[] ByteArray2DoubleArray_pass1Amp(byte[] data){

         byte[] tempDouble_pass1Amp = new byte[2];
         short numDouble_pass1Amp = 0 ;
         double[] convertDataDouble_pass1Amp = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass1Amp, 0, 2);
            numDouble_pass1Amp = byteToShort(tempDouble_pass1Amp);
            if (numDouble_pass1Amp != 0) {
                convertDataDouble_pass1Amp[i / 2] = numDouble_pass1Amp;
//                System.out.println(numDouble_pass1Amp);
            }
        }
        return convertDataDouble_pass1Amp;
    }

//    static byte[] tempDouble = new byte[2];
//    static short numDouble = 0;
//    static double[] convertDataDouble = new double[1024];


//    static byte[] tempDouble_pass2Amp = new byte[2];
//    static short numDouble_pass2Amp = 0;
//    static double[] convertDataDouble_pass2Amp = new double[512];

    public static synchronized double[] ByteArray2DoubleArray_pass2Amp(byte[] data) throws IOException {
         byte[] tempDouble_pass2Amp = new byte[2];
         short numDouble_pass2Amp = 0;
         double[] convertDataDouble_pass2Amp = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass2Amp, 0, 2);
            numDouble_pass2Amp = byteToShort(tempDouble_pass2Amp);
            if (numDouble_pass2Amp != 0) {
                convertDataDouble_pass2Amp[i / 2] = numDouble_pass2Amp;
//                if (convertDataDouble[i / 2] >2){
//
//                System.out.println(convertDataDouble_pass2Amp[i / 2]);
//                }
//                if (limitNum != null){
//                    if(numDouble > limitNum){
//                        ExportTxt.exportTxt(numDouble + " " + DateUtil.DateConvert(System.currentTimeMillis()),path);
//                    }
//                }
            }
        }
        return convertDataDouble_pass2Amp;
    }

//    static byte[] tempDouble_pass3Amp = new byte[2];
//    static short numDouble_pass3Amp = 0;
//    static double[] convertDataDouble_pass3Amp = new double[512];

    public static synchronized double[] ByteArray2DoubleArray_pass3Amp(byte[] data){
         byte[] tempDouble_pass3Amp = new byte[2];
         short numDouble_pass3Amp = 0;
         double[] convertDataDouble_pass3Amp = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass3Amp, 0, 2);
            numDouble_pass3Amp = byteToShort(tempDouble_pass3Amp);
            if (numDouble_pass3Amp != 0) {
                convertDataDouble_pass3Amp[i / 2] = numDouble_pass3Amp;
            }
        }
        return convertDataDouble_pass3Amp;
    }



    public static synchronized double[] ByteArray2DoubleArray_pass4Amp(byte[] data){
         byte[] tempDouble_pass4Amp = new byte[2];
         short numDouble_pass4Amp = 0;
         double[] convertDataDouble_pass4Amp = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass4Amp, 0, 2);
            numDouble_pass4Amp = byteToShort(tempDouble_pass4Amp);
            if (numDouble_pass4Amp != 0) {
                convertDataDouble_pass4Amp[i / 2] = numDouble_pass4Amp;
            }
        }
        return convertDataDouble_pass4Amp;
    }


    static double conversionFactor = 1.0 / 8192 * 180 / Math.PI;



    public static  synchronized double[] ByteArray2DoubleArray_pass1Phase(byte[] data) {

         byte[] tempDouble_pass1Phase = new byte[2];
         short numDouble_pass1Phase = 0;
         double[] convertDataDouble_pass1Phase = new double[4096];
        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass1Phase, 0, 2);
            numDouble_pass1Phase = byteToShort(tempDouble_pass1Phase);
            if (numDouble_pass1Phase != 0) {
                convertDataDouble_pass1Phase[i / 2] = numDouble_pass1Phase * conversionFactor;
            }
        }
        return convertDataDouble_pass1Phase;
    }



    public static synchronized double[] ByteArray2DoubleArray_pass2Phase(byte[] data){

         byte[] tempDouble_pass2Phase = new byte[2];
         short numDouble_pass2Phase = 0;
         double[] convertDataDouble_pass2Phase = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass2Phase, 0, 2);
            numDouble_pass2Phase = byteToShort(tempDouble_pass2Phase);
            if (numDouble_pass2Phase != 0) {
                convertDataDouble_pass2Phase[i / 2] = numDouble_pass2Phase * conversionFactor;
            }
        }
        return convertDataDouble_pass2Phase;
    }



    public static synchronized double[] ByteArray2DoubleArray_pass3Phase(byte[] data){
         byte[] tempDouble_pass3Phase = new byte[2];
         short numDouble_pass3Phase = 0;
         double[] convertDataDouble_pass3Phase = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass3Phase, 0, 2);
            numDouble_pass3Phase = byteToShort(tempDouble_pass3Phase);
            if (numDouble_pass3Phase != 0) {
                convertDataDouble_pass3Phase[i / 2] = numDouble_pass3Phase * conversionFactor;
            }
        }
        return convertDataDouble_pass3Phase;
    }



    public static synchronized double[] ByteArray2DoubleArray_pass4Phase(byte[] data){
         byte[] tempDouble_pass4Phase = new byte[2];
         short numDouble_pass4Phase = 0;
         double[] convertDataDouble_pass4Phase = new double[4096];

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, tempDouble_pass4Phase, 0, 2);
            numDouble_pass4Phase = byteToShort(tempDouble_pass4Phase);
            if (numDouble_pass4Phase != 0) {
                convertDataDouble_pass4Phase[i / 2] = numDouble_pass4Phase * conversionFactor;
            }
        }
        return convertDataDouble_pass4Phase;
    }

    static byte[] temp2 = new byte[2];
    static double num2 = 0;
    static double[] convertData2 = new double[4096];

    public static double[] ByteArray2ShortArrayPhaseData(byte[] data) {

        for (int i = 0; i < data.length; i += 2) {
            System.arraycopy(data, i, temp2, 0, 2);
            num2 = (byteToShort(temp2) * conversionFactor);
            convertData2[i / 2] = (short) num2;
        }
        return convertData2;
    }

    public static boolean isOver(int flag, double[] data, double PhaseUpLimitNum, double PhaseDownLimitNum, double AmplimitNum) {
        boolean sign = false;

        if (AmplimitNum != Double.POSITIVE_INFINITY || PhaseUpLimitNum != Double.POSITIVE_INFINITY || PhaseDownLimitNum != Double.POSITIVE_INFINITY) {
            if (flag % 2 == 0) {
                for (int i = 0; i < data.length; i++) {
                    if ((data[i]) > PhaseUpLimitNum || (data[i]) < PhaseDownLimitNum) {
                        System.out.println(PhaseDownLimitNum + " " + data[i] + " " + PhaseUpLimitNum);
                        sign = true;
                    }
                }
            } else {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] < AmplimitNum) {
                        System.out.println(AmplimitNum + " " + data[i]);
                        sign = true;
                    }
                }
            }
        }
        return sign;
    }

}
