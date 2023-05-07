/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oopjava;

import java.sql.DriverManager;
import java.sql.*;
import com.sun.jdi.connect.spi.Connection;
import java.util.Scanner;
/**
 *
 * @author ahmed
 */
public class ITadmin {
    private String name ; 
    private String password ;
   public ITadmin() {

    }
//****************************************************************************************************
    public ITadmin(String name, String password) {
        this.name = name;
        this.password = password;
    }   
//****************************************************************************************************
public void getadmin(String name, String password) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM admins WHERE name = ? AND password = ?"
        );
        statement.setString(1, name);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String userName = resultSet.getString("name");
            System.out.println("Hello " + userName + "!");
            this.name = resultSet.getString("name");
            this.password = resultSet.getString("password");
            adminfunctions() ; 
        } else {
            System.out.println("User not found.");
        }

        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error retrieving user: " + e.getMessage());
    }
}    
//***************************************************************************************************
public void adminfunctions ()
        {
            System.out.println("hi admin");
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
              System.out.println("1-Veiw Products \n");
               System.out.println("2-add Item \n");
               System.out.println("3-remove Item \n");
               int n = scanner.nextInt() ; 
     if (n==1)
     {
        products A = new products() ; 
         A.showproducts();
     }
         //***************************************************************************************************

     else if (n==2)
     {
take_product() ; 
     }
         //***************************************************************************************************

     else if (n==3)
     {

     }
     else 
     {
          System.out.println("wrong input \n");
     }
     //*************************************************************************************************
}
        }
    private void take_product()
     {
              Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the product name:");
        String name = scanner.nextLine();
        
        System.out.println("Please enter the supplier name:");
         String suppliername = scanner.nextLine();
          System.out.println("Please enter the product price:");
          int price = scanner.nextInt() ; 
        products a = new products() ;
        a.insertProduct(name, suppliername, price);
     }
}