package com.ansel.io;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 2016/5/13.
 */
public class ProcessFiles {
    public static void main(String[] args) {
        new ProcessFiles(new Strategy() {
            @Override
            public void process(File file) {

            }
        }, "java").start(args);
    }

    interface Strategy {
        void process(File file);
    }

    private Strategy strategy;
    private String ext;

    public ProcessFiles(Strategy strategy, String ext) {
        this.strategy = strategy;
        this.ext = ext;
    }

    public void start(String[] args) {
        try {
            if (args.length == 0) {
                processDirectoryTree(new File("."));
            } else {
                for (String arg : args) {
                    File fileArg = new File(arg);
                    if (fileArg.isDirectory()) {
                        processDirectoryTree(fileArg);

                    } else {
                        if (!arg.endsWith("." + ext)) {
                            arg += "." + ext;
                        }
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processDirectoryTree(File root) throws IOException{
        for (File file : Directory.walk(root.getAbsoluteFile(), ".*\\." + ext)) {
            strategy.process(file.getCanonicalFile());
        }
    }
}
