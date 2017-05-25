import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by kaeun on 2017. 5. 16..
 * 참조 : www.pmguda.com/460
 */
public class JTextFieldLimit extends PlainDocument {
    private int limit;
    public JTextFieldLimit(int limit){
        super();
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(str == null)
            return;

        if(getLength() + str.length() <= limit)
            super.insertString(offset, str, attr);
    }
}
