import javax.swing.*;
/**
 * Created by kaeun on 2017. 5. 16..
 */
public class SnackBar {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JTextField textOrderDate;
    private JTextField textOrderCustomer;
    private JPanel orderinfo;
    private JComboBox textOrderMenu;
    private JButton btnOrder;
    private JButton btnOrderCancel;
    private JTextField textCustomerNum;
    private JTextField textCustomerName;
    private JTextField textCustomerPhone;
    private JTextField textJoinDate;
    private JButton btnAddCustomer;
    private JButton btnSearchCustomer;
    private JButton btnDeleteCustomer;

    public static void main(String[] args){
        JFrame frame = new JFrame("SnackBar");
        frame.setContentPane(new SnackBar().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

