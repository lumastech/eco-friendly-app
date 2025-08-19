package com.lumastech.ecoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.AuthResponse;
import com.lumastech.ecoapp.Models.LoginRequest;
import com.lumastech.ecoapp.Models.User;


import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText = null, passworEditText = null;
    private Button loginBtn = null;
    Utility utility;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;
        utility = new Utility(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameEditText = findViewById(R.id.username);
        passworEditText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernameEditText.getText().toString();
                String password = passworEditText.getText().toString();

                utility.removeError(usernameEditText);
                utility.removeError(passworEditText);

                if(!email.isEmpty() && !password.isEmpty()){
                    LoginRequest request = new LoginRequest();
                    request.setEmail(email);
                    request.setPassword(password);
                    loginUser(request);
                }else {
                    if (email.isEmpty()) utility.setError(usernameEditText);
                    if (password.isEmpty()) utility.setError(passworEditText);
                }
            }
        });
    }


    public void loginUser(LoginRequest request){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        AlertDialog alertDialog;

        alertDialog = builder.create();
        ProgressDialog dialog = ProgressDialog.show(context, "",
                "Please wait...", true);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            Call<ApiResponse<AuthResponse>> registerResponseCall = Api.apiCall().login(request);
            registerResponseCall.enqueue(new Callback<ApiResponse<AuthResponse>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<AuthResponse>> call, @NonNull Response<ApiResponse<AuthResponse>> response) {
                    if (response.code() == 401) {
                        utility.dialog("Invalid email or password");
                        dialog.dismiss();
                        return;
                    }
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body().getData();
                        utility.putUser(authResponse.user);
                        utility.writeToFile("token", authResponse.token);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<AuthResponse>> call, @NonNull Throwable t) {
                    String message = "Error : "+t.getLocalizedMessage();
                    if (Objects.equals(t.getLocalizedMessage(), "End of input at line 1 column 1 path $")){
                        message = "We are having trouble connecting to the internet! Please make sure you have a working Internet connection.";
                    }
                    utility.dialog(message);
                    dialog.dismiss();
                    builder.setMessage(message);
                    alertDialog.show();
                }
            });
        }else{
            dialog.dismiss();
            utility.dialog("There is no Internet connection!");
        }
    }
}