package com.lumastech.ecoapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText emailEdit = null;
    private EditText passwordEdit;
    private EditText passwordConfirmEdit;
    private Button registerBtn = null;
    Utility utility;
    Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = RegisterActivity.this;
        utility  = new Utility(context);

        nameEdit = findViewById(R.id.name);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        passwordConfirmEdit = findViewById(R.id.password_confirm);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String passwordConfirm = passwordConfirmEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                utility.removeError(nameEdit);
                utility.removeError(emailEdit);
                utility.removeError(passwordEdit);
                utility.removeError(passwordConfirmEdit);

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !passwordConfirm.isEmpty()){
                    if(!password.equals(passwordConfirm)){
                        utility.setError(passwordEdit);
                        utility.setError(passwordConfirmEdit);
                        Toast.makeText(context, "Password and Password Confirm Don't match!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    LoginRequest request = new LoginRequest();
                    request.setName(name);
                    request.setEmail(email);
                    request.setPassword(password);
                    registerUser(request);
                }else {
                    if (name.isEmpty()){
                        utility.setError(nameEdit);
                    }
                    if (email.isEmpty()){
                        utility.setError(emailEdit);
                    }
                    if (password.isEmpty()){
                        utility.setError(passwordEdit);
                    }
                    if (passwordConfirm.isEmpty()){
                        utility.setError(passwordConfirmEdit);
                    }
                }
            }
        });
    }

    public void registerUser(LoginRequest request){
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
            Call<ApiResponse<AuthResponse>> registerResponseCall = Api.apiCall().register(request);
            registerResponseCall.enqueue(new Callback<ApiResponse<AuthResponse>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<AuthResponse>> call, @NonNull Response<ApiResponse<AuthResponse>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body().getData();
                        String token = authResponse.token;
                        User user = authResponse.user;

                        // Save token and user data
                        utility.putUser(user);
                        utility.writeToFile("token", token);
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