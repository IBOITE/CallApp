package com.ibo.mycall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=findViewById(R.id.button);

        if(Build.VERSION.SDK_INT>=23){
            if(checkPermission()){
                Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();
            }else {
                requestPermission();
            }
        }
    }

    public boolean checkPermission(){
        int CallPermissionResult= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        return CallPermissionResult== PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    boolean CallPermissioin= grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(CallPermissioin){
                        Toast.makeText(MainActivity.this,"Permission accepted",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,"Permission denied",Toast.LENGTH_SHORT).show();
                        button.setEnabled(false);
                    }
                    break;
                }
        }
    }

    public void call(View view){
        final EditText editText=findViewById(R.id.editTextPhone);
        String phoneNum=editText.getText().toString();
        if(!TextUtils.isEmpty(phoneNum)){
            String dial="tel:"+phoneNum;
            if(checkSelfPermission(Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }else {
            Toast.makeText(MainActivity.this,"please put a phone number",Toast.LENGTH_SHORT).show();

        }
    }
}