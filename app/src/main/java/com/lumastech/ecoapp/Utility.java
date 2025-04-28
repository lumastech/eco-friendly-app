package com.lumastech.ecoapp;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class Utility {
    private final Context context;

    public static Course COURSE = null;
    public static Lesson LESSON = null;
    public static Post POST = null;
    public static Chat CHAT = null;

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

    public void logout() {
        writeToFile("username", null);
        writeToFile("token", null);
    }



    public void generalDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.setMessage(message);
        AlertDialog alertDialog;
        alertDialog = builder.create();
        alertDialog.show();
    }

//    public User getProfile(){
//        User user = new User();
//        return user;
//    }



    public void setError(View view){
        view.setBackgroundResource(R.drawable.bg_input_error);
    }

    public void removeError(View view){
        view.setBackgroundResource(R.drawable.bg_outline);
    }

    public void putFromJson(){
        // this function will be used to save class object into string
    }
    public String getFromJson(String filename, String string){
        String value = "";
        try {
            String profileText = readFile(filename);
            if(!Objects.equals(profileText, "")) {
                JSONObject userObject = new JSONObject(profileText);
                value = userObject.getString(string);
            }
        }catch (Exception ignored){}

        return  value;
    }

    public String token() {
        String token = readFile("token");
        if (token == null){
            logout();
            return null;
        }
        return token;
    }

    public String getUsername() {
        String str = readFile("username");
        if (str == null){
            logout();
        }
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
