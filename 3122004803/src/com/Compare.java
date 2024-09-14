package com; /**
 * @version 1.0
 */


import com.Check;

import java.util.SortedSet;

public class Compare {
    /**
     * 比较2篇文章的特征集合，并计算有多少个一样的特征，即可认为2篇文章有多少个双字词一样。
     * @param originArticle 源文章
     * @param targetArticle 目标文章
     * @return 相同特征个数
     */
    public static int compare(Check originArticle, Check targetArticle){
        //相同特征个数
        int countSameWord=0;
        //获取源文章和目标文章的特征
        SortedSet<Integer> origin=originArticle.getFeature();
        SortedSet<Integer> target=targetArticle.getFeature();

        //采用双指针法，计算2个整数集合共同元素的个数

        //orgin文章指针
        int originHashNow=origin.first();
        //target文章指针
        int targetHashNow=target.first();
        while(origin.size()!=0&&target.size()!=0){
            if(originHashNow==targetHashNow) {
                //发现有相同的特征，相同双字词数+1
                //origin和target指针同时后移
                countSameWord += 1;
                origin.remove(originHashNow);
                target.remove(targetHashNow);
                if(origin.size()!=0)originHashNow=origin.first();
                if(target.size()!=0)targetHashNow=target.first();
            }else if(originHashNow<targetHashNow){
                //当前origin指向的特征值小
                //需要指向下一个值更大的特征
                //origin指针后移
                origin.remove(originHashNow);
                if(origin.size()!=0)
                    originHashNow=origin.first();
                else
                    //到达末尾直接退出
                    break;
            }else{
                //当前target指向的特征值小
                //需要指向下一个值更大的特征
                //target指针后移
                target.remove(targetHashNow);
                if(target.size()!=0)
                    targetHashNow=target.first();
                else
                    //到达末尾直接退出
                    break;
            }
        }
        return countSameWord;
    }
}
