package com.example.kcdwebservice.util;

public class Test {

    public static void main (String args[]){
        String xx="11";
        String x1=xx.substring(1,2);
        System.out.println("test:"+x1);
        
        if(x1.equals("1")){
            System.out.println("ininin");
        }

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