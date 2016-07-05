package com.gmail.austintingwork.googlefirebase;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GET KEY HASH
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.gmail.austintingwork.googlefirebase", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //String something = new String(Base64.encodeBytes(md.digest()));
                String KeyResult = new String(Base64.encode(md.digest(), 0));
                Log.d("KeyHash&ID", "KeyHash :" + KeyResult);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());

        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }
}
