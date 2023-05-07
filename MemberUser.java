package com.mycompany.oopjava;
import java.sql.*;
import java.util.Scanner;

public class MemberUser {
    private String name;
    private String email;
    private String password;
    private String shippingAddress;
    //***************************************************************************************************

    public MemberUser() {
    }
    //***************************************************************************************************

    public MemberUser(String name, String email, String password, String shippingAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.shippingAddress = shippingAddress;
    }
        //***************************************************************************************************

    public String getName() {
        return name;
    }
        public String getPassword() {
        return password;
    }
            //***************************************************************************************************

    public void insertUser() {
        String url = "jdbc:mysql://localhost:3306/myshop";
        String user = "root";
        String dbPassword = "123456789";

        try {
            Connection connection = DriverManager.getConnection(url, user, dbPassword);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (name, email, password, address) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, shippingAddress);

            statement.executeUpdate();

            statement.close();
            connection.close();

            System.out.println("User inserted successfully!");
            main_menue();
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }
        //***************************************************************************************************

public void getUser(String name, String password) {
    String url = "jdbc:mysql://localhost:3306/myshop";
    String user = "root";
    String dbPassword = "123456789";

    try {
        Connection connection = DriverManager.getConnection(url, user, dbPassword);

        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM users WHERE name = ? AND password = ?"
        );
        statement.setString(1, name);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String userName = resultSet.getString("name");
            System.out.println("Hello " + userName + "!");
            this.name = resultSet.getString("name");
            this.password = resultSet.getString("password");
           main_menue();
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

Scanner scanner = new Scanner(System.in);
    //***************************************************************************************************

   public void main_menue(){   
    while(true)
    {
        System.out.println("1-Veiw Products \n");
     System.out.println("2-Buy Item \n");
     System.out.println("3- Veiw order History \n");
     System.out.println("4- show your cart \n");
     System.out.println("5-remove Item from cart \n");
     System.out.println("6-procced to checkout \n");
     System.out.println("7- Return to main menue\n");
     System.out.println("8- close \n");
     int n = scanner.nextInt() ; 
     if (n==1)
     {
        products A = new products() ; 
         A.showproducts();
     }
         //***************************************************************************************************

     else if (n==2)
     {
      System.out.println("Want product? \n");
      System.out.println("Enter its ID \n");
      int k = scanner.nextInt() ;
      products A = new products() ; 
         A.addToCart(this, k);
           
     }
         //***************************************************************************************************

     else if (n==3)
     {
         MemberUser A = new MemberUser() ; 
       orders a = new orders();
      a.view_orders(this);
     }
         //***************************************************************************************************

          else if (n==4)
     {
         products A = new products() ; 
         A.showcart(this);
     }
  //***************************************************************************************************
     else if (n==5)
     {
products A = new products() ; 
      System.out.println("Want to remove product? \n");
      System.out.println("Enter its ID \n");
      int k = scanner.nextInt() ;
       A.removefromCart(this, k);
     }
     else if (n==6){
     products A = new products() ; 
       orders o = new orders();
       o.check_out(this);
     }
     else if (n==7)
     {
       User a = new User();
     a.display();
     }
    //***************************************************************************************************
     
     else if (n==8)
     {
         break ; 
     }
  //***************************************************************************************************
 
    }
    }
       //***************************************************************************************************

}