package com.example.kcdwebservice.util;

public class Test {

    public static void main (String args[]){
        double dblAmount=234.0;
        System.out.println("sets"+dblAmount);
        //dblAmount=dblAmount*10%10;
        System.out.println("sets"+dblAmount);
        if(dblAmount*10%10==0){
            System.out.println("  equal 0");}
        System.out.println("sets"+dblAmount);
        String strAmount=String.format("%.0f", dblAmount);  
        System.out.println(strAmount);
    }
}