/**
 * Created by kaeun on 2017. 5. 25..
 */
public class SBException extends Exception{
    private final int ERR_CODE;
    SBException(String msg, int err_code){
        super(msg);
        ERR_CODE = err_code;
    }

    SBException(String msg){
        this(msg, 7);
    }

    public int getErrCode(){
        return ERR_CODE;
    }
}


// 참고 http://devbox.tistory.com/entry/Java-%EC%98%88%EC%99%B8-%EB%A7%8C%EB%93%A4%EA%B8%B0