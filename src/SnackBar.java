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
public class SnackBar extends JFrame{

    private Vector<Customer> customer = new Vector<Customer>(0);
    private Vector<Order> order = new Vector<Order>(0);
    private int customerNum;
    private int orderNum;
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
        customerNum=0;
        orderNum = 0;
        readFile();
        this.setContentPane(panelMain);
        this.pack();
        this.setVisible(true);

        btnOrder.addActionListener(new ActionListener() {
            @Override
            // 주문 버튼
            public void actionPerformed(ActionEvent e) {
                try {
                    // 날짜
                    String year = textOrderYear.getText();
                    String month = textOrderMonth.getText();
                    String day = textOrderDay.getText();
                    checkIsNull("날짜", year);
                    checkIsNull("날짜", month);
                    checkIsNull("날짜", day);
                    checkIsCorrectNumForm("날짜", year, 1, 4);
                    checkIsCorrectNumForm("날짜", month, 1, 2);
                    checkIsCorrectNumForm("날짜", day, 1, 2);
                    checkValidDate(year, month, day);
                    if(month.length() < 2) month = "0" + month;
                    if(day.length() < 2) day = "0" + day;
                    String date = year + "/" + month + "/" + day;
                    // 고객번호
                    String cNum = textOrderCustomer.getText();
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);
                    // 메뉴
                    String menu = String.valueOf(textOrderMenu.getSelectedItem());
                    checkIsNull("메뉴", menu);
                    checkValidName("메뉴", menu, 1, 10);

                    boolean coupon = addOrder(cNum, date, menu);
                    if(coupon == true){
                        String title = "COUPON";
                        String mesg = "쿠폰이 도착했습니다 \n";
                        JOptionPane.showMessageDialog(panelMain, mesg, title,
                                JOptionPane.OK_OPTION, null);
                    }
                }
                catch (SBException sbe) {
                    String title = "ERR : " + sbe.getErrCode();
                    JOptionPane.showMessageDialog(panelMain, sbe.getMessage(), title,
                            JOptionPane.OK_OPTION, null);
                }
            }
        });

        btnOrderCancel.addActionListener(new ActionListener() {
            @Override
            // 주문 취소 버튼
            public void actionPerformed(ActionEvent e) {
                try {
                    // 날짜
                    String year = textOrderYear.getText();
                    String month = textOrderMonth.getText();
                    String day = textOrderDay.getText();
                    checkIsNull("날짜", year);
                    checkIsNull("날짜", month);
                    checkIsNull("날짜", day);
                    checkIsCorrectNumForm("날짜", year, 1, 4);
                    checkIsCorrectNumForm("날짜", month, 1, 2);
                    checkIsCorrectNumForm("날짜", day, 1, 2);
                    checkValidDate(year, month, day);
                    if (month.length() < 2) month = "0" + month;
                    if (day.length() < 2) day = "0" + day;
                    String date = year + "/" + month + "/" + day;
                    // 고객번호
                    String cNum = textOrderCustomer.getText();
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);

                    cancelOrder(cNum, date);
                }
                catch (SBException sbe) {
                    String title = "ERR : " + sbe.getErrCode();
                    JOptionPane.showMessageDialog(panelMain, sbe.getMessage(), title,
                            JOptionPane.OK_OPTION, null);
                }
            }

        });

        btnAddCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 등록 버튼
            public void actionPerformed(ActionEvent e) {
                try {
                    // 고객번호
                    String cNum = textCustomerNum.getText();
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);
                    // 고객명
                    String name = textCustomerName.getText();
                    checkIsNull("고객명", name);
                    checkValidName("고객명", name, 1, 10);
                    // 전화번호
                    String p1 = textCustomerPhone1.getText();
                    String p2 = textCustomerPhone2.getText();
                    String p3 = textCustomerPhone3.getText();
                    checkIsNull("전화번호", p1);
                    checkIsNull("전화번호", p2);
                    checkIsNull("전화번호", p3);
                    checkIsCorrectNumForm("전화번호", p1, 2, 3);
                    checkIsCorrectNumForm("전화번호", p2, 3, 4);
                    checkIsCorrectNumForm("전화번호", p3, 4, 4);
                    String phone = p1 + "-" + p2 + "-" + p3;
                    // 가입일
                    String year = textJoinYear.getText();
                    String month = textJoinMonth.getText();
                    String day = textJoinDay.getText();
                    checkIsNull("가입일", year);
                    checkIsNull("가입일", month);
                    checkIsNull("가입일", day);
                    checkIsCorrectNumForm("가입일", year, 1, 4);
                    checkIsCorrectNumForm("가입일", month, 1, 2);
                    checkIsCorrectNumForm("가입일", day, 1, 2);
                    checkValidDate(year, month, day);
                    if(month.length() < 2) month = "0" + month;
                    if(day.length() < 2) day = "0" + day;
                    String date = year + "/" + month + "/" + day;

                    addCustomer(cNum, name, phone, date);

                } catch (SBException sbe) {
                    String title = "ERR : " + sbe.getErrCode();
                    JOptionPane.showMessageDialog(panelMain, sbe.getMessage(), title,
                            JOptionPane.OK_OPTION, null);
                }
            }
        });

        btnSearchCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 검색 버튼
            public void actionPerformed(ActionEvent e) {
                try {
                    String cNum = textCustomerNum.getText();
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);
                    searchCustomer(cNum);

                } catch (SBException sbe) {
                    String title = "ERR : " + sbe.getErrCode();
                    JOptionPane.showMessageDialog(panelMain, sbe.getMessage(), title,
                            JOptionPane.OK_OPTION, null);
                }
            }
        });

        btnDeleteCustomer.addActionListener(new ActionListener() {
            @Override
            // 고객 삭제 버튼
            public void actionPerformed(ActionEvent e) {
                try {
                    String cNum = textCustomerNum.getText();
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);

                    deleteCustomer(cNum);

                } catch (SBException sbe) {
                    String title = "ERR : " + sbe.getErrCode();
                    JOptionPane.showMessageDialog(panelMain, sbe.getMessage(), title,
                            JOptionPane.OK_OPTION, null);
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                writeFile();
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        new SnackBar();

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

    private void readFile(){
        File inFileCustom = new File("./custom.txt");
        BufferedReader brc = null;
        try{
            brc = new BufferedReader(new FileReader(inFileCustom));
            String line;
            String info[] = new String[6];
            int n = 0;
            while((line = brc.readLine()) != null){
                info[n] = line;
                n++;
                if(n == 6){
                    Customer c = new Customer(info[0], info[1], info[2], info[3],
                            Integer.parseInt(info[4]), Integer.parseInt(info[5]));
                    customer.add(c);
                    customerNum++;
                    n = 0;
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally { if(brc != null){ try{ brc.close(); } catch (IOException e) {} }
        }

        File inFileOrder = new File("./order.txt");
        BufferedReader bro = null;
        try{
            bro = new BufferedReader(new FileReader(inFileOrder));
            String line;
            String info[] = new String[3];
            int n = 0;
            while((line = bro.readLine()) != null){
                info[n] = line;
                n++;
                if(n == 3){
                    Order o = new Order(info[0], info[1], info[2]);
                    order.add(o);
                    orderNum++;
                    n = 0;
                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally { if(bro != null){ try{ bro.close(); } catch (IOException e) {} }
        }
    }

    public void writeFile(){
        File outFileCustom = new File("./custom.txt");
        BufferedWriter bwc = null;
        try{
            bwc = new BufferedWriter(new FileWriter(outFileCustom));
            for(int i=0; i<customerNum; i++){
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
        }catch(IOException e){
            e.printStackTrace();;

        }finally { if(bwc != null){ try{ bwc.close(); } catch (IOException e) {} }}
        File outFileOrder = new File("./order.txt");
        BufferedWriter bwo = null;
        try{
            bwo = new BufferedWriter(new FileWriter(outFileOrder));
            for(int i=0; i<orderNum; i++){
                bwo.write(order.elementAt(i).getID());
                bwo.newLine();
                bwo.write(order.elementAt(i).getDate());
                bwo.newLine();
                bwo.write(order.elementAt(i).getMenu());
                bwo.newLine();
            }
            bwo.flush();
        }catch(IOException e){
            e.printStackTrace();;

        }finally { if(bwo != null){ try{ bwo.close(); } catch (IOException e) {} }}
    }

    private int findIndexCustomer(String cNum){
        int rst=-1;
        for(int i=0; i<customerNum; i++){
            if(customer.elementAt(i).getID().equals(cNum) == true){
                rst = i;
                break;
            }
        }
        return rst;
    }

    private int findIndexOrder(String cNum, String date){
        int rst = -1;
        for(int i=0; i<orderNum; i++){
            if((order.elementAt(i).getID().equals(cNum) == true)
                    &&(order.elementAt(i).getDate().equals(date) == true)){
                rst = i;
                break;
            }
        }
        return rst;
    }

    private int findSameInfo(String cNum, String name, String phone, String date){
        int rst = -1;
        for(int i=0; i<customerNum; i++){
            String nu = customer.elementAt(i).getID();
            String n = customer.elementAt(i).getName();
            String p = customer.elementAt(i).getPhone();
            String d = customer.elementAt(i).getJoinDate();

            if((nu.equals(cNum) == false) && (n.equals(name) == true)
                    && (p.equals(phone) == true) && (d.equals(date) == true)){
                rst = i;
                break;
            }
        }
        return rst;
    }

    private void checkValidDate(String y, String m, String d) throws SBException {
        int year = Integer.parseInt(y);
        int month = Integer.parseInt(m);
        int day = Integer.parseInt(d);
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day >= 1 && day <= 31) ;
                else {
                    String err_msg = "유효한 가입일을 입력해주세요";
                    throw new SBException(err_msg);
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day >= 1 && day <= 30) ;
                else {
                    String err_msg = "유효한 가입일을 입력해주세요";
                    throw new SBException(err_msg);
                }
                break;
            case 2:
                if (day == 29) {
                    if (year % 400 == 0) ;
                    else if ((year % 4 == 0) && (year % 100 != 0)) ;
                    else {
                        String err_msg = "유효한 가입일을 입력해주세요";
                        throw new SBException(err_msg);
                    }
                } else if (day >= 1 && day <= 28) ;
                else {
                    String err_msg = "유효한 가입일을 입력해주세요";
                    throw new SBException(err_msg);
                }
                break;
            default:
                String err_msg = "유효한 가입일을 입력해주세요";
                throw new SBException(err_msg);
        }
    }

    private void checkIsNull(String attr, String val) throws SBException {
        if (val.equals("")) {
            String err_msg = attr + "을(를) 입력해주세요.\n";
            throw new SBException(err_msg, 6);
        }
    }

    private void checkIsCorrectNumForm(String attr, String val, int len1, int len2) throws SBException{
        if(val.length() < len1 || val.length() > len2){
            String err_msg = attr + "의 형식을 확인해주세요\n";
            throw new SBException(err_msg, 1);
        }

        for(char c : val.toCharArray()){
            if(Character.isDigit(c) == false){
                if((attr.equals("가입일") || attr.equals("날짜")) && c == '/');
                else if(attr.equals("전화번호") && c == '-');
                else{
                    String err_msg = attr + "의 형식을 확인해주세요\n";
                    throw new SBException(err_msg, 1);
                }
            }
        }
    }

    private void checkValidName(String attr, String name, int len1, int len2) throws SBException{
        if(name.length() < len1 || name.length() > len2){
            String err_msg;
            if(attr.equals("고객번호") == true)
                err_msg = "고객번호는 4글자여야합니다.\n";
            else if(attr.equals("고객명") == true)
                err_msg = "고객명은 10글자를 넘을 수 없습니다.\n";
            else
                err_msg = attr + " 입력내용을 확인해주세요.\n";
            throw new SBException(err_msg, 4);
        }

        if(name.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]+.*"));
        else{
            String err_msg = attr + "에 특수문자를 포함할 수 없습니다.\n";
            throw new SBException(err_msg, 3);
        }
    }

    private void addCustomer(String cNum, String name, String phone, String date) throws SBException{

        int fsi = findSameInfo(cNum, name, phone, date);
        if(fsi != -1){
            String err_msg = "고객번호를 수정할 수 없습니다.\n";
            throw new SBException(err_msg, 2);
        }

        int index = findIndexCustomer(cNum);
        if(index == -1 || customerNum == 0){
            Customer c1 = new Customer(cNum, name, phone, date);
            customer.add(c1);
            customerNum++;
        }
        else{
            String err_msg = "이미 사용중인 고객번호입니다.\n";
            throw new SBException(err_msg, 5);
        }

    }

    private void deleteCustomer(String cNum) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            customer.remove(index);
            customerNum--;
        }
        else{
            String err_msg = "해당 고객은 존재하지 않습니다\n";
            throw new SBException(err_msg, 5);
        }
    }

    private void searchCustomer(String cNum) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            textCustomerName.setText(customer.elementAt(index).getName());
            String phone = customer.elementAt(index).getPhone();
            String p[] = phone.split("-");
            textCustomerPhone1.setText(p[0]);
            textCustomerPhone2.setText(p[1]);
            textCustomerPhone3.setText(p[2]);
            String date = customer.elementAt(index).getJoinDate();
            String d[] = date.split("/");
            textJoinYear.setText(d[0]);
            textJoinMonth.setText(d[1]);
            textJoinDay.setText(d[2]);
        }
        else{
            String err_msg = "해당 고객은 존재하지 않습니다\n";
            throw new SBException(err_msg, 5);
        }

    }

    private boolean addOrder(String cNum, String date, String menu) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            boolean coupon = customer.elementAt(index).order();
            Order o = new Order(cNum, date, menu);
            order.add(o);
            orderNum++;
            return coupon;
        }
        else{
            Order o = new Order("Guest", date, menu);
            order.add(o);
            orderNum++;
            return false;
        }
    }

    private void cancelOrder(String cNum, String date) throws SBException{
        int indexCustomer = findIndexCustomer(cNum);
        if(indexCustomer != -1){
            int indexOrder = findIndexOrder(cNum, date);
            if(indexOrder != -1){
                customer.elementAt(indexCustomer).orderCacel();
                order.remove(indexOrder);
                orderNum--;
            }
            else{
                String err_msg = "주문 내역이 없습니다\n";
                throw new SBException(err_msg, 5);
            }
        }
        else{
            int indexOrder = findIndexOrder("Guest", date);
            if(indexOrder != -1){
                order.remove(indexOrder);
                orderNum--;
            }
            else{
                String err_msg = "주문 내역이 없습니다\n";
                throw new SBException(err_msg, 5);
            }
        }
    }
}


// 정규식 참조 : http://devfalledinmac.tistory.com/14
// 파일 입출력 : http://hmjkor.tistory.com/451