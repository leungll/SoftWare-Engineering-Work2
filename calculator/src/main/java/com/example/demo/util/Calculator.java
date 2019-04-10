package com.example.demo.util;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author:liangll
 * @Description:
 * @Date: 0:05 2019/3/18
 */
public class Calculator {
    public int NBL(String s){
        //放数字
        Stack<Integer> stack1 = new Stack<>();
        //放操作符
        Stack<String> stack2 = new Stack<>();
        //存放运算符优先级
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("(", 0);
        hashmap.put("+", 1);
        hashmap.put("-", 1);
        hashmap.put("*", 2);
        hashmap.put("÷", 2);

        for (int i = 0; i < s.length();) {
            //设置可变长的字符串
            StringBuffer digit = new StringBuffer();
            //将式子字符串切割为c字符
            char c = s.charAt(i);
            //判断字符是否为10进制数字,将一个数加入digit
            while (Character.isDigit(c)) {
                digit.append(c);
                i++;
                c = s.charAt(i);
            }
            //当前digit里面已经无数字，即当前处理符号
            if (digit.length() == 0){
                switch (c) {
                    case '(': {
                        stack2.push(String.valueOf(c));
                        break;
                    }
                    //遇到右括号了计算，因为（的优先级最高
                    case ')': {
                        String stmp = stack2.pop();
                        while (!stack2.isEmpty() && !stmp.equals("(")) {
                            int a = stack1.pop();
                            int b = stack1.pop();
                            int sresulat = calculate(b, a, stmp);
                            if(sresulat<0)
                                return  -1;
                            stack1.push(sresulat);
                            //符号指向下一个计算符号
                            stmp = stack2.pop();
                        }
                        break;
                    }
                    case '=': {
                        String stmp;
                        while (!stack2.isEmpty()) {
                            stmp = stack2.pop();
                            int a = stack1.pop();
                            int b = stack1.pop();
                            int sresulat = calculate(b, a, stmp);
                            if(sresulat<0)
                                return  -1;
                            stack1.push(sresulat);
                        }
                        break;
                    }
                    default: {
                        String stmp;
                        while (!stack2.isEmpty()) {
                            stmp = stack2.pop();
                            //比较优先级
                            if (hashmap.get(stmp) >= hashmap.get(String.valueOf(c))) {
                                int a = stack1.pop();
                                int b = stack1.pop();
                                int sresulat =calculate (b, a, stmp);
                                if(sresulat<0)
                                    return  -1;
                                stack1.push(sresulat);
                            }
                            else {
                                stack2.push(stmp);
                                break;
                            }
                        }
                        //将符号压入符号栈
                        stack2.push(String.valueOf(c));
                        break;
                    }
                }
            }
            else {
                //处理数字
                stack1.push(Integer.valueOf(digit.toString()));
                continue;
            }
            i++;
        }
        //返回栈底得到答案
        return stack1.peek();
    }

    /**
     * 计算a stmp b的值
     * @param a
     * @param b
     * @param stmp
     * @return
     */
    private int calculate(int a, int b, String stmp) {
        //存结果
        int res = 0;
        char s = stmp.charAt(0);
        switch (s) {
            case '+': {
                res = a + b;
                break;
            }
            case '-': {
                //产生负数就不合格
                res = a - b;
                break;
            }
            case '*': {
                res = a * b;
                break;
            }
            case '÷': {
                if(b==0) {
                    return -1;
                } else if(a%b!=0) {
                    //产生小数就不合格
                    return -2;
                }
                else {
                    res = a / b;
                }
                break;
            }default:
        }
        return res;
    }
}
