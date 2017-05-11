package edu.byu.cs.familymap.Model;

/**
 * Created by User on 7/26/2016.
 */
public class Server {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private String mServerHost;
    private int mServerPort;
    private static Server sServer;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    private Server() {
        mServerHost = "";
        mServerPort = 0;
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public static Server get() {
        if (sServer == null) {
            sServer = new Server();
        }
        return sServer;
    }

    public String getServerHost() {
        return mServerHost;
    }

    public int getServerPort() {
        return mServerPort;
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void setServerHost(String serverHost) {
        mServerHost = serverHost;
    }

    public void setServerPort(int serverPort) {
        mServerPort = serverPort;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
