import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer implements Serializable {
    private final String email;
    private final String name;
    private final String hostelRoom;
    private final String password;
    private boolean vip;
    private final HashMap<Item,Integer> cart;
    private Order currentOrder;
    private final ArrayList<Order> pastOrders;

    public Customer(String email,String password) {
        this.email=email;
        this.password=password;
        System.out.print("Enter Name: ");
        name=StaticData.scan.nextLine();
        System.out.print("Enter Hostel Room: ");
        hostelRoom=StaticData.scan.nextLine();
        cart=new HashMap<>();
        vip=false;
        pastOrders=new ArrayList<>();
    }

    public Customer(String email,String password,String name,String hostelRoom) {
        this.email=email;
        this.password=password;
        this.name=name;
        this.hostelRoom=hostelRoom;
        cart=new HashMap<>();
        vip=false;
        pastOrders=new ArrayList<>();
    }

    public String getHostelRoom() {
        return hostelRoom;
    }

    public boolean isVIP() {
        return vip;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<Item,Integer> getCart() {
        return cart;
    }

    public void clean() {
        cart.clear();
        currentOrder=null;
    }

    public void viewMenu() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("Menu has not been updated yet");
            return;
        }
        System.out.println("\nItem - Price - Category - Available");
        for (Item i: StaticData.menu) {
            i.display();
        }
    }

    public void searchMenu() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("Menu has not been updated yet");
            return;
        }
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(item)) {
                i.display();
            }
        }
    }

    public void filterMenuByCategory() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("Menu has not been updated yet");
            return;
        }
        System.out.print("\nEnter Category: ");
        String category=StaticData.scan.nextLine();
        System.out.println("\nItem - Price - Category - Available");
        for (Item i: StaticData.menu) {
            if (i.getCategory().equals(category)) {
                i.display();
            }
        }
    }

    public void sortMenuByPrice() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("Menu has not been updated yet");
            return;
        }
        Object menuCopyObj=StaticData.menu.clone();
        ArrayList<Item> menuCopy=(ArrayList<Item>) menuCopyObj;
        menuCopy.sort(Item::compareTo);
        System.out.println("\nItem - Price - Category - Available");
        for (Item i: menuCopy) {
            i.display();
        }
    }

    public void addToCart() {
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(item) && i.getAvailabilityStatus()) {
                System.out.print("Enter Quantity: ");
                cart.put(i,Integer.parseInt(StaticData.scan.nextLine()));
                break;
            }
        }
    }

    public void addToCart(String item,int quantity) {
        for (Item i: StaticData.menu) {
            if (i.getName().equals(item) && i.getAvailabilityStatus()) {
                cart.put(i,quantity);
                break;
            }
        }
    }

    public void updateItemQuantity() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: cart.keySet()) {
            if (i.getName().equals(item)) {
                System.out.print("Enter New Quantity: ");
                int quantity=Integer.parseInt(StaticData.scan.nextLine());
                if (quantity>0)
                    cart.put(i,quantity);
            }
        }
    }

    public void removeFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: cart.keySet()) {
            if (i.getName().equals(item)) {
                cart.remove(i);
                break;
            }
        }
    }

    public void viewTotal() {
        int cost=0;
        for (Item i: cart.keySet()) {
            cost+=i.getPrice()*cart.get(i);
        }
        System.out.println("\nTotal Cost: "+cost);
    }

    public int getTotal() {
        int cost=0;
        for (Item i: cart.keySet()) {
            cost+=i.getPrice()*cart.get(i);
        }
        return cost;
    }

    public void checkout() {
        if (((pastOrders.isEmpty() && currentOrder!=null) || (!pastOrders.isEmpty() &&
                currentOrder!=pastOrders.getLast()) && !currentOrder.isCancelled())) {
            System.out.println("You already have an order out");
            return;
        }
        System.out.println("\nTransferring Funds: Rs."+getTotal());
        System.out.println("Delivery Location: "+hostelRoom);
        currentOrder=new Order(this,cart);
        if (vip) {
            StaticData.vipOrders.offer(currentOrder);
        }
        else {
            StaticData.orders.offer(currentOrder);
        }
        cart.clear();
        SaveManager.savePendingOrders();
    }

    public void becomeVIP() {
        System.out.println("\nTo become a VIP you have to pay a monthly subscription of Rs.500");
        System.out.println("Do you wish to subscribe?(y/n): ");
        String choice=StaticData.scan.nextLine();
        if (choice.equals("y")) {
            System.out.println("Transferring Funds: Rs.500");
            vip=true;
        }
    }

    public void cancelSubscription() {
        System.out.println("\nCancelling subscription");
        vip=false;
    }

    public void orderStatus() {
        System.out.println();
        if (currentOrder==null) {
            System.out.println("No order has been placed yet");
            return;
        }

        if (currentOrder.getDeliveryStatus()) {
            System.out.println("Your order has been delivered");
            currentOrder=null;
        }
        else {
            System.out.println("Your order is en route");
        }
    }

    public void orderCompleted() {
        pastOrders.add(currentOrder);
    }

    public void cancelOrder() {
        System.out.println();
        if (currentOrder==null || currentOrder.getDeliveryStatus()) {
            System.out.println("There is no order to cancel");
            return;
        }
        currentOrder.cancel();
        System.out.println("Your order has been cancelled");
        System.out.println("Your order amount will be refunded within 2 business days");
    }

    public void viewOrderHistory() {
        System.out.println();
        if (pastOrders.isEmpty()) {
            System.out.println("No orders have been placed yet");
            return;
        }
        for (Order i: pastOrders) {
            i.display();
            System.out.println();
        }
        System.out.print("Do you wish to reorder?(y/n): ");
        String choice=StaticData.scan.nextLine();
        if (choice.equals("y")) {
            System.out.print("\nEnter Order ID: ");
            int id=Integer.parseInt(StaticData.scan.nextLine());
            cart.clear();
            for (Order i: pastOrders) {
                if (i.getOrderID()==id) {
                    for (Item j: i.getOrderItems().keySet()) {
                        cart.put(j,i.getOrderItems().get(j));
                    }
                    break;
                }
            }
            if (!cart.isEmpty())
                checkout();
        }
    }

    public void leaveReview() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("\nMenu is empty");
            return;
        }
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(item)) {
                i.addReview(this);
                break;
            }
        }
    }

    public void seeReviews() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("\nMenu is empty");
            return;
        }
        System.out.print("\nEnter Item: ");
        String item=StaticData.scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(item)) {
                i.viewReviews();
                break;
            }
        }
    }
}