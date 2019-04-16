package com.example.demo.service.impl;

import com.example.demo.service.ExerciseService;
import com.example.demo.util.Calculator;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author:Liangll
 * @Description:
 * @Date: 19:59 2019/4/6
 */

@Service("exerciseService")
public class ExerciseServiceImpl implements ExerciseService {

    /**
     * 整数生成器
     * @param min 数值的最小范围
     * @param max 数值的最大范围
     * @param maxOper 最大操作符数
     * @param is_Mul 是否包含乘除法（值为2：不包含乘除法；值为4：包含乘除法）
     * @param is_Bracket 是否包含括号（值为0：包含括号；值为1：不包含括号）
     * @return
     */
    @Override
    public String[] create(int min, int max, int maxOper, int is_Mul, int is_Bracket){
        Random random = new Random();
        String[] opertor = {"+","-","*","÷"};
        //操作符的个数
        int operatorCount = maxOper;
        //操作数个数，比操作符多1
        int[] operatorNum = new  int[operatorCount + 1];
        //产生操作符的下标（后续为了判断操作符的重复个数）
        int[] operatorIndex = index(operatorCount, is_Mul);
        String str = new String();

        for(int i = 0;i < operatorCount + 1;i++){
            operatorNum[i] = min + random.nextInt(max - min);
        }

        switch (operatorCount){
            case 3:{
                if(is_Bracket == 0){
                    str = "(" + operatorNum[0] + opertor[operatorIndex[0]] + operatorNum[1] + ")" +opertor[operatorIndex[1]] + "(" + operatorNum[2] + opertor[operatorIndex[2]] + operatorNum[3] + ")";
                }
                else{
                    str = operatorNum[0] + opertor[operatorIndex[0]] + operatorNum[1] + opertor[operatorIndex[1]] + operatorNum[2] + opertor[operatorIndex[2]] + operatorNum[3] ;
                }
                break;
            }
            case 4:{
                if(is_Bracket == 0){
                    str = "(" + "(" + operatorNum[0] + opertor[operatorIndex[0]] + operatorNum[1] + ")" + opertor[operatorIndex[1]] + operatorNum[2] + ")" + opertor[operatorIndex[2]] + "(" + operatorNum[3] + opertor[operatorIndex[3]] + operatorNum[4] + ")";
                }
                else{
                    str = operatorNum[0] + opertor[operatorIndex[0]] + operatorNum[1] +opertor[operatorIndex[1]] + operatorNum[2] + opertor[operatorIndex[2]] + operatorNum[3] + opertor[operatorIndex[3]] + operatorNum[4] ;
                }
                break;
            }
            case 5:{
                if(is_Bracket == 0){
                    str = "(" + "(" + operatorNum[0] + opertor[operatorIndex[0]]+operatorNum[1]+")" + opertor[operatorIndex[1]] + operatorNum[2] + ")" + opertor[operatorIndex[2]]+ "(" + "(" + operatorNum[3] + opertor[operatorIndex[3]] + operatorNum[4] + ")" + opertor[operatorIndex[4]] + operatorNum[5] + ")";
                }
                else{
                    str = operatorNum[0] + opertor[operatorIndex[0]] + operatorNum[1] + opertor[operatorIndex[1]] + operatorNum[2] + opertor[operatorIndex[2]] + operatorNum[3] + opertor[operatorIndex[3]] + operatorNum[4] + opertor[operatorIndex[4]] + operatorNum[5] ;
                }
                break;
            }
        }
//        str += "=";
        String str1 = str + "=";
        Calculator calculator = new Calculator();
        int answer = calculator.NBL(str1);

        String[] strArr = new String[2];
//        String finalEx;
        //判断式子是否符合要求，凡是返回负数的就是不合格的
        if(answer > 0 && answer <= 100){

//            finalEx = str + answer;
            strArr[0] = str;
            strArr[1] = String.valueOf(answer);
        }else{
            return create(min, max, maxOper, is_Mul, is_Bracket);
        }
//        return finalEx;
        return strArr;
    }

    /**
     * 随机产生操作符的下标数组
     * @param n
     * @param m
     * @return
     */
    @Override
    public int[] index(int n, int m){
        Random random = new Random();
        int similar = 0;
        int[] a = new int[n];
        for(int j = 0;j < n;j++){
            a[j] = random.nextInt(m);
        }
        for(int j = 1;j < n;j++){
            if(a[0] == a[j]){
                similar++;
            }
        }
        //保证一个式子里至少有2个不同的操作符
        if(similar == n-1){
            while(a[n - 1] == a[0]){
                a[n - 1] = random.nextInt(m);
            }
        }
        return a;
    }
}
