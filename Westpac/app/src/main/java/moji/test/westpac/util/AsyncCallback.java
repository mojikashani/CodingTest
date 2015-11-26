package moji.test.westpac.util;

/**
 * Created by mojtaba on 11/26/2015.
 */

public interface AsyncCallback<T> {

    public void onSuccess(T result);
    public void onError(WestPacError err);
}



