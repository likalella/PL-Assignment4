import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Vector;

/**
 * Created by kaeun on 2017. 5. 16..
 */
public class SnackBar extends JFrame {

    public static Vector<Customer> customer = new Vector<Customer>(0);
    public static Vector<Order> order = new Vector<Order>(0);
    public static int customerNum;
    public static int orderNum;
    private JTabbedPane tabbedPane1;
    private JPanel orderInfo;
    private JPanel orderDate;
    private JPanel CustomerJoinDate;
    private JPanel CustomerPhone;
    private JPanel customerManageBtn;
    private JPanel customerInfo;
    private JPanel orderManageBtn;
    private JPanel panelMain;
    private JComboBox textOrderMenu;
    private JButton btnOrder;
    private JButton btnOrderCancel;
    private JButton btnAddCustomer;
    private JButton btnSearchCustomer;
    private JButton btnDeleteCustomer;
    private JTextField textOrderCustomer;
    private JTextField textCustomerNum;
    private JTextField textCustomerName;
    private JTextField textCustomerPhone1;
    private JTextField textCustomerPhone2;
    private JTextField textCustomerPhone3;
    private JTextField textJoinYear;
    private JTextField textJoinMonth;
    private JTextField textJoinDay;
    private JTextField textOrderYear;
    private JTextField textOrderMonth;
    private JTextField textOrderDay;

