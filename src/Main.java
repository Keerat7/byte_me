public class Main {

    public static Customer login(String email,String password) {
        for (Customer i:StaticData.customers) {
            if (i.getEmail().equals(email)) {
                if (i.getPassword().equals(password)) {
                    return i;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SaveManager.init();
        System.out.println("--Byte Me!--");
        System.out.println("Welcome to the Canteen Food Ordering Application");
        while (true) {
            System.out.println("\nPlease Select a User Role:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(StaticData.scan.nextLine());
            if (choice == 1) {
                System.out.print("\nEnter Password: ");
                if (!StaticData.scan.nextLine().equals(StaticData.ADMIN_PASS)) {
                    System.out.println("Invalid Password");
                    continue;
                }
                Admin a=new Admin();
                while (true) {
                    System.out.println("\n1. Add Item\n2. Update Item\n3. Remove Item\n4. View Pending Orders");
                    System.out.println("5. Complete Order\n6. Generate Sales Report\n7. Logout");
                    int choice2=Integer.parseInt(StaticData.scan.nextLine());
                    if (choice2==1) {
                        a.addItem();
                    }
                    else if (choice2==2) {
                        a.updateItem();
                    }
                    else if (choice2==3) {
                        a.removeItem();
                    }
                    else if (choice2==4) {
                        a.viewOrders();
                    }
                    else if (choice2==5) {
                        a.resolveOrder();
                    }
                    else if (choice2==6) {
                        a.generateSalesReport();
                    }
                    else if (choice2==7) {
                        break;
                    }
                    else {
                        System.out.println("Invalid Choice");
                    }
                }
            }
            else if (choice==2) {
                Customer c=null;
                while (true) {
                    System.out.println("\n1. Sign up\n2. Login\n3. Go back");
                    int choice2=Integer.parseInt(StaticData.scan.nextLine());
                    if (choice2==1) {
                        System.out.print("\nEnter Email: ");
                        String email=StaticData.scan.nextLine();
                        boolean found=false;
                        for (Customer i: StaticData.customers) {
                            if (i.getEmail().equals(email)) {
                                System.out.println("Account already exists, please login");
                                found=true;
                                break;
                            }
                        }
                        if (found) {
                            continue;
                        }
                        boolean made=false;
                        for (int i=0; i<3; i++) {
                            System.out.print("Enter Password: ");
                            String password=StaticData.scan.nextLine();
                            System.out.print("Confirm Password: ");
                            if (StaticData.scan.nextLine().equals(password)) {
                                c=new Customer(email,password);
                                StaticData.customers.add(c);
                                made=true;
                                break;
                            }
                            else {
                                System.out.println("Passwords do not match, please try again");
                            }
                        }
                        if (!made) {
                            System.out.println("Too many incorrect attempts");
                            continue;
                        }
                    }
                    else if (choice2==2) {
                        System.out.print("Enter Email: ");
                        String email=StaticData.scan.nextLine();
                        for (Customer i:StaticData.customers) {
                            if (i.getEmail().equals(email)) {
                                System.out.print("Enter Password: ");
                                if (StaticData.scan.nextLine().equals(i.getPassword())) {
                                    c=i;
                                }
                                break;
                            }
                        }
                        if (c==null) {
                            System.out.println("Account does not exist, please sign up");
                            continue;
                        }
                    }
                    else if (choice2==3) {
                        break;
                    }
                    else {
                        System.out.println("Invalid choice");
                        continue;
                    }
                    while (true) {
                        System.out.println("\n1. View All Items\n2. Search Item\n3. Filter Items by Category");
                        System.out.println("4. Sort Items by Price\n5. Add Item to Cart\n6. Modify Item Quantity");
                        System.out.println("7. Remove Item from Cart\n8. View Total\n9. Checkout");
                        System.out.println("10. View Order Status\n11. Cancel Order\n12. Order History");
                        System.out.println("13. Review Item\n14. View Reviews");
                        if (c.isVIP()) {
                            System.out.println("15. Cancel Subscription");
                        }
                        else {
                            System.out.println("15. Become VIP");
                        }
                        System.out.println("16. Logout");
                        choice2=Integer.parseInt(StaticData.scan.nextLine());
                        if (choice2==1) {
                            c.viewMenu();
                        }
                        else if (choice2==2) {
                            c.searchMenu();
                        }
                        else if (choice2==3) {
                            c.filterMenuByCategory();
                        }
                        else if (choice2==4) {
                            c.sortMenuByPrice();
                        }
                        else if (choice2==5) {
                            c.addToCart();
                        }
                        else if (choice2==6) {
                            c.updateItemQuantity();
                        }
                        else if (choice2==7) {
                            c.removeFromCart();
                        }
                        else if (choice2==8) {
                            c.viewTotal();
                        }
                        else if (choice2==9) {
                            c.checkout();
                        }
                        else if (choice2==10) {
                            c.orderStatus();
                        }
                        else if (choice2==11) {
                            c.cancelOrder();
                        }
                        else if (choice2==12) {
                            c.viewOrderHistory();
                        }
                        else if (choice2==13) {
                            c.leaveReview();
                        }
                        else if (choice2==14) {
                            c.seeReviews();
                        }
                        else if (choice2==15 && c.isVIP()) {
                            c.cancelSubscription();
                        }
                        else if (choice2==15 && !c.isVIP()) {
                            c.becomeVIP();
                        }
                        else if (choice2==16) {
                            break;
                        }
                        else {
                            System.out.println("Invalid choice");
                        }
                    }
                }
            }
            else if (choice == 3) {
                break;
            }
        }
        System.out.println("\nThank you for using the Canteen Food Ordering Application");
        System.out.println("Goodbye!");
        SaveManager.save();
    }
}