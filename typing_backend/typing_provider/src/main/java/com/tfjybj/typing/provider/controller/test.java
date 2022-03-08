package com.tfjybj.typing.provider.controller;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 张艳伟
 * @Date: 2022/01/04/9:32
 * @Description:
 */
public class test {

    public static void main(String[] args){
        //工程1，直接输入两个数
//        System.out.println(4+5);
        //工程2 定义两个变量
//        int a=4,b=5;
//        System.out.println(a+b);
        //工程3，定义三个变量
//        int a=4,b=5,c=a+b;
//        System.out.println(c);
        //工程4 输入数据
//        Scanner sc =new Scanner(System.in);
//        Integer a,b,c;
//        System.out.println("请输入a的数值");
//        a=sc.nextInt();
//        System.out.println("请输入b的数值");
//        b=sc.nextInt();
//        c=a+b;
//        System.out.println("输出c="+c);
        //工程5 输入数据
        Scanner sc =new Scanner(System.in);
        Integer a,b,c;
        System.out.println("请输入a的数值");
        a=sc.nextInt();
        System.out.println("输入a=" +a);
        System.out.println("请输入b的数值");
        b=sc.nextInt();
        System.out.println("输入b=" +b);
        c=a+b;
        System.out.println("输出c="+c);

    }
}
