package com.example.demo.service;

/**
 * @Author:Liangll
 * @Description:
 * @Date: 19:50 2019/4/6
 */

public interface ExerciseService {

    /**
     * 随机产生操作符的下标数组
     * @param n
     * @param m
     * @return
     */
    public int[] index(int n, int m);

    /**
     * 整数生成器
     * @param min 数值的最小范围
     * @param max 数值的最大范围
     * @param maxOper 最大操作符数
     * @param is_Mul 是否包含乘除法
     * @param is_Bracket 是否包含括号
     * @return
     */
    public String[] create(int min, int max, int maxOper, int is_Mul, int is_Bracket);

}
