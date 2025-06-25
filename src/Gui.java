import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Gui {

    public static void main(String[] args) {
        JFrame frame=new JFrame("Byte Me!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);

        CardLayout layout=new CardLayout();
        JPanel mainPanel=new JPanel(layout);
        JPanel mainScreen=new JPanel();

        mainScreen.setLayout(new GridBagLayout());
        JButton menuButton=new JButton("Browse Menu");
        JButton pendingOrdersButton=new JButton("Pending Orders");
        mainScreen.add(menuButton);
        mainScreen.add(pendingOrdersButton);

        JPanel menuScreen=new JPanel();
        menuScreen.setLayout(new BorderLayout());
        JTextArea textArea1=new JTextArea();
        textArea1.setEditable(false);
        JScrollPane scrollPane1=new JScrollPane(textArea1);
        JButton backButton1=new JButton("Back");
        menuScreen.add(scrollPane1,BorderLayout.CENTER);
        menuScreen.add(backButton1,BorderLayout.SOUTH);

        JPanel pendingOrdersScreen=new JPanel();
        pendingOrdersScreen.setLayout(new BorderLayout());
        JTextArea textArea2=new JTextArea();
        textArea2.setEditable(false);
        JScrollPane scrollPane2=new JScrollPane(textArea2);
        JButton backButton2=new JButton("Back");
        pendingOrdersScreen.add(scrollPane2,BorderLayout.CENTER);
        pendingOrdersScreen.add(backButton2,BorderLayout.SOUTH);


        mainPanel.add(mainScreen,"Main");
        mainPanel.add(menuScreen,"Menu");
        mainPanel.add(pendingOrdersScreen,"Pending Orders");

        menuButton.addActionListener(e -> {
            try {
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream("Menu.dat"));
                ArrayList<Item> menu=(ArrayList<Item>) ois.readObject();
                ois.close();
                StringBuilder buildString=new StringBuilder();
                for (Item item :menu) {
                    buildString.append(item.getDisplay());
                    buildString.append("\n");
                }
                textArea1.setText(buildString.toString());
            }
            catch (ClassNotFoundException | IOException _) {}
            layout.show(mainPanel,"Menu");
        });

        pendingOrdersButton.addActionListener(e -> {
            try {
                ObjectInputStream ois=new ObjectInputStream(new FileInputStream("PendingOrders.dat"));
                PriorityQueue<Order> vipOrders=(PriorityQueue<Order>) ois.readObject();
                PriorityQueue<Order> orders=(PriorityQueue<Order>) ois.readObject();
                ois.close();
                StringBuilder buildString=new StringBuilder();
                buildString.append("--VIP Orders--\n\n");
                for (Order i:vipOrders) {
                    buildString.append(i.getDisplay());
                    buildString.append("\n\n");
                }
                buildString.append("--Orders--\n\n");
                for (Order i:orders) {
                    buildString.append(i.getDisplay());
                    buildString.append("\n\n");
                }
                textArea2.setText(buildString.toString());
            }
            catch (ClassNotFoundException | IOException _) {}
            layout.show(mainPanel,"Pending Orders");
        });

        backButton1.addActionListener(e -> {
            layout.show(mainPanel,"Main");
        });

        backButton2.addActionListener(e -> {
            layout.show(mainPanel,"Main");
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
