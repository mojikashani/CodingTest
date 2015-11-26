package moji.test.westpac.util;

/**
 * Created by mojtaba on 11/26/2015.
 */
public class WestPacError {
    private String errorMsg;
    private int errorCode;
    public WestPacError(){
        errorCode = 0;
        errorMsg = "";
    }

    public WestPacError(int errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
