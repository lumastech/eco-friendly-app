package com.lumastech.ecoapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;


import com.lumastech.ecoapp.Models.Chat;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.Models.Quiz;
import com.lumastech.ecoapp.Models.User;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Utility {
    private final Context context;

    public static Course COURSE = null;
    public static Lesson LESSON = null;
    public static Post POST = null;
    public static Chat CHAT = null;
    public static List<Quiz> QUIZ = null;
    public static int LESSON_COUNT = 0;

    public Utility(Context context) {
        this.context = context;
    }

    // WRITE TO FILE
    public void writeToFile(String filename, String content) {
        SharedPreferences mPrefs = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(filename, content);
        editor.apply();
    }

    public String readFile(String filename){
        SharedPreferences sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(filename, null);
    }

    public void dialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        builder.setMessage(message);
        AlertDialog alertDialog;
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void checkResponse(int code, String message ){
        if (message == null) message = "Application Error";
        // if code = 401, logout
        if (code == 401) {
            logout();
        }else if(code == 400){
            dialog("Error: " + code + "Bad Request");
        }else if (code == 500) {
            dialog("Error: " + code + " Server Error");
        }else if (code == 200){
            return;
        } else {
            dialog(message);
        }
    }

    public User getUser(){
        User user = new User();
        try {
            user.name = readFile("username");
            user.email = readFile("email");
            user.image = readFile("image");
            user.imageCover = readFile("imageCover");
            user.points = readFile("points");

        } catch (Exception e) {
            dialog("Error: "+e.getLocalizedMessage());
        }
        return user;
    }
    public void putUser(User user){
        try {
            writeToFile("userid", String.valueOf(user.id));
            writeToFile("username", user.name);
            writeToFile("email", user.email);
            writeToFile("image", user.image);
            writeToFile("imageCover", user.imageCover);
            writeToFile("points", user.points);
        } catch (Exception e) {
            dialog("Error: "+e.getLocalizedMessage());
        }
    }


    public void logout() {
        this.writeToFile("token",null);
        putUser(new User());
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }



    public void setError(View view){
        view.setBackgroundResource(R.drawable.bg_input_error);
    }

    public void removeError(View view){
        view.setBackgroundResource(R.drawable.bg_outline);
    }

    public String token() {
        String token = readFile("token");
        if (token == null) logout();
        return token;
    }

    public String getUsername() {
        String str = readFile("username");
        if (str == null) logout();
        return str;
    }

    public void showDatePickerDialog(EditText etSaleDate) {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date as YYYY-MM-DD
                    String formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etSaleDate.setText(formattedDate); // Set the selected date to the EditText
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    public void vibrate(int duration){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(duration);
        }
    }

}
