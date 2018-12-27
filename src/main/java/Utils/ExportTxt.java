package Utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 数据写出工具
 * @author gz
 */
public class ExportTxt {
        public static void exportTxt(String num, String path) throws IOException {
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(path,true)));
            out.write(num + "\r\n");
            out.close();
        }
}
