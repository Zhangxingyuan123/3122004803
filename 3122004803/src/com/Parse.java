package com; /**
 * @version 1.0
 */

import com.Check;
import com.Compare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Parse {
    /**
     * 处理命令行参数
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        if(args.length!=3){
            System.out.println("分别输入源文件 查重文件 输出文件");
            return;
        }
        System.out.println("源文件："+args[0]+"  比对文件："+args[1]);
        Check origin;
        Check target;
        try {
            origin = new Check(args[0]);
            target = new Check(args[1]);
        }catch (IOException e){
            return;
        }catch (Check.ZeroFeature e){
            e.print();
            return;
        }
        //计算重复特征数
        double countSameWord= Compare.compare(origin,target);
        //取特征数最小
        double countMinWord=Math.min(origin.getCountTwoCharWord(),target.getCountTwoCharWord());
        double res=(countSameWord/countMinWord);
        try {
            System.out.println("查重率:" + output(args[2], res));
        }catch (IOException e){
            return;
        }
        System.out.println("输出文件:"+args[2]);
    }

    /**
     *
     * @param outputPath 输出路径
     * @param result 查重率
     */
    public static String output(String outputPath,double result) throws IOException {
        DecimalFormat format=new DecimalFormat("0.00");
        String data=null;
        File outputFile = new File(outputPath);
        try (FileWriter output = new FileWriter(outputFile)) {
            data = format.format(result);
            char[] chars = data.toCharArray();
            output.write(chars);
        } catch (IOException e) {
            WrongMessage.writeFileError(outputPath);
            throw e;
        }
        return data;
    }
}