    public SnackBar() {
        this.setContentPane(panelMain);
        this.pack();
        this.setVisible(true);
        customerNum = 0;
        orderNum = 0;
        readFile();

        btnOrder.addActionListener(new ActionListener() {
            @Override
            // 주문 버튼
            public void actionPerformed(ActionEvent e) {
                String year = textOrderYear.getText();
                String month = textOrderMonth.getText();
                String day = textOrderDay.getText();
                String cNum = textOrderCustomer.getText();
                String menu = String.valueOf(textOrderMenu.getSelectedItem());

                Runnable r = new Manage(1, cNum, year, month, day, menu);
                Thread t = new Thread(r);

                t.start();
            }
        });

        btnOrderCancel.addActionListener(new ActionListener() {
            @Override
            // 주문 취소 버튼
            public void actionPerformed(ActionEvent e) {
                String year = textOrderYear.getText();
                String month = textOrderMonth.getText();
                String day = textOrderDay.getText();
                String cNum = textOrderCustomer.getText();

                Runnable r = new Manage(2, cNum, year, month, day, null);
                Thread t = new Thread(r);

                t.start();
            }
        });

        btnAddCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 등록 버튼
            public void actionPerformed(ActionEvent e) {
                String cNum = textCustomerNum.getText();
                String name = textCustomerName.getText();
                String p1 = textCustomerPhone1.getText();
                String p2 = textCustomerPhone2.getText();
                String p3 = textCustomerPhone3.getText();
                String year = textJoinYear.getText();
                String month = textJoinMonth.getText();
                String day = textJoinDay.getText();

                Runnable r = new Manage(3, cNum, name, p1, p2, p3, year, month, day);
                Thread t = new Thread(r);

                t.start();
            }
        });

        btnSearchCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 검색 버튼
            public void actionPerformed(ActionEvent e) {
                String cNum = textCustomerNum.getText();

                Runnable r = new Manage(4, cNum);
                Thread t = new Thread(r);

                t.start();
            }
        });

        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 삭제 버튼
            public void actionPerformed(ActionEvent e) {
                String cNum = textCustomerNum.getText();
                Runnable r = new Manage(5, cNum);
                Thread t = new Thread(r);

                t.start();

            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                writeFile();
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        //new SnackBar();
        JFrame frame = new SnackBar();
        //frame.setContentPane(new SnackBar());
        //frame.pack();
        //frame.setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        textCustomerNum = new JTextField();
        textCustomerName = new JTextField();
        textCustomerPhone1 = new JTextField();
        textCustomerPhone2 = new JTextField();
        textCustomerPhone3 = new JTextField();
        textJoinYear = new JTextField();
        textJoinMonth = new JTextField();
        textJoinDay = new JTextField();
        textOrderYear = new JTextField();
        textOrderMonth = new JTextField();
        textOrderDay = new JTextField();
        textOrderCustomer = new JTextField();
        textCustomerNum.setDocument(new JTextFieldLimit(4));
        textCustomerName.setDocument(new JTextFieldLimit(10));
        textCustomerPhone1.setDocument(new JTextFieldLimit(3));
        textCustomerPhone2.setDocument(new JTextFieldLimit(4));
        textCustomerPhone3.setDocument(new JTextFieldLimit(4));
        textJoinYear.setDocument(new JTextFieldLimit(4));
        textJoinMonth.setDocument(new JTextFieldLimit(2));
        textJoinDay.setDocument(new JTextFieldLimit(2));
        textOrderYear.setDocument(new JTextFieldLimit(4));
        textOrderMonth.setDocument(new JTextFieldLimit(2));
        textOrderDay.setDocument(new JTextFieldLimit(2));
        textOrderCustomer.setDocument(new JTextFieldLimit(4));
    }

    private void readFile() {
        File inFileCustom = new File("./custom.txt");
        BufferedReader brc = null;
        try {
            brc = new BufferedReader(new FileReader(inFileCustom));
            String line;
            String info[] = new String[6];
            int n = 0;
            while ((line = brc.readLine()) != null) {
                info[n] = line;
                n++;
                if (n == 6) {
                    Customer c = new Customer(info[0], info[1], info[2], info[3],
                            Integer.parseInt(info[4]), Integer.parseInt(info[5]));
                    customer.add(c);
                    customerNum++;
                    n = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (brc != null) {
                try {
                    brc.close();
                } catch (IOException e) {
                }
            }
        }

        File inFileOrder = new File("./order.txt");
        BufferedReader bro = null;
        try {
            bro = new BufferedReader(new FileReader(inFileOrder));
            String line;
            String info[] = new String[3];
            int n = 0;
            while ((line = bro.readLine()) != null) {
                info[n] = line;
                n++;
                if (n == 3) {
                    Order o = new Order(info[0], info[1], info[2]);
                    order.add(o);
                    orderNum++;
                    n = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bro != null) {
                try {
                    bro.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void writeFile() {
        File outFileCustom = new File("./custom.txt");
        BufferedWriter bwc = null;
        try {
            bwc = new BufferedWriter(new FileWriter(outFileCustom));
            for (int i = 0; i < customerNum; i++) {
                bwc.write(customer.elementAt(i).getID());
                bwc.newLine();
                bwc.write(customer.elementAt(i).getName());
                bwc.newLine();
                bwc.write(customer.elementAt(i).getPhone());
                bwc.newLine();
                bwc.write(customer.elementAt(i).getJoinDate());
                bwc.newLine();
                bwc.write(String.valueOf(customer.elementAt(i).getOrderNum()));
                bwc.newLine();
                bwc.write(String.valueOf(customer.elementAt(i).getTotalNum()));
                bwc.newLine();
            }
            bwc.flush();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (bwc != null) {
                try {
                    bwc.close();
                } catch (IOException e) {
                }
            }
        }
        File outFileOrder = new File("./order.txt");
        BufferedWriter bwo = null;
        try {
            bwo = new BufferedWriter(new FileWriter(outFileOrder));
            for (int i = 0; i < orderNum; i++) {
                bwo.write(order.elementAt(i).getID());
                bwo.newLine();
                bwo.write(order.elementAt(i).getDate());
                bwo.newLine();
                bwo.write(order.elementAt(i).getMenu());
                bwo.newLine();
            }
            bwo.flush();
        } catch (IOException e) {
            e.printStackTrace();
            ;

        } finally {
            if (bwo != null) {
                try {
                    bwo.close();
                } catch (IOException e) {
                }
            }
        }
    }
}



// 정규식 참조 : http://devfalledinmac.tistory.com/14
// 파일 입출력 : http://hmjkor.tistory.com/451