package com.naosteam.slideshowmaker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.naosteam.slideshowmaker.R;
import com.naosteam.slideshowmaker.util.AdManager;
import com.naosteam.slideshowmaker.util.KSUtil;

public class SendEmailActivity extends AppCompatActivity {

    EditText edt_name, edt_email, edt_content;
    Button btn_send;
    ImageView btn_back;
    String content, name;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.send_name, RegexTemplate.NOT_EMPTY, R.string.name_alert);
        awesomeValidation.addValidation(this, R.id.send_content, RegexTemplate.NOT_EMPTY, R.string.content_alert);

        edt_email = findViewById(R.id.send_email);
        edt_name = findViewById(R.id.send_name);
        edt_content = findViewById(R.id.send_content);
        btn_back = findViewById(R.id.btn_back12);
        btn_send = findViewById(R.id.btn_send_email);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    KSUtil.Bounce(SendEmailActivity.this, btn_send);
                    if (awesomeValidation.validate()){
                        content = edt_content.getText().toString();
                        name = edt_name.getText().toString();
                        String m = "Hello! My name is "+name+". I would like to use your service. Please check my mail.";

                        String[] emails = {""};
                        emails[0] = getResources().getString(R.string.email_admin);

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:"));
                        intent.putExtra(Intent.EXTRA_EMAIL, emails);
                        intent.putExtra(Intent.EXTRA_SUBJECT,m);
                        intent.putExtra(Intent.EXTRA_TEXT,content);

                        if(intent.resolveActivity(getPackageManager())!=null){
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SendEmailActivity.this, "No app is installed", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        btn_back.setOnClickListener(arg0 -> {
            // TODO Auto-generated method stub
            KSUtil.Bounce(this, btn_back);
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        AdManager.showAdmobInterAd(SendEmailActivity.this, new AdManager.InterAdsListener() {
            @Override
            public void onClick() {
                SendEmailActivity.super.onBackPressed();
            }
        });

    }

    //    int FLAG_VIDEO = 21;
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (KSUtil.fromAlbum) {
//            Intent intent = new Intent();
//            setResult(FLAG_VIDEO, intent);
//        } else {
//            gotoMain();
//        }
//    }
//
//    public void gotoMain() {
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
}