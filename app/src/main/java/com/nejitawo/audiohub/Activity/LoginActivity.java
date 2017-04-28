package com.nejitawo.audiohub.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import com.nejitawo.audiohub.R;

import java.util.List;


/**
 * Created by Neji on 11/17/2014.
 */
public class LoginActivity extends Activity {
    TextView username;
    TextView password;
Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (TextView)findViewById(R.id.txtlogusername);
        password = (TextView)findViewById(R.id.txtlogpass);

        btnCreate = (Button)findViewById(R.id.btnSignUp);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                intent.putExtra("data","http://audiohub.mymegalibrary.com/register/join.aspx");
                startActivity(intent);
            }
        });

    }


    public void loginClickListener(View view){
        if(!username.getText().toString().isEmpty()){

        } else {
username.setError("Enter Username");
            return;
        }

        if(!password.getText().toString().isEmpty()){

        } else {
password.setError("Enter Password");
            return;
        }
        final ProgressDialog ringProgressDialog =
                ProgressDialog.show(LoginActivity.this, "Please wait ...", "Login in progress..", true);
        ringProgressDialog.setCancelable(false);

        ParseUser.logInInBackground(username.getText().toString().toLowerCase(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    // Hooray! The user is logged in.
                    //Now check if user has been approved
                    ParseQuery<ParseObject> isApproved = new ParseQuery<ParseObject>("Player");
                    isApproved.whereEqualTo("username",username.getText().toString().toLowerCase());
                    isApproved.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e==null && objects.size()>0){

                                ringProgressDialog.dismiss();
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();

                            } else{
                                ringProgressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "YOUR ACCOUNT HAS NOT YET BEEN APPROVED. TRY AGAIN SHORTLY",Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    ringProgressDialog.dismiss();
                    showFailDialog();

                }
            }
        });

    }

    private void showFailDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Error..");

        // Setting Dialog Message
        alertDialog.setMessage("Login failed, invalid user credentials or check data connection");

        // Setting Icon to Dialog
        alertDialog.setIcon(android.R.drawable.ic_dialog_info);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                // Toast.makeText(getApplicationContext(),"New Location created", Toast.LENGTH_SHORT).show();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


}
