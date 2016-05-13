package com.ansel.io;

import utils.PPrint;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 打印一个目录下的目录名和文件名
 */
public class Directory {
    public static void main(String[] args) {

        if (args.length == 0) {
            TreeInfo walk = walk(".");

            System.out.println(walk);

        } else {
            for (String arg : args) {
                System.out.println(walk(arg));

            }
        }
    }

    /**
     *
     * @param dir
     * @param regex
     * @return
     */
    public static File[] local(File dir, final String regex) {
        return dir.listFiles(new FilenameFilter() {

            private Pattern pattern = Pattern.compile(regex);

            @Override
            public boolean accept(File dir, String pathName) {
                Matcher matcher = pattern.matcher(new File(pathName).getName());

                return matcher.matches();
            }
        });
    }

    public static File[] local(String path, String regex) {
        return local(new File(path), regex);
    }

    /**
     * 目录树实体类
     *    内部使用两个list分别存放目录名和文件名
     */
    public static class TreeInfo implements Iterable<File> {

        public List<File> files = new ArrayList<>();
        public List<File> dirs = new ArrayList<>();

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        @Override
        public String toString() {
            return "dirs:" + PPrint.pformat(dirs) + "\n\nfiles:" + PPrint.pformat(files);
        }
    }

    public static TreeInfo walk(String start, String regex) {
        return recurseDirs(new File(start), regex);
    }

    public static TreeInfo walk(File start, String regex) {
        return recurseDirs(start, regex);
    }

    public static TreeInfo walk(File start) {
        return recurseDirs(start, ".*");
    }

    public static TreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    /**
     * 递归目录(recurse:递归)<p/>
     *      将目标File下的File对象(目录或文件)存到TreeInfo中
     * @param startDir 目标目录
     * @param regex 过滤文件的字符串
     * @return
     */
    private static TreeInfo recurseDirs(File startDir, String regex) {

        TreeInfo result = new TreeInfo();
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                result.dirs.add(item);
                //递归
                result.addAll(recurseDirs(item, regex));
            } else if (item.getName().matches(regex)) {
                result.files.add(item);
            }
        }
        return result;
    }
}
