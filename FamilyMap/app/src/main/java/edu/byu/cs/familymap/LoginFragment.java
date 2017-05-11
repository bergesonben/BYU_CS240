package edu.byu.cs.familymap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Server;
import edu.byu.cs.familymap.Model.User;

/**
 * Created by User on 7/26/2016.
 */
public class LoginFragment extends Fragment {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private Button mSignInButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mServerHostEditText;
    private EditText mServerPortEditText;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           REACTIVE METHODS
//**************************************************************************************************
//region REACTIVE METHODS

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mUsernameEditText = (EditText)v.findViewById(R.id.username_edit_text);
        mPasswordEditText = (EditText)v.findViewById(R.id.password_edit_text);
        mServerHostEditText = (EditText)v.findViewById(R.id.server_host_edit_text);
        mServerHostEditText.setText("192.168.2.53");
        mServerPortEditText = (EditText)v.findViewById(R.id.server_port_edit_text);
        mServerPortEditText.setText("8080");

        mSignInButton = (Button)v.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String host = mServerHostEditText.getText().toString();
                String port = mServerPortEditText.getText().toString();

                if (username.equals("") || password.equals("") || host.equals("") || port.equals("")) {
                    Toast.makeText(getActivity(), R.string.fields_empty, Toast.LENGTH_SHORT).show();
                } else {
                    User.get().setUsername(username);
                    User.get().setPassword(password);
                    Server.get().setServerHost(host);
                    Server.get().setServerPort(Integer.valueOf(port));
                    FamilyMapData.get().clearAll();
                    new SendLoginRequestTask().execute();
                }
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.blank_menu, menu);
    }

//endregion REACTIVE METHODS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS

    protected void switchFragments() {
        MapFragment mapFragment = MapFragment.newInstance("", MainActivity.ACTIVITY_NAME);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment, null)
                .addToBackStack(null)
                .commit();
    }

//endregion PRIVATE METHODS


//**************************************************************************************************
//                                           PRIVATE CLASS
//**************************************************************************************************
//region PRIVATE CLASS

    private class SendLoginRequestTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return new FamilyMapConnector().getLogin();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(getActivity(), R.string.login_failure, Toast.LENGTH_SHORT).show();
            } else {
                new DataSyncTask().execute();
            }
        }
    }

    private class DataSyncTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return new FamilyMapConnector().syncData();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(getActivity(), R.string.data_sync_error, Toast.LENGTH_SHORT).show();
            } else {
                switchFragments();
            }
        }
    }

//endregion PRIVATE CLASS
}
