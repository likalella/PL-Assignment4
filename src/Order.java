/**
 * Created by kaeun on 2017. 5. 25..
 */
public class Order {
    private String id;
    private String date;
    private String menu;

    public Order(String id, String date, String menu){
        this.id = id;
        this.date = date;
        this.menu = menu;
    }

    public String getID(){
        return id;
    }

    public String getDate(){
        return date;
    }

    public String getMenu(){
        return menu;
    }
}
