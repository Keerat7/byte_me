import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class Order implements Comparable<Order>, Serializable {
    private static int idCounter=1;
    private final int orderID;
    private final Customer customer;
    private final HashMap<Item,Integer> orderItems;
    private int price;
    boolean delivered;
    boolean cancelled;
    private String specialRequest;
    private final LocalDate orderDate;

    public Order(Customer customer, HashMap<Item,Integer> cart) {
        orderItems=new HashMap<>();
        orderID=idCounter++;
        price=0;
        this.customer=customer;
        for (Item i:cart.keySet()) {
            orderItems.put(i,cart.get(i));
            price+=i.getPrice()*cart.get(i);
        }
        delivered=false;
        cancelled=false;
        System.out.println("\nDo you have a special request?(y/n): ");
        char ch=StaticData.scan.next().charAt(0);
        StaticData.scan.nextLine();
        if (ch=='y') {
            System.out.print("Enter Request: ");
            specialRequest=StaticData.scan.nextLine();
        }
        System.out.println("\nOrder Confirmed");
        System.out.println("Order ID: "+orderID);
        orderDate=LocalDate.now();
    }

    public int getOrderID() {
        return orderID;
    }

    public int getPrice() {
        return price;
    }

    public boolean getDeliveryStatus() {
        return delivered;
    }

    public HashMap<Item,Integer> getOrderItems() {
        return orderItems;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(orderID,o.orderID);
    }

    public void resolve() {
        delivered=true;
        customer.orderCompleted();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled=true;
    }

    public void display() {
        System.out.println("--Order--");
        System.out.println("Order ID: "+orderID);
        System.out.println("Order Date: "+orderDate);
        for (Item i:orderItems.keySet()) {
            System.out.println(i.getName()+" - "+orderItems.get(i));
        }
        System.out.println("Price: "+price);
        System.out.println("Delivery Location: "+customer.getHostelRoom());
        System.out.println("Special Request: "+specialRequest);
    }

    public String getDisplay() {
        StringBuilder buildString=new StringBuilder();
        buildString.append("Order ID: ").append(orderID);
        buildString.append("\nOrder Date: ").append(orderDate);
        for (Item i:orderItems.keySet()) {
            buildString.append("\n").append(i.getName()).append(" - ").append(orderItems.get(i));
        }
        buildString.append("\nPrice: ").append(price);
        buildString.append("\nDelivery Location: ").append(customer.getHostelRoom());
        buildString.append("\nSpecial Request: ").append(specialRequest);
        return buildString.toString();
    }
}