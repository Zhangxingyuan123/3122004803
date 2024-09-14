package test;
/**
 * @version 1.0
 */

import java.io.File;
import java.util.Arrays;
import java.util.Stack;

import com.Parse;
import org.junit.Test;

public class test {
    /**
     * 测试某个目录下所有文件查重率
     */
    @Test
    public void testAllArticle() {
        String basePath = "src\\testfiles\\";
        String resPath = "src\\res.txt";
        String origin = "orig.txt";

        long startTime = System.currentTimeMillis();

        Stack<String> testFileName = getTestFileName(basePath);

        //测试每一个子文件查重率
        while (!testFileName.empty()) {
            String fileName = testFileName.pop();

            if (fileName.equals(origin)) {
                continue;
            }
            String[] args = {
                    basePath + origin,
                    basePath + fileName,
                    resPath
            };
            System.out.println("参数："+ Arrays.toString(args));
            Parse.main(args);
            System.out.println();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("消耗时间：" + new Double(endTime - startTime) / 1000 + "s");
    }
    /**
     * 测试命令行输入参数错误处理
     */
    @Test
    public void testWrongArgs(){
        String[] args={
                "wrong_origin_path",
                "wrong_target_path",
                "wrong_res_path",
        };
        System.out.println("参数："+ Arrays.toString(args));
        Parse.main(args);
    }
    /**
     * 测试命令行输入参数错误处理
     */
    @Test
    public void testShortArgs(){
        String[] args={
                "wrong_args_length",
        };
        System.out.println("参数："+ Arrays.toString(args));
        Parse.main(args);
    }

    /**
     * 仅当使用JProfiler时定义
     * 防止JVM过早退出，导致无法查看信息
     */
//    @Test
//    public void zwaitForJProfiler() {
//        try {
//            Thread.sleep(100 * 1000);
//        }catch (InterruptedException e){
//
//        }
//    }
    /**
     * 获取目录下的所有文件名
     * @param basePath 目录路径
     * @return 目录下的所有文件名
     */
    public static Stack<String> getTestFileName(String basePath){
        Stack<String> res=new Stack<String>();
        File dir = new File(basePath);
        if(dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if(file.isDirectory()){
                }else{
                    res.push(file.getName());
                }
            }
        }
        return res;
    }

}
