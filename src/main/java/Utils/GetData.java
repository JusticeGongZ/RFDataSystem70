package Utils;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 服务端监听并返回数据
 */
public class GetData implements Runnable {


    private String IPAddress;
    private String ampKey;
    private String phaseKey;
    private int localPort;
    private int remotePort;


    public GetData(String IPAddress, String ampKey, String phaseKey, int localPort, int remotePort) {
        this.IPAddress = IPAddress;
        this.ampKey = ampKey;
        this.phaseKey = phaseKey;
        this.localPort = localPort;
        this.remotePort = remotePort;
    }

    public static void StartListen(String IPAddress, String ampKey, String phaseKey, int localPort, int remotePort) throws IOException, ParseException, InterruptedException {
        Jedis j = JedisPoolTool.getJedis();
        double startTime = 0;
        double endTime = 0;
        double startAmp = 0;
        double startPhase = 0;
        double endPhase = 0;
        while (true) {
            /**
             * 接受时间
             */

            DatagramSocket socket = new DatagramSocket();

            DatagramSocket timesocket = new DatagramSocket(localPort);

            byte[] timeBuf = new byte[24];

            DatagramPacket timePacket = new DatagramPacket(timeBuf, timeBuf.length);

            timesocket.receive(timePacket);

            String ip = timePacket.getAddress().getHostAddress();
            String data = new String(timeBuf, 0, timePacket.getLength());
            timeBuf = timePacket.getData();
            byte flag = timeBuf[4];
            if (flag == 0) {

                short startYear;
                short startMouth;
                short startDate;
                short startHour;
                short startMinute;
                short startSecond;
                short endYear;
                short endMouth;
                short endDate;
                short endHour;
                short endMinute;
                short endSecond;
                byte[] stimeBufYear = new byte[2];
                byte[] stimeBufMouth = new byte[1];
                byte[] stimeBufDate = new byte[1];
                byte[] stimeBufHour = new byte[1];
                byte[] stimeBufMinute = new byte[1];
                byte[] stimeBufSecond = new byte[1];
                byte[] etimeBufYear = new byte[2];
                byte[] etimeBufMouth = new byte[1];
                byte[] etimeBufDate = new byte[1];
                byte[] etimeBufHour = new byte[1];
                byte[] etimeBufMinute = new byte[1];
                byte[] etimeBufSecond = new byte[1];
                System.arraycopy(timeBuf, 8, stimeBufYear, 0, 2);
                System.arraycopy(timeBuf, 10, stimeBufMouth, 0, 1);
                System.arraycopy(timeBuf, 11, stimeBufDate, 0, 1);
                System.arraycopy(timeBuf, 12, stimeBufHour, 0, 1);
                System.arraycopy(timeBuf, 13, stimeBufMinute, 0, 1);
                System.arraycopy(timeBuf, 14, stimeBufSecond, 0, 1);
                System.arraycopy(timeBuf, 15, etimeBufYear, 0, 2);
                System.arraycopy(timeBuf, 17, etimeBufMouth, 0, 1);
                System.arraycopy(timeBuf, 18, etimeBufDate, 0, 1);
                System.arraycopy(timeBuf, 19, etimeBufHour, 0, 1);
                System.arraycopy(timeBuf, 20, etimeBufMinute, 0, 1);
                System.arraycopy(timeBuf, 21, etimeBufSecond, 0, 1);

                startYear = DataConvert.byteToShort(stimeBufYear);
                startMouth = stimeBufMouth[0];
                startDate = stimeBufDate[0];
                startHour = stimeBufHour[0];
                startMinute = stimeBufMinute[0];
                startSecond = stimeBufSecond[0];

                endYear = DataConvert.byteToShort(etimeBufYear);
                endMouth = etimeBufMouth[0];
                endDate = etimeBufDate[0];
                endHour = etimeBufHour[0];
                endMinute = etimeBufMinute[0];
                endSecond = etimeBufSecond[0];

                String sDate = String.format("%04d%02d%02d%02d%02d%02d", startYear,
                        startMouth, startDate, startHour, startMinute, startSecond);

                String eDate = String.format("%04d%02d%02d%02d%02d%02d", endYear,
                        endMouth, endDate, endHour, endMinute, endSecond);

                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                startTime = format.parse(sDate).getTime();
                endTime = format.parse(eDate).getTime();
                System.out.println("起始时间： " + sDate + " " + "结束时间： " + eDate);
//			System.out.println(startTime + " " + endTime);

                System.out.println("收到 " + ip + " 发来的消息 ");
                timesocket.close();
//            } else if (flag == 1) {
//                /**
//                 * 接受幅度范围
//                 */
//
//                byte[] DataByte = new byte[8];
//                System.arraycopy(timeBuf, 8, DataByte, 0, 8);
//                startAmp = DataConvert.bytes2Double(DataByte);
//                System.out.println("起始幅度： " + startAmp);
//                // System.out.println("收到 " + ip + " 发来的消息：" + data);
//                timesocket.close();
//            } else {

//                /**
//                 * 接受相位范围
//                 */
//
//                byte[] DataByte = new byte[8];
//                byte[] DataByte2 = new byte[8];
//                System.arraycopy(timeBuf, 8, DataByte, 0, 8);
//                System.arraycopy(timeBuf, 16, DataByte2, 0, 8);
//                startPhase = DataConvert.bytes2Double(DataByte);
//                endPhase = DataConvert.bytes2Double(DataByte2);
//                System.out.println("起始相位：" + startPhase + " " + "结束相位： " + endPhase);
//
//                timesocket.close();
            }
            Set<String> keySet = j.keys("*1*AMP*");

            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()) {
                ampKey = (String) iterator.next();
                System.out.println(ampKey);
            }

            switch (flag) {
                case 0:
                    /**
                     * 查询指定时间点间的幅度数据和相位数据并返回数据包
                     */

                    Iterator iter = j.zrangeByScore(ampKey, startTime, endTime)
                            .iterator();
                    List list = new ArrayList();
                    byte[] sumBuf = new byte[8204];
                    int packetNum = 0;
                    int j1 = 0;
                    byte type = (byte) 0xA0;
                    while (iter.hasNext()) {
                        String str = (String) iter.next();
                        String[] s = str.split(" ");

                        list.add(s[0]);
                    }
                    int count = list.size();
                    int num_of_packet = 0;
                    if (count % 1024 == 0) {
                        num_of_packet = (count >> 10);
                    } else {
                        num_of_packet = ((count >> 1024) + 1);
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {

                        String s = (String) it.next();
                        double element = Double.parseDouble(s);
                        byte[] ampBuf = new byte[8];
                        ampBuf = DataConvert.double2Bytes(element);

                        if (j1 < 1024) {
                            System.arraycopy(ampBuf, 0, sumBuf, 12 + j1 * 8,
                                    ampBuf.length);
                            j1++;
                        } else {
                            sumBuf[0] = (byte) 0xEF;
                            sumBuf[1] = (byte) 0xEF;
                            sumBuf[2] = (byte) (1024 >> 8);
                            sumBuf[3] = (byte) (1024);
                            sumBuf[4] = type;
                            sumBuf[8] = (byte) (packetNum >> 8);
                            sumBuf[9] = (byte) packetNum;
                            sumBuf[10] = (byte) (num_of_packet >> 8);
                            sumBuf[11] = (byte) num_of_packet;
                            Thread.sleep(100);
                            DatagramPacket packet = new DatagramPacket(sumBuf,
                                    sumBuf.length,
                                    InetAddress.getByName(IPAddress), remotePort);
                            socket.send(packet);
                            j1 = 0;
                            packetNum++;
                        }
                    }
                    sumBuf[0] = (byte) 0xEF;
                    sumBuf[1] = (byte) 0xEF;
                    sumBuf[2] = (byte) (((sumBuf.length - 12) >> 3) >> 8);
                    sumBuf[3] = (byte) ((sumBuf.length - 12) >> 3);
                    sumBuf[4] = type;
                    sumBuf[8] = (byte) (packetNum >> 8);
                    sumBuf[9] = (byte) packetNum;
                    sumBuf[10] = (byte) (num_of_packet >> 8);
                    sumBuf[11] = (byte) num_of_packet;
                    Thread.sleep(100);
                    DatagramPacket packet = new DatagramPacket(sumBuf, sumBuf.length,
                            InetAddress.getByName(IPAddress), remotePort);
                    socket.send(packet);

                    Set<String> keySet2 = j.keys("*1*PHASE*");
                    Iterator iterator2 = keySet2.iterator();
                    while (iterator2.hasNext()) {
                        phaseKey = (String) iterator2.next();
                        System.out.println(phaseKey);
                    }


                    Iterator iter2 = j.zrangeByScore(phaseKey, startTime, endTime)
                            .iterator();
                    List list2 = new ArrayList();
                    byte[] sumBuf2 = new byte[8204];
                    int packetNum2 = 0;
                    int j2 = 0;
                    while (iter2.hasNext()) {
                        String str = (String) iter2.next();
                        String[] s = str.split(" ");

                        list2.add(s[0]);
                    }
                    int count2 = list2.size();
                    int num_of_packet2 = 0;
                    if (count2 % 1024 == 0) {
                        num_of_packet2 = (count2 >> 10);
                    } else {
                        num_of_packet2 = ((count2 >> 1024) + 1);
                    }
                    Iterator it2 = list2.iterator();
                    byte type2 = (byte) 0xB0;
                    while (it2.hasNext()) {

                        String s = (String) it2.next();
                        double element = Double.parseDouble(s);
                        byte[] ampBuf = new byte[8];
                        ampBuf = DataConvert.double2Bytes(element);

                        if (j2 < 1024) {
                            System.arraycopy(ampBuf, 0, sumBuf2, 12 + j2 * 8,
                                    ampBuf.length);
                            j2++;
                        } else {
                            sumBuf2[0] = (byte) 0xEF;
                            sumBuf2[1] = (byte) 0xEF;
                            sumBuf2[2] = (byte) (1024 >> 8);
                            sumBuf2[3] = (byte) (1024);
                            sumBuf2[4] = type2;
                            sumBuf2[8] = (byte) (packetNum >> 8);
                            sumBuf2[9] = (byte) packetNum;
                            sumBuf2[10] = (byte) (num_of_packet >> 8);
                            sumBuf2[11] = (byte) num_of_packet;
                            Thread.sleep(100);

                            DatagramPacket packet2 = new DatagramPacket(sumBuf2, sumBuf2.length,
                                    InetAddress.getByName(IPAddress), remotePort);
                            socket.send(packet2);
                            j2 = 0;
                            packetNum2++;
                        }
                    }
                    sumBuf2[0] = (byte) 0xEF;
                    sumBuf2[1] = (byte) 0xEF;
                    sumBuf2[2] = (byte) (((sumBuf2.length - 12) >> 3) >> 8);
                    sumBuf2[3] = (byte) ((sumBuf2.length - 12) >> 3);
                    sumBuf2[4] = type;
                    sumBuf2[8] = (byte) (packetNum2 >> 8);
                    sumBuf2[9] = (byte) packetNum2;
                    sumBuf2[10] = (byte) (num_of_packet2 >> 8);
                    sumBuf2[11] = (byte) num_of_packet2;
                    Thread.sleep(100);
                    DatagramPacket packet2 = new DatagramPacket(sumBuf2, sumBuf2.length,
                            InetAddress.getByName(IPAddress), remotePort);
                    socket.send(packet2);


//                    Iterator iter2 = j.zrangeByScore(phaseKey, startTime, endTime)
//                            .iterator();
//                    List list2 = new ArrayList();
//
//                    byte[] sumBuf2 = new byte[8204];
//
//                    int packetNum2 = 0;
//                    int j2 = 0;
//                    byte packetHead2 = (byte) 0xEFEF;
//                    byte simpleLength2 = 0;
//                    byte type2 = (byte) 0xB0;
//
//                    while (iter2.hasNext()) {
//                        String str = (String) iter2.next();
//                        String[] s = str.split(" ");
//
//                        list2.add(s[0]);
//                    }
//                    int count2 = list2.size();
//                    int num_of_packet2 = 0;
//                    if (count2 % 1024 == 0) {
//                        num_of_packet2 = (count2 >> 10);
//                    } else {
//                        num_of_packet2 = ((count2 >> 10) + 1);
//                    }
//
//                    Iterator it2 = list.iterator();
//                    while (it2.hasNext()) {
//
//                        String s = (String) it2.next();
////                        System.out.println(s);
//                        double element = Double.parseDouble(s);
//                        System.out.println(element);
//                        byte[] phaseBuf = new byte[8];
//                        phaseBuf = DataConvert.double2Bytes(element);
//
//                        if (j2 < 1024) {
//                            System.arraycopy(phaseBuf, 0, sumBuf2, 12 + j2 * 8,
//                                    phaseBuf.length);
//                            j2++;
//                        } else {
//                            sumBuf2[0] = (byte) 0xEF;
//                            sumBuf2[1] = (byte) 0xEF;
//                            sumBuf2[2] = (byte) (1024 >> 8);
//                            sumBuf2[3] = (byte) (1024);
//                            sumBuf2[4] = type2;
//                            sumBuf2[8] = (byte) (packetNum2 >> 8);
//                            sumBuf2[9] = (byte) packetNum2;
//                            sumBuf2[10] = (byte) (num_of_packet2 >> 8);
//                            sumBuf2[11] = (byte) num_of_packet2;
//                            Thread.sleep(100);
//                            DatagramPacket packet2 = new DatagramPacket(sumBuf2,
//                                    sumBuf2.length,
//                                    InetAddress.getByName(IPAddress), remotePort);
//                            socket.send(packet2);
//                            j2 = 0;
//                            packetNum2++;
//                        }
//                    }
//                    sumBuf2[0] = (byte) 0xEF;
//                    sumBuf2[1] = (byte) 0xEF;
//                    sumBuf2[2] = (byte) (((sumBuf2.length - 12) >> 3) >> 8);
//                    sumBuf2[3] = (byte) ((sumBuf2.length - 12) >> 3);
//                    sumBuf2[4] = type2;
//                    sumBuf2[8] = (byte) (packetNum2 >> 8);
//                    sumBuf2[9] = (byte) packetNum2;
//                    sumBuf2[10] = (byte) (num_of_packet2 >> 8);
//                    sumBuf2[11] = (byte) num_of_packet2;
//                    Thread.sleep(100);
//                    DatagramPacket packet2 = new DatagramPacket(sumBuf, sumBuf.length,
//                            InetAddress.getByName(IPAddress), remotePort);
//                    socket.send(packet2);

//                case 1:
//                    /**
//                     * 查询指定范围间的幅度数据并返回数据包
//                     */
//
//                    Iterator range_amp_iter = j.zrange(ampKey, 0,
//                            -1).iterator();
//                    List range_amp_list = new ArrayList();
//
//                    byte[] range_amp_sumBuf = new byte[1100];
//
//                    int range_amp_packetNum = 0;
//                    int range_amp_j1 = 0;
//                    byte range_amp_packetHead = (byte) 0xEFEF;
//                    byte range_amp_simpleLength = 0;
//                    byte range_amp_type = (byte) 0xC0;
//
//                    while (range_amp_iter.hasNext()) {
//                        String str = (String) range_amp_iter.next();
//                        String[] s = str.split(" ");
//                        double ampData = Double.parseDouble(s[0]);
//                        if (ampData > startAmp) {
//                            range_amp_list.add(str);
//                        }
//
//                    }
//                    int range_amp_count = range_amp_list.size();
//                    int range_amp_num_of_packet = 0;
//                    if (range_amp_count % 1024 == 0) {
//                        range_amp_num_of_packet = (range_amp_count >> 10);
//                    } else {
//                        range_amp_num_of_packet = ((range_amp_count >> 10) + 1);
//                    }
//
//                    Iterator range_amp_it = range_amp_list.iterator();
//                    while (range_amp_it.hasNext()) {
//
//                        String str = (String) range_amp_it.next();
//                        String[] s = str.split(" ");
////                        System.out.println(str);
//                        long time = Long.parseLong(s[1]);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(time);
//                        int year = calendar.get(Calendar.YEAR);
//                        byte[] byteYear = DataConvert.int2byte(year);
//                        byte month = (byte) (calendar.get(Calendar.MONTH) + 1);
//
//                        byte day = (byte) calendar.get(Calendar.DAY_OF_MONTH);
//                        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY);// 24小时制
//                        byte minute = (byte) calendar.get(Calendar.MINUTE);
//                        byte second = (byte) calendar.get(Calendar.SECOND);
//                        int millisecond = calendar.get(Calendar.MILLISECOND);
//                        byte[] byteMillisecond = DataConvert.int2byte(millisecond);
//                        double element = Double.parseDouble(s[1]);
//                        byte[] timeByte = new byte[9];
//                        byte[] amp_dataByte = new byte[8];
//                        System.arraycopy(byteYear, 0, timeByte, 0, 2);
//                        timeByte[2] = month;
//                        timeByte[3] = day;
//                        timeByte[4] = hour;
//                        timeByte[5] = minute;
//                        timeByte[6] = second;
//                        System.arraycopy(byteMillisecond, 0, timeByte, 7, 2);
//                        amp_dataByte = DataConvert.double2Bytes(element);
//
//                        if (range_amp_j1 < 64) {
//                            System.arraycopy(timeByte, 0, range_amp_sumBuf,
//                                    12 + range_amp_j1 * 17, 9);
//                            System.arraycopy(amp_dataByte, 0, range_amp_sumBuf,
//                                    12 + range_amp_j1 * 17 + 9, 8);
//                            range_amp_j1++;
//                        } else {
//                            range_amp_sumBuf[0] = (byte) 0xEF;
//                            range_amp_sumBuf[1] = (byte) 0xEF;
//                            range_amp_sumBuf[2] = (byte) (1024 >> 8);
//                            range_amp_sumBuf[3] = (byte) (1024);
//                            range_amp_sumBuf[4] = range_amp_type;
//                            range_amp_sumBuf[8] = (byte) (range_amp_packetNum >> 8);
//                            range_amp_sumBuf[9] = (byte) range_amp_packetNum;
//                            range_amp_sumBuf[10] = (byte) (range_amp_num_of_packet >> 8);
//                            range_amp_sumBuf[11] = (byte) range_amp_num_of_packet;
//                            Thread.sleep(100);
//                            DatagramPacket packet3 = new DatagramPacket(
//                                    range_amp_sumBuf, range_amp_sumBuf.length,
//                                    InetAddress.getByName(IPAddress), remotePort);
//                            socket.send(packet3);
//                            range_amp_j1 = 0;
//                            range_amp_packetNum++;
//                        }
//                    }
//                    range_amp_sumBuf[0] = (byte) 0xEF;
//                    range_amp_sumBuf[1] = (byte) 0xEF;
//                    range_amp_sumBuf[2] = (byte) (((range_amp_sumBuf.length - 12) / 17) >> 8);
//                    range_amp_sumBuf[3] = (byte) ((range_amp_sumBuf.length - 12) / 17);
//                    range_amp_sumBuf[4] = range_amp_type;
//                    range_amp_sumBuf[8] = (byte) (range_amp_packetNum >> 8);
//                    range_amp_sumBuf[9] = (byte) range_amp_packetNum;
//                    range_amp_sumBuf[10] = (byte) (range_amp_num_of_packet >> 8);
//                    range_amp_sumBuf[11] = (byte) range_amp_num_of_packet;
//                    Thread.sleep(100);
//                    DatagramPacket packet3 = new DatagramPacket(range_amp_sumBuf,
//                            range_amp_sumBuf.length,
//                            InetAddress.getByName(IPAddress), remotePort);
//                    socket.send(packet3);
//                    break;
//                case 2:
//                    /**
//                     * 查询指定范围间的相位数据并返回数据包
//                     */
//
//                    Iterator range_phase_iter = j.zrange(phaseKey, 0,
//                            -1).iterator();
//                    List range_phase_list = new ArrayList();
//
//                    byte[] range_phase_sumBuf = new byte[1100];
//
//                    int range_phase_packetNum = 0;
//                    int range_phase_j1 = 0;
//                    byte range_phase_packetHead = (byte) 0xEFEF;
//                    byte range_phase_simpleLength = 0;
//                    byte range_phase_type = (byte) 0xC1;
//
//
//                    while (range_phase_iter.hasNext()) {
//                        String str = (String) range_phase_iter.next();
//                        String[] s = str.split(" ");
//                        double phaseData = Double.parseDouble(s[0]);
//                        if (phaseData < startPhase && phaseData > endPhase) {
//                            range_phase_list.add(str);
//                        }
//
//                    }
//
//
//                    int range_phase_count = range_phase_list.size();
//                    int range_phase_num_of_packet = 0;
//                    if (range_phase_count % 1024 == 0) {
//                        range_phase_num_of_packet = (range_phase_count >> 10);
//                    } else {
//                        range_phase_num_of_packet = ((range_phase_count >> 10) + 1);
//                    }
//
//                    Iterator range_phase_it = range_phase_list.iterator();
//                    while (range_phase_it.hasNext()) {
//
//                        String str = (String) range_phase_it.next();
//                        String[] s = str.split(" ");
//                        System.out.println(str);
//                        long time = Long.parseLong(s[1]);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(time);
//                        int year = calendar.get(Calendar.YEAR);
//                        byte[] byteYear = DataConvert.int2byte(year);
//                        byte month = (byte) (calendar.get(Calendar.MONTH) + 1);
//                        byte day = (byte) calendar.get(Calendar.DAY_OF_MONTH);
//                        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY);// 24小时制
//                        byte minute = (byte) calendar.get(Calendar.MINUTE);
//                        byte second = (byte) calendar.get(Calendar.SECOND);
//                        int millisecond = calendar.get(Calendar.MILLISECOND);
//                        byte[] byteMillisecond = DataConvert.int2byte(millisecond);
//                        double element = Double.parseDouble(s[1]);
//                        byte[] timeByte = new byte[9];
//                        byte[] phase_dataByte = new byte[8];
//                        System.arraycopy(byteYear, 0, timeByte, 0, byteYear.length);
//                        timeByte[2] = month;
//                        timeByte[3] = day;
//                        timeByte[4] = hour;
//                        timeByte[5] = minute;
//                        timeByte[6] = second;
//                        System.arraycopy(byteMillisecond, 0, timeByte, 7, 2);
//                        phase_dataByte = DataConvert.double2Bytes(element);
//
//                        if (range_phase_j1 < 64) {
//                            System.arraycopy(timeByte, 0, range_phase_sumBuf,
//                                    12 + range_phase_j1 * 17, 9);
//                            System.arraycopy(phase_dataByte, 0, range_phase_sumBuf,
//                                    12 + range_phase_j1 * 17 + 9, 8);
//                            range_phase_j1++;
//                        } else {
//                            range_phase_sumBuf[0] = (byte) 0xEF;
//                            range_phase_sumBuf[1] = (byte) 0xEF;
//                            range_phase_sumBuf[2] = (byte) (1024 >> 8);
//                            range_phase_sumBuf[3] = (byte) (1024);
//                            range_phase_sumBuf[4] = range_phase_type;
//                            range_phase_sumBuf[8] = (byte) (range_phase_packetNum >> 8);
//                            range_phase_sumBuf[9] = (byte) range_phase_packetNum;
//                            range_phase_sumBuf[10] = (byte) (range_phase_num_of_packet >> 8);
//                            range_phase_sumBuf[11] = (byte) range_phase_num_of_packet;
//
//                            Thread.sleep(100);
//                            DatagramPacket packet4 = new DatagramPacket(
//                                    range_phase_sumBuf, range_phase_sumBuf.length,
//                                    InetAddress.getByName(IPAddress), remotePort);
//                            socket.send(packet4);
//                            range_phase_j1 = 0;
//                            range_phase_packetNum++;
//                        }
//                    }
//                    range_phase_sumBuf[0] = (byte) 0xEF;
//                    range_phase_sumBuf[1] = (byte) 0xEF;
//                    range_phase_sumBuf[2] = (byte) (((range_phase_sumBuf.length - 12) / 17) >> 8);
//                    range_phase_sumBuf[3] = (byte) ((range_phase_sumBuf.length - 12) / 17);
//                    range_phase_sumBuf[4] = range_phase_type;
//                    range_phase_sumBuf[8] = (byte) (range_phase_packetNum >> 8);
//                    range_phase_sumBuf[9] = (byte) range_phase_packetNum;
//                    range_phase_sumBuf[10] = (byte) (range_phase_num_of_packet >> 8);
//                    range_phase_sumBuf[11] = (byte) range_phase_num_of_packet;
//                    Thread.sleep(100);
//                    DatagramPacket packet4 = new DatagramPacket(range_phase_sumBuf,
//                            range_phase_sumBuf.length,
//                            InetAddress.getByName(IPAddress), remotePort);
//                    socket.send(packet4);
//                    break;
            }

        }
    }

    @Override
    public void run() {
        try {
            StartListen(IPAddress, ampKey, phaseKey, localPort, remotePort);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
