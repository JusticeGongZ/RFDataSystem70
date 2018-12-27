package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * time convert util
 * @author gz
 */
public class DateUtil {

    //转换到年
    public static String DateConvert2year(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-00-00_00:00:00");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
    //转换到月
    public static String DateConvert2mouth(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-00_00:00:00");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
    //转换到日
    public static String DateConvert2day(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_00:00:00");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
    //转换到小时
    public static String DateConvert2hour(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:00:00");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
    //转换到分钟
    public static String DateConvert2minute(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:00");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
    //转换到秒
    public static String DateConvert2second(Long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String standardTime = sdf.format(new Date(date));
        return standardTime;
    }
}
