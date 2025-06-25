import java.io.Serializable;
import java.util.HashMap;

public class Item implements Comparable<Item>, Serializable {
    private String name;
    private int price;
    private String category;
    private boolean isAvailable;
    private final HashMap<Customer,String> reviews;

    public Item() {
        System.out.println("\n--New Item--");
        System.out.print("Enter Name: ");
        name=StaticData.scan.nextLine();
        System.out.print("Enter Price: ");
        price=StaticData.scan.nextInt();
        StaticData.scan.nextLine();
        System.out.print("Enter Category: ");
        category=StaticData.scan.nextLine();
        System.out.print("Enter Availability Status: ");
        isAvailable=StaticData.scan.nextBoolean();
        StaticData.scan.nextLine();
        reviews=new HashMap<>();
    }

    public Item(String name,int price,String category,boolean isAvailable) {
        this.name=name;
        this.price=price;
        this.category=category;
        this.isAvailable=isAvailable;
        reviews=new HashMap<>();
    }

    @Override
    public int compareTo(Item o) {
        return Integer.compare(this.price,o.price);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean getAvailabilityStatus() {
        return isAvailable;
    }

    public void update() {
        System.out.println("\n--Updating Item--");
        System.out.println("1. Name - "+name);
        System.out.println("2. Price - "+price);
        System.out.println("3. Category - "+category);
        System.out.println("4. Availability - "+isAvailable);
        System.out.print("Select an option: ");
        int choice=StaticData.scan.nextInt();
        StaticData.scan.nextLine();
        switch(choice) {
            case 1: System.out.print("Enter New Name: ");
                    name=StaticData.scan.nextLine();
                    break;
            case 2: System.out.print("Enter New Price: ");
                    price=StaticData.scan.nextInt();
                    StaticData.scan.nextLine();
                    break;
            case 3: System.out.print("Enter New Category: ");
                    category=StaticData.scan.nextLine();
                    break;
            case 4: System.out.print("Enter Availability: ");
                    isAvailable=StaticData.scan.nextBoolean();
                    StaticData.scan.nextLine();
                    break;
        }
    }

    public void display() {
        System.out.println(name+" - "+price+" - "+category+" - "+isAvailable);
    }

    public String getDisplay() {
        return name+" - "+price+" - "+category+" - "+isAvailable;
    }

    public void addReview(Customer customer) {
        System.out.print("Enter Review: ");
        String review=StaticData.scan.nextLine();
        reviews.put(customer,review);
    }

    public void viewReviews() {
        if (reviews.isEmpty()) {
            System.out.println("No reviews exist");
            return;
        }
        for (Customer i: reviews.keySet()) {
            System.out.println(i.getName()+": "+reviews.get(i));
        }
    }
}