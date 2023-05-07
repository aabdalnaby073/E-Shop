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
public class orders {
public void check_out(MemberUser a) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        // Get the user's ID using their name and password
        PreparedStatement idStatement = connection.prepareStatement(
                "SELECT id FROM users WHERE name = ? AND password = ?"
        );
        idStatement.setString(1, a.getName());
        idStatement.setString(2, a.getPassword());
        ResultSet idResult = idStatement.executeQuery();
        if (!idResult.next()) {
            System.out.println("User not found.");
            return;
        }
        int userId = idResult.getInt("id");
        idResult.close();
        idStatement.close();

        // Get the user's cart ID using their ID
        PreparedStatement cartStatement = connection.prepareStatement(
                "SELECT cartId FROM cart WHERE userId = ?"
        );
        cartStatement.setInt(1, userId);
        ResultSet cartResult = cartStatement.executeQuery();
        if (!cartResult.next()) {
            System.out.println("Cart not found for user " + a.getName());
            return;
        }
        int cartd = cartResult.getInt("cartId");
        cartResult.close();
        cartStatement.close();

        // Insert the products into the orders table
        PreparedStatement orderStatement = connection.prepareStatement(
    "INSERT INTO orders (usercart, timeordered, timetodeliver) " +
    "VALUES (?, NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY))"
);

orderStatement.setInt(1, cartd);
orderStatement.executeUpdate();
orderStatement.close();

 //*************************************************delete cart
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error checking out: " + e.getMessage());
    }
}
//********************************************************************************************
public void view_orders(MemberUser memberuser) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        java.sql.Connection connection = DriverManager.getConnection(url, user, dbPassword);

        // Get the cart ID of the user
        PreparedStatement cartStatement = connection.prepareStatement(
                "SELECT cartId FROM cart WHERE userId = (SELECT id FROM users WHERE name = ? AND password = ?)"
        );
        cartStatement.setString(1, memberuser.getName());
        cartStatement.setString(2, memberuser.getPassword());
        ResultSet cartResult = cartStatement.executeQuery();
        if (!cartResult.next()) {
            System.out.println("No orders found for user: " + memberuser.getName());
            return;
        }
        int cartId = cartResult.getInt("cartId");

        // Retrieve all orders with product prices for the user's cart
        PreparedStatement orderStatement = connection.prepareStatement(
                "SELECT o.usercart, c.productName, c.price, o.timeordered, o.timetodeliver " +
                        "FROM orders o " +
                        "JOIN cart c ON o.usercart = c.cartId " +
                        "JOIN products p ON c.productId = p.product_id " +
                        "WHERE o.usercart = ?"
        );
        orderStatement.setInt(1, cartId);
        ResultSet orderResult = orderStatement.executeQuery();

        // Print the orders
        System.out.println("Orders for user " + memberuser.getName() + ":");
        while (orderResult.next()) {
            String productName = orderResult.getString("productName");
            double price = orderResult.getDouble("price");
            Timestamp timeOrdered = orderResult.getTimestamp("timeordered");
            Timestamp timeToDeliver = orderResult.getTimestamp("timetodeliver");

            System.out.println("Product Name: " + productName +
                    "\tPrice: " + price + "\tTime Ordered: " + timeOrdered +
                    "\tTime To Deliver: " + timeToDeliver);
        }

        cartResult.close();
        cartStatement.close();
        orderResult.close();
        orderStatement.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error viewing orders for user " + memberuser + ": " + e.getMessage());
    }
}
}
