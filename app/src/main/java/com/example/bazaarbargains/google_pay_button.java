package com.example.bazaarbargains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bazaarbargains.databinding.ActivityGooglePayButtonBinding;


public class google_pay_button extends AppCompatActivity {
private ActivityGooglePayButtonBinding binding;
public static final String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
String amount, name = "Google Bank", upiid = "google@transaction", transac ="Paying", status;

int GOOGLE_PAY_REQUEST_CODE = 123;

Uri uni_ri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGooglePayButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.googlePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = binding.amounttext.getText().toString().replace("$","");
                if(!amount.isEmpty()){
                    uni_ri = getpayment(name,upiid,transac,amount);
                }
                PayWithGooglePay();
            }
        });

    }
    private void PayWithGooglePay(){
     if (isAppInstalled(this, GOOGLE_PAY_PACKAGE_NAME )){
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setData(uni_ri);
         intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
         startActivityForResult(intent,GOOGLE_PAY_REQUEST_CODE);
     }
     else{
         Toast.makeText(google_pay_button.this, "Please Install Google Pay", Toast.LENGTH_SHORT).show();
     }
    }

    public void ActivityResult(int reqCode, int rescode, Intent intent){
        super.onActivityResult(reqCode,rescode,intent);
        if(intent!= null){
            status = intent.getStringExtra("Status").toLowerCase();
        }
        if((RESULT_OK == reqCode) && status.equals("success")){
            Toast.makeText(google_pay_button.this, "Transaction Successful", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(google_pay_button.this, "Transaction Unsuccessful",Toast.LENGTH_SHORT).show();
        }
    }
    private static boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
           return false;
        }

    }
    private static Uri getpayment(String name, String upiid, String transac, String amount){
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa",upiid)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", transac)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();

    }
}