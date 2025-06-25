import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class StaticData {
    public static final String ADMIN_PASS="admin1234";
    public static Scanner scan=new Scanner(System.in);
    public static ArrayList<Item> menu=new ArrayList<>();
    public static PriorityQueue<Order> orders=new PriorityQueue<>();
    public static PriorityQueue<Order> vipOrders=new PriorityQueue<>();
    public static ArrayList<Order> completedOrders=new ArrayList<>();
    public static ArrayList<Customer> customers=new ArrayList<>();
}