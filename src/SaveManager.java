import java.io.*;
import java.util.ArrayList;

public class SaveManager {
    public static void init() {
       loadCustomerList();
       for (Customer i:StaticData.customers) {
           i.clean();
       }
    }

    public static void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("CustomerList.dat"));
            oos.writeObject(StaticData.customers);
            oos.close();
        }
        catch (IOException e) {
            System.out.println("Error while saving customer list");
            System.out.println(e.getMessage());
        }
    }

    private static void loadCustomerList() {
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("CustomerList.dat"));
            StaticData.customers=(ArrayList<Customer>) ois.readObject();
            ois.close();
        }
        catch (ClassNotFoundException | IOException _) {}
    }

    public static void saveMenu() {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("Menu.dat"));
            oos.writeObject(StaticData.menu);
            oos.close();
        }
        catch (IOException e) {
            System.out.println("Error while saving menu");
            System.out.println(e.getMessage());
        }
    }

    public static void savePendingOrders() {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("PendingOrders.dat"));
            oos.writeObject(StaticData.vipOrders);
            oos.writeObject(StaticData.orders);
            oos.close();
        }
        catch (IOException e) {
            System.out.println("Error while saving pending orders");
            System.out.println(e.getMessage());
        }
    }
}
