package com.chehelp.connection;

/**
 * Created by Administrator on 2015-1-21.
 */
public interface Connection {

    public void sendRequest(String param);

    public String getResponse();
}
