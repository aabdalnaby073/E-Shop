/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oopjava;

import java.util.Scanner;

/**
 *
 * @author ahmed
 */
public class User {
 public void display()
 {
     Scanner scanner = new Scanner(System.in); 
     while(true){
     System.out.println("1-Register \n");
     System.out.println("2-log in \n");
       System.out.println("3-log in as admin \n");
     System.out.println("4-Exit \n");
     int n = scanner.nextInt() ;
     if (n==1)
     {
         take_new_user() ; 
     }
     else if (n==2)
     {
        enter() ; 
     }
     else if (n==3)
     {
       enter_admin() ; 
     }     
     else if (n==4)
     {
         System.out.println("We are glad to serve you \n");
         break; 
     }
     else 
     {
         System.out.println("Re-enter correct numbr \n");
     }
     }
 }
 //******************************************************************************************************************************
 private static void take_new_user()
 {
     Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the user's name:");
        String name_in = scanner.nextLine();

        System.out.println("Please enter the user's email:");
        String email_in = scanner.nextLine();

        System.out.println("Please enter the user's password:");
        String password_in = scanner.nextLine();
        
        System.out.println("Please enter the user's shipping address:");
        String shippingAddress_in = scanner.nextLine();
         MemberUser a = new MemberUser(name_in,email_in,password_in,shippingAddress_in) ;
         a.insertUser();
 }
 //******************************************************************************************************************************
 private static void enter()
 {
     Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the user's name:");
        String name = scanner.nextLine();
        
        System.out.println("Please enter the user's password:");
         String password = scanner.nextLine();
          MemberUser a = new MemberUser() ;
        a.getUser(name, password);
 }
 //******************************************************************************************************************************
 private static void enter_admin()
 {
     Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the name:");
        String name = scanner.nextLine();
        
        System.out.println("Please enter the  password:");
         String password = scanner.nextLine();
        ITadmin a = new ITadmin() ;
        a.getadmin(name, password);
 }
}

