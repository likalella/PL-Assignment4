/**
 * Created by kaeun on 2017. 5. 16..
 */
public class Customer {
    private String id;
    private String name;
    private String phone;
    private String joinDate;
    private int orderNum;
    private int totalNum;

    public Customer(String id, String name, String phone, Strin gg joinDate){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.joinDate = joinDate;
        orderNum = 0;
        totalNum = 0;
    }

    public Customer(String id, String name, String phone, String joinDate, int orderNum, int totalNum){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.joinDate = joinDate;
        this.orderNum = orderNum;
        this.totalNum = totalNum;
    }

    public boolean order(){
        totalNum++;
        orderNum++;
        if(orderNum == 3){
            orderNum=0;
            return true;
        }
        else {
            return false;
        }
    }

    public void orderCacel(){
        totalNum--;
        orderNum = totalNum % 3;
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getJoinDate(){
        return joinDate;
    }

    public void setJoinDate(String joinDate){
        this.joinDate = joinDate;
    }

    public int getOrderNum(){
        return orderNum;
    }

    public int getTotalNum() {
        return totalNum;
    }
}
