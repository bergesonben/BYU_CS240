package edu.byu.cs.familymap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Server;
import edu.byu.cs.familymap.Model.User;

/**
 * Created by User on 7/27/2016.
 */
public class FamilyMapConnector {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static final String HTTP_GET = "GET";

    private String mHost;
    private String mPort;
    private String mBaseUrl;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    public FamilyMapConnector() {
        mHost = Server.get().getServerHost();
        mPort = Integer.toString(Server.get().getServerPort());
        mBaseUrl = "http://" + mHost + ":" + mPort;
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally{
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public boolean getLogin() {
        try {
            URL url = new URL(mBaseUrl + "/user/login");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod(HTTP_GET);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            String request = "{username: \"" + User.get().getUsername() + "\"," +
                    "password:\"" + User.get().getPassword() + "\"}";
            OutputStream os = connection.getOutputStream();
            byte[] outputInBytes = request.getBytes("UTF-8");
            os.write(outputInBytes);
            os.close();

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject root = new JSONObject(responseBodyData);
                String message = root.optString("message");
                if(message.equals("")){
                    String authorization = root.getString("Authorization");
                    String id = root.getString("personId");
                    User.get().setToken(authorization);
                    User.get().setPersonId(id);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean syncData() {

        try {
            URL url = new URL(mBaseUrl + "/person/");


            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", User.get().getToken());

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject root = new JSONObject(responseBodyData);
                JSONArray people = root.getJSONArray("data");
                FamilyMapData.get().addPeople(people);
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            URL url = new URL(mBaseUrl + "/event/");


            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", User.get().getToken());

            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject root = new JSONObject(responseBodyData);
                JSONArray people = root.getJSONArray("data");
                FamilyMapData.get().addEvents(people);
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
