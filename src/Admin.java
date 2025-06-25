import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Admin {
    Scanner scan=StaticData.scan;

    public void addItem() {
        StaticData.menu.add(new Item());
        SaveManager.saveMenu();
    }

    public void updateItem() {
        System.out.print("\nEnter Item Name: ");
        String name=scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(name)) {
                i.update();
                return;
            }
        }
        SaveManager.saveMenu();
    }

    public void removeItem() {
        if (StaticData.menu.isEmpty()) {
            System.out.println("Item does not exist");
            return;
        }
        System.out.print("Enter Item Name: ");
        String name=scan.nextLine();
        for (Item i: StaticData.menu) {
            if (i.getName().equals(name)) {
                StaticData.menu.remove(i);
                SaveManager.saveMenu();
                System.out.println("Item has been removed");
                for (Order j: StaticData.vipOrders) {
                    if (j.getOrderItems().containsKey(i)) {
                        j.cancel();
                    }
                }
                for (Order j: StaticData.orders) {
                    if (j.getOrderItems().containsKey(i)) {
                        j.cancel();
                    }
                }
                return;
            }
        }
        System.out.println("Item does not exist");
    }

    public void viewOrders() {
        if (StaticData.orders.isEmpty() && StaticData.vipOrders.isEmpty()) {
            System.out.println("\nThere are no orders yet");
            return;
        }
        if (!StaticData.vipOrders.isEmpty()) {
            System.out.println("\nVip Orders:");
            System.out.print("ID: ");
            for (Order i: StaticData.vipOrders) {
                System.out.print(i.getOrderID()+" ");
            }
        }
        if (!StaticData.orders.isEmpty()) {
            System.out.println("\nRegular Orders:");
            System.out.print("ID: ");
            for (Order i: StaticData.orders) {
                System.out.print(i.getOrderID()+" ");
            }
        }
    }

    public void resolveOrder() {
        if (!StaticData.vipOrders.isEmpty()) {
            Order o=StaticData.vipOrders.poll();
            SaveManager.savePendingOrders();
            if (!o.isCancelled()) {
                o.resolve();
                StaticData.completedOrders.add(o);
            }
            else {
                System.out.println("Vip Order has been cancelled");
                System.out.println("Refunding Amount: Rs."+o.getPrice());
            }
            return;
        }
        if (!StaticData.orders.isEmpty()) {
            Order o=StaticData.orders.poll();
            SaveManager.savePendingOrders();
            if (!o.isCancelled()) {
                o.resolve();
                StaticData.completedOrders.add(o);
            }
            else {
                System.out.println("Vip Order has been cancelled");
                System.out.println("Refunding Amount: Rs."+o.getPrice());
            }
        }
    }

    public void generateSalesReport() {
        System.out.println("\n--Sales Report--");
        int c=0,total=0;
        HashMap<Item,Integer> itemFrequencies=new HashMap<>();
        for (Item i: StaticData.menu) {
            itemFrequencies.put(i,0);
        }
        for (Order i: StaticData.completedOrders) {
            if (i.getOrderDate().isEqual(LocalDate.now())) {
                HashMap<Item,Integer> orderItems=i.getOrderItems();
                for (Item j: orderItems.keySet()) {
                    itemFrequencies.put(j,itemFrequencies.get(j)+orderItems.get(j));
                }
                System.out.println(i.getOrderID() + " - Rs." + i.getPrice());
                total += i.getPrice();
                c++;
            }
        }
        System.out.println("Total Sales: "+c+"\tTotal Earnings: Rs."+total);
        int max=0;
        Item maxSold=null;
        for (Item i: itemFrequencies.keySet()) {
            if (itemFrequencies.get(i)>max) {
                max=itemFrequencies.get(i);
                maxSold=i;
            }
        }
        if (maxSold!=null)
            System.out.println("Most Popular Item: "+maxSold.getName());
    }
}