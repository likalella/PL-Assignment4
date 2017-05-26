import javax.swing.*;

/**
 * Created by kaeun on 2017. 5. 25..
 */
public class Manage implements Runnable {
    int opt;
    String cNum;
    String name;
    String p1, p2, p3;
    String year, month, day;
    String menu;

    public Manage(int opt, String cNum){
        this.opt = opt;
        this.cNum = cNum;
    }

    public Manage(int opt, String cNum, String name, String p1, String p2, String p3, String year, String month, String day){
        this.opt = opt;
        this.cNum = cNum;
        this.name = name;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Manage(int opt, String cNum, String year, String month, String day, String menu){
        this.opt = opt;
        this.cNum = cNum;
        this.year = year;
        this.month = month;
        this.day = day;
        this.menu = menu;
    }


    public void run() {
        if(opt == 1){   // order
                try {
                    //날짜
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
                    checkIsNull("고객번호", cNum);
                    checkValidName("고객번호", cNum, 4, 4);
                    // 메뉴
                    checkIsNull("메뉴", menu);
                    checkValidName("메뉴", menu, 1, 10);
                    boolean coupon = addOrder(cNum, date, menu);
                    if(coupon == true){
                        String msg = "쿠폰이 도착했습니다.";
                        String title = "COUPON";
                        JOptionPane.showMessageDialog(null, msg, title,
                                JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }
                catch (SBException e){
                    String title = "ERR : " + e.getErrCode();
                    String msg = e.getMessage();
                    JOptionPane.showMessageDialog(null, msg, title,
                            JOptionPane.OK_OPTION, null);
                }

        }
        else if(opt == 2){  // ordercancel
            try{
                // 날짜
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
                checkIsNull("고객번호", cNum);
                checkValidName("고객번호", cNum, 4, 4);

                cancelOrder(cNum, date);

            }
            catch (SBException e){
                String title = "ERR : " + e.getErrCode();
                String msg = e.getMessage();
                JOptionPane.showMessageDialog(null, msg, title,
                        JOptionPane.OK_OPTION, null);
            }
        }
        else if(opt == 3){
            try {
                // 고객번호
                checkIsNull("고객번호", cNum);
                checkValidName("고객번호", cNum, 4, 4);
                // 고객명
                checkIsNull("고객명", name);
                checkValidName("고객명", name, 1, 10);
                // 전화번호
                checkIsNull("전화번호", p1);
                checkIsNull("전화번호", p2);
                checkIsNull("전화번호", p3);
                checkIsCorrectNumForm("전화번호", p1, 2, 3);
                checkIsCorrectNumForm("전화번호", p2, 3, 4);
                checkIsCorrectNumForm("전화번호", p3, 4, 4);
                String phone = p1 + "-" + p2 + "-" + p3;
                // 가입일
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
            }
            catch (SBException e){
                String title = "ERR : " + e.getErrCode();
                String msg = e.getMessage();
                JOptionPane.showMessageDialog(null, msg, title,
                        JOptionPane.OK_OPTION, null);
            }
        }
        else if(opt == 4){
            try{
                checkIsNull("고객번호", cNum);
                checkValidName("고객번호", cNum, 4, 4);
                searchCustomer(cNum);
            }
            catch (SBException e){
                String title = "ERR : " + e.getErrCode();
                String msg = e.getMessage();
                JOptionPane.showMessageDialog(null, msg, title,
                        JOptionPane.OK_OPTION, null);
            }
        }
        else if(opt == 5){
            try{
                checkIsNull("고객번호", cNum);
                checkValidName("고객번호", cNum, 4, 4);
                deleteCustomer(cNum);
            }
            catch (SBException e){
                String title = "ERR : " + e.getErrCode();
                String msg = e.getMessage();
                JOptionPane.showMessageDialog(null, msg, title,
                        JOptionPane.OK_OPTION, null);
            }
        }
    }

    private int findIndexCustomer(String cNum){
        int rst=-1;
        for(int i=0; i<SnackBar.customerNum; i++){
            if(SnackBar.customer.elementAt(i).getID().equals(cNum) == true){
                rst = i;
                break;
            }
        }
        return rst;
    }

    private int findIndexOrder(String cNum, String date){
        int rst = -1;
        for(int i=0; i<SnackBar.orderNum; i++){
            if((SnackBar.order.elementAt(i).getID().equals(cNum) == true)
                    &&(SnackBar.order.elementAt(i).getDate().equals(date) == true)){
                rst = i;
                break;
            }
        }
        return rst;
    }

    private int findSameInfo(String cNum, String name, String phone, String date){
        int rst = -1;
        for(int i=0; i<SnackBar.customerNum; i++){
            String nu = SnackBar.customer.elementAt(i).getID();
            String n = SnackBar.customer.elementAt(i).getName();
            String p = SnackBar.customer.elementAt(i).getPhone();
            String d = SnackBar.customer.elementAt(i).getJoinDate();

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
        if(index == -1 || SnackBar.customerNum == 0){
            Customer c1 = new Customer(cNum, name, phone, date);
            SnackBar.customer.add(c1);
            SnackBar.customerNum++;
        }
        else{
            String err_msg = "이미 사용중인 고객번호입니다.\n";
            throw new SBException(err_msg, 5);
        }

    }

    private void deleteCustomer(String cNum) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            SnackBar.customer.remove(index);
            SnackBar.customerNum--;
        }
        else{
            String err_msg = "해당 고객은 존재하지 않습니다\n";
            throw new SBException(err_msg, 5);
        }
    }

    private void searchCustomer(String cNum) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            String title = "Search Result";
            String msg = "고객 번호 : " + SnackBar.customer.elementAt(index).getID()
                    + "\n고객명 : " + SnackBar.customer.elementAt(index).getName()
                    + "\n전화번호 : " + SnackBar.customer.elementAt(index).getPhone()
                    + "\n가입일 : " + SnackBar.customer.elementAt(index).getJoinDate();
            JOptionPane.showMessageDialog(null, msg, title,
                    JOptionPane.INFORMATION_MESSAGE, null);
        }
        else{
            String err_msg = "해당 고객은 존재하지 않습니다\n
            throw new SBException(err_msg, 5);
        }

    }

    private boolean addOrder(String cNum, String date, String menu) throws SBException{
        int index = findIndexCustomer(cNum);
        if(index != -1){
            boolean coupon = SnackBar.customer.elementAt(index).order();
            Order o = new Order(cNum, date, menu);
            SnackBar.order.add(o);
            SnackBar.orderNum++;
            return coupon;
        }
        else{
            Order o = new Order("Guest", date, menu);
            SnackBar.order.add(o);
            SnackBar.orderNum++;
            return false;
        }
    }

    private void cancelOrder(String cNum, String date) throws SBException{
        int indexCustomer = findIndexCustomer(cNum);
        if(indexCustomer != -1){
            int indexOrder = findIndexOrder(cNum, date);
            if(indexOrder != -1){
                SnackBar.customer.elementAt(indexCustomer).orderCacel();
                SnackBar.order.remove(indexOrder);
                SnackBar.orderNum--;
            }
            else{
                String err_msg = "주문 내역이 없습니다\n";
                throw new SBException(err_msg, 5);
            }
        }
        else{
            int indexOrder = findIndexOrder("Guest", date);
            if(indexOrder != -1){
                SnackBar.order.remove(indexOrder);
                SnackBar.orderNum--;
            }
            else{
                String err_msg = "주문 내역이 없습니다\n";
                throw new SBException(err_msg, 5);
            }
        }
    }
}
