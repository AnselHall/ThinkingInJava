package com.ansel.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

/**
 * 目录过滤器
 */
public class FilenameFilterDemo {
    public static void main(String[] args) {
        filter(args);
    }

    private static void filter(final String[] args) {
        File path = new File(".");
        //path.getAbsolutePath() = E:\Personal\IDEA\.
        //path.getPath() = .
        System.out.println(path.getPath());
        String[] list;
        if (args.length == 0) {
            list = path.list();
        } else {
//            list = path.list(new DirFilter(args[0]));
            list = path.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return Pattern.compile(args[0]).matcher(name).matches();
                }
            });
        }

        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
        for (String dirItem : list) {
            System.out.println(dirItem);
        }
    }

    private static class DirFilter implements FilenameFilter {
        private Pattern pattern;
        public DirFilter(String regex) {
            //将给定的规则字符串，转换为Pattern对象，用于后续的比较
            pattern = Pattern.compile(regex);
        }

        //dir:文件所在的目录
        //name：文件的名称
        @Override
        public boolean accept(File dir, String name) {
            //与文件名比较，返回Matcher对象
            Matcher matcher = pattern.matcher(name);
            //返回是否匹配
            return matcher.matches();
        }

    }
}
