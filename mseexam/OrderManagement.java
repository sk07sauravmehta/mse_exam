
package mseexam;

import java.sql.*;
import java.util.Scanner;

public class OrderManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Connection con = DatabaseConnection.getConnection();

        if (con == null) {
            return;
        }

        int choice;

        do {

            System.out.println("\n===== ORDER MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Orders");
            System.out.println("2. Display Orders");
            System.out.println("3. Add Quantity");
            System.out.println("4. Update Order Status");
            System.out.println("5. Total Value and Delete");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addOrders(con, sc);
                    break;

                case 2:
                    displayOrders(con, sc);
                    break;

                case 3:
                    addQuantity(con, sc);
                    break;

                case 4:
                    updateStatus(con, sc);
                    break;

                case 5:
                    totalValueAndDelete(con, sc);
                    break;

                case 0:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);

        try {
            con.close();
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. Add Orders

    public static void addOrders(Connection con, Scanner sc) {

        String query = "INSERT INTO Orders VALUES (?, ?, ?, ?, ?, ?)";

        try {

            for (int i = 1; i <= 3; i++) {

                System.out.println("\nEnter Order Details " + i);

                System.out.print("Order ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Customer Name: ");
                String customer = sc.nextLine();

                System.out.print("Product Name: ");
                String product = sc.nextLine();

                System.out.print("Quantity: ");
                int quantity = Integer.parseInt(sc.nextLine());

                System.out.print("Unit Price: ");
                double price = Double.parseDouble(sc.nextLine());

                System.out.print("Status: ");
                String status = sc.nextLine();

                Order obj = new Order(
                    id, customer, product,
                    quantity, price, status
                );

                try (PreparedStatement ps = con.prepareStatement(query)) {

                    ps.setInt(1, obj.getOrder_id());
                    ps.setString(2, obj.getCustomer_name());
                    ps.setString(3, obj.getProduct_name());
                    ps.setInt(4, obj.getQuantity());
                    ps.setDouble(5, obj.getUnit_price());
                    ps.setString(6, obj.getStatus());

                    ps.executeUpdate();

                    System.out.println("Order Added Successfully!");

                } catch (SQLException e) {

                    if (e.getSQLState() != null &&
                        e.getSQLState().startsWith("23")) {

                        System.out.println("Duplicate Order ID!");

                    } else {
                        System.out.println(e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    // 2. Display Orders

    public static void displayOrders(Connection con, Scanner sc) {

        String query = "SELECT * FROM Orders WHERE status = ? " +
                       "AND quantity > 2 ORDER BY unit_price DESC";

        try {

            System.out.print("Enter Order Status: ");
            String status = sc.nextLine();

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, status);

                ResultSet rs = ps.executeQuery();

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println("\nOrder ID: " +
                        rs.getInt("order_id"));

                    System.out.println("Customer: " +
                        rs.getString("customer_name"));

                    System.out.println("Product: " +
                        rs.getString("product_name"));

                    System.out.println("Quantity: " +
                        rs.getInt("quantity"));

                    System.out.println("Unit Price: " +
                        rs.getDouble("unit_price"));

                    System.out.println("Status: " +
                        rs.getString("status"));
                }

                if (!found) {
                    System.out.println("No Orders Found!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Add Quantity

    public static void addQuantity(Connection con, Scanner sc) {

        String query = "UPDATE Orders SET quantity = quantity + ? " +
                       "WHERE order_id = ?";

        try {

            System.out.print("Enter Order ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Additional Quantity: ");
            int qty = Integer.parseInt(sc.nextLine());

            if (qty <= 0) {
                System.out.println("Quantity must be positive!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, qty);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Quantity Updated Successfully!");
                } else {
                    System.out.println("Order Not Found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    // 4. Update Order Status

    public static void updateStatus(Connection con, Scanner sc) {

        String query = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try {

            System.out.print("Enter Order ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter New Status: ");
            String status = sc.nextLine();

            if (!status.equalsIgnoreCase("Pending") &&
                !status.equalsIgnoreCase("Shipped") &&
                !status.equalsIgnoreCase("Delivered") &&
                !status.equalsIgnoreCase("Cancelled")) {

                System.out.println("Invalid Status!");
                return;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, status);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    System.out.println("Status Updated Successfully!");

                    String select = "SELECT * FROM Orders WHERE order_id = ?";

                    try (PreparedStatement ps2 =
                         con.prepareStatement(select)) {

                        ps2.setInt(1, id);

                        ResultSet rs = ps2.executeQuery();

                        if (rs.next()) {

                            System.out.println("\nUpdated Order Details:");

                            System.out.println("Order ID: " +
                                rs.getInt("order_id"));

                            System.out.println("Customer: " +
                                rs.getString("customer_name"));

                            System.out.println("Product: " +
                                rs.getString("product_name"));

                            System.out.println("Quantity: " +
                                rs.getInt("quantity"));

                            System.out.println("Unit Price: " +
                                rs.getDouble("unit_price"));

                            System.out.println("Status: " +
                                rs.getString("status"));
                        }
                    }

                } else {
                    System.out.println("Order Not Found!");
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    // 5. Total Value and Delete

    public static void totalValueAndDelete(Connection con, Scanner sc) {

        String totalQuery =
            "SELECT SUM(quantity * unit_price) AS total FROM Orders";

        String deleteQuery =
            "DELETE FROM Orders WHERE order_id = ? AND status = 'Cancelled'";

        try {

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(totalQuery)) {

                if (rs.next()) {

                    System.out.println("Total Order Value: " +
                        rs.getDouble("total"));
                }
            }

            System.out.print("\nEnter Order ID to Delete: ");
            int id = Integer.parseInt(sc.nextLine());

            try (PreparedStatement ps = con.prepareStatement(deleteQuery)) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Order Deleted Successfully!");
                } else {
                    System.out.println(
                        "Order Not Found or Status is not Cancelled!"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Operation Failed!");
            e.printStackTrace();
        }
    }
}
