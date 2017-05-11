package edu.byu.cs.familymap.Model;

/**
 * Created by User on 7/27/2016.
 */
public class User {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static User sUser;

    private String mUsername;
    private String mPassword;
    private String mToken;
    private String mPersonId;
    private String mName;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    private User() {
        mUsername = "";
        mPassword = "";
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public static User get() {
        if (sUser == null) {
            sUser = new User();
        }
        return sUser;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getToken() {
        return mToken;
    }

    public String getPersonId() {
        return mPersonId;
    }

    public String getName() {
        return mName;
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public void setPersonId(String personId) {
        mPersonId = personId;
    }

    public void setName(String name) {
        mName = name;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
