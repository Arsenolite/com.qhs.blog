package com.qhs.blog.serviceImpl;

/**
 * Created by QHS on 2017/5/30.
 */
class fu{
    int a = 1;
    void show(){
        System.out.print("fu");
    }
}
class zi extends fu{
    int a = 2;
    void show(){
        System.out.print("zi");
    }
}


public class Test {

    public static void main(String[] args) throws InterruptedException {

        fu f = new fu();

        zi z = new zi();

        test(z).show();

        int flag = test(z).a;

        test2(z).show();

        int flag2 = test2(z).a;

        test3(z).show();

        int flag3 = test3(z).a;

        Thread.sleep(100000);
    }

    public static fu test(zi z){

        return z;
    }

    public static fu test2(fu z){

        return z;
    }
    public static zi test3(zi z){

        return z;
    }


}
