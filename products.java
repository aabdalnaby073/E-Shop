/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oopjava;
import java.sql.*;
import com.sun.jdi.connect.spi.Connection;
import java.util.Scanner;

/**
 *
 * @author ahmed
 */
public class products {
     private String product_name ; 
   private int product_id ; 
   private String supplier_name ; 
    public products() {
         
   }
    //***************************************************************************************************
    public products(String product_name, int product_id, String supplier_name) {
      this.product_name = product_name;
      this.product_id = product_id;
      this.supplier_name = supplier_name;
   }
    //***************************************************************************************************

  public void showproducts() {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
         java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        PreparedStatement statement = connection.prepareStatement(
                "select * from products "
        );

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String productName = resultSet.getString("productname");
            String suppliername = resultSet.getString("suppliername");
            int price = resultSet.getInt("price");
            int id = resultSet.getInt("product_id");

            System.out.println("Product Name: " + productName);
            System.out.println("supplier: " + suppliername);
            System.out.println("price: " + price);
            System.out.println("ID: " + id);
            System.out.println("--------------------------");
        }      
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error retrieving products: " + e.getMessage());
    }
}  
      //***************************************************************************************************

  public void addToCart(MemberUser a, int productId) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        // Check if user exists
         java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE name = ? AND password = ?"
        );
        statement.setString(1, a.getName());
        statement.setString(2, a.getPassword());
        System.out.println(a.getName());
        System.out.println(a.getPassword());
        ResultSet userResult = statement.executeQuery();
        if (!userResult.next()) {
            System.out.println("Invalid username or password");
            return;
        }
        int userId = userResult.getInt("id");

        // Check if product exists
        PreparedStatement productStatement = connection.prepareStatement(
                "SELECT productname,price FROM products WHERE product_id = ?"
        );
        productStatement.setInt(1, productId);
        ResultSet productResult = productStatement.executeQuery();
        if (!productResult.next()) {
            System.out.println("Product not found");
            return;
        }
        String productName = productResult.getString("productname");
         int price = productResult.getInt("price");
        // Insert into cart table
        PreparedStatement cartStatement = connection.prepareStatement(
                "INSERT INTO cart (userId, productId, productName,price, timeAddedToCart) VALUES (?, ?, ?, ?,NOW())"
        );
        cartStatement.setInt(1, userId);
        cartStatement.setInt(2, productId);
        cartStatement.setString(3, productName);
        cartStatement.setInt(4, price);
        cartStatement.executeUpdate();

        cartStatement.close();
        userResult.close();
        statement.close();
        productResult.close();
        productStatement.close();
        connection.close();

        System.out.println("Product added to cart successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding product to cart: " + e.getMessage());
    }
}
    //***************************************************************************************************
  public void showcart(MemberUser a) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        PreparedStatement statement = connection.prepareStatement(
                "select productName , price from cart where userId = (select id from users where name = ? and password = ?)"
        );
        statement.setString(1, a.getName());
        statement.setString(2, a.getPassword());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String productName = resultSet.getString("productName");
            int price = resultSet.getInt("price");

            System.out.println("Product Name: " + productName);
            System.out.println("Price: " + price);
            System.out.println("--------------------------");
        }

        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error retrieving products: " + e.getMessage());
    }
}
  //***************************************************************************************************
   public void removefromCart(MemberUser a, int productId) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        // Check if user exists
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM users WHERE name = ? AND password = ?"
        );
        statement.setString(1, a.getName());
        statement.setString(2, a.getPassword());
        ResultSet userResult = statement.executeQuery();
        if (!userResult.next()) {
            System.out.println("Invalid username or password");
            return;
        }
        int userId = userResult.getInt("id");

        // Check if product exists
        PreparedStatement productStatement = connection.prepareStatement(
                "SELECT productname,price FROM products WHERE product_id = ?"
        );
        productStatement.setInt(1, productId);
        ResultSet productResult = productStatement.executeQuery();
        if (!productResult.next()) {
            System.out.println("Product not found");
            return;
        }
        String productName = productResult.getString("productname");
        int price = productResult.getInt("price");

        // Delete related rows in orders table
        PreparedStatement ordersStatement = connection.prepareStatement(
                "DELETE FROM orders WHERE usercart IN (SELECT cartId FROM cart WHERE userId = ? AND productId = ?)"
        );
        ordersStatement.setInt(1, userId);
        ordersStatement.setInt(2, productId);
        ordersStatement.executeUpdate();

        // Delete row from cart table
        PreparedStatement cartStatement = connection.prepareStatement(
                "DELETE FROM cart WHERE userId = ? AND productId = ?"
        );
        cartStatement.setInt(1, userId);
        cartStatement.setInt(2, productId);
        cartStatement.executeUpdate();

        ordersStatement.close();
        cartStatement.close();
        userResult.close();
        statement.close();
        productResult.close();
        productStatement.close();
        connection.close();

        System.out.println("Product removed from cart successfully!");
    } catch (SQLException e) {
        System.out.println("Error removing product from cart: " + e.getMessage());
    }
}
//***************************************************************************************************
   public void insertProduct(String productName, String supplierName,int price) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO products (productname, suppliername,price) VALUES (?, ?,?)"
        );
        statement.setString(1, productName);
        statement.setString(2, supplierName);
        statement.setInt(3, price);
        statement.executeUpdate();

        statement.close();
        connection.close();

        System.out.println("Product inserted successfully!");
    } catch (SQLException e) {
        System.out.println("Error inserting product: " + e.getMessage());
    }
}
}
