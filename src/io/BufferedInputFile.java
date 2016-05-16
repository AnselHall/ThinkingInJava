package io;//: io/BufferedInputFile.java

import com.ansel.Constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedInputFile {
    // Throw exceptions to console:
    public static String
    /**
     * 接收一个文件名称，打印文件中的内容
     */
    read(String filename) throws IOException {
        // Reading input by lines:
        BufferedReader in = new BufferedReader(
                new FileReader(filename));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null)
            //sb.append(s + "\n"); 与sb.append(s).append("\n");有什么区别？
            sb.append(s+"\n");
        in.close();
        return sb.toString();
    }

    public static void main(String[] args)
            throws IOException {
        System.out.print(read(Constant.FILEPATH + "/io/BufferedInputFile.java"));
    }
}
