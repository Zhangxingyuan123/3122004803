package com; /**
 * @version 1.0
 */

import java.io.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class Check {
    public class ZeroFeature extends Exception {
        private String pathname;

        public ZeroFeature(String initPathname) {
            pathname = initPathname;
        }

        public void print() {
            System.out.println("无法从文本中提取特征,文件路径：" + pathname);
        }

        ;
    }

    /**
     * @return 文章特征
     */
    public SortedSet<Integer> getFeature() {
        return sortedHashFeature;
    }

    /**
     * @return 获取文章中双字词个数
     */
    public int getCountTwoCharWord() {
        return countTwoCharWord;
    }

    //储存相邻汉字之间两两结合的Hash作为论文特征,并排序。
    private SortedSet<Integer> sortedHashFeature = new TreeSet<Integer>();

    //文章中双字词的数量
    private int countTwoCharWord;

    //正则表达式：匹配标点符号
    private final String splitSymbol = "[.。,，、《》”“：？]";

    /**
     * @param pathname 文章路径
     */
    public Check(String pathname) throws IOException, ZeroFeature {
        BufferedReader reader = null;
        InputStreamReader fileStream = null;
        FileInputStream file = null;
        try {
            //读取文件并且使用BufferedReader逐行分析
            file = new FileInputStream(pathname);
            fileStream = new InputStreamReader(file, "UTF-8");
            reader = new BufferedReader(fileStream);

            String line;
            //按顺序读取每一行
            while ((line = reader.readLine()) != null) {
                //使用正则表达式分割这一行，分割这一行的短句
                String[] subSentence = line.split(splitSymbol);
                //逐个短句分析
                for (String word : subSentence) {
                    //每2个汉字作为双字词
                    for (int i = 0; i < word.length() - 1; i++) {
                        //获取双字词的Hash值作为，添加到特征集合sortedHashFeature中
                        int hash = word.substring(i, i + 2).hashCode();
                        sortedHashFeature.add(hash);
                    }
                }
            }
            reader.close();
            fileStream.close();
            file.close();
        } catch (IOException e) {
            //输出错误信息
            WrongMessage.openFileError(pathname);
            throw e;
        } finally {
            //回收reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e2) {
                }
            }
            //回收fileStream
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e2) {
                }
            }
            //回收file
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e2) {
                }
            }
        }
        //文章中双字词的数量
        countTwoCharWord = sortedHashFeature.size();//双字词数
        if (countTwoCharWord == 0) {
            //没有检测到特征，抛出异常
            throw new ZeroFeature(pathname);
        }
    }
}
