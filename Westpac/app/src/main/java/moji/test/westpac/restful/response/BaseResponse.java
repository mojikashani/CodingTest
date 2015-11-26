package moji.test.westpac.restful.response;

import moji.test.westpac.util.WestPacError;

/**
 * Created by mojtaba on 11/26/2015.
 */
public class BaseResponse  {

    public WestPacError error;

    public boolean hasError() {
        return error.getErrorCode() != 0;
    }

    public BaseResponse() {
        error = new WestPacError();
    }
}
