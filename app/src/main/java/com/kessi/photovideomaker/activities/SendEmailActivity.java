package com.kessi.photovideomaker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.kessi.photovideomaker.R;
import com.kessi.photovideomaker.util.KSUtil;

import java.util.Properties;
import java.util.regex.Pattern;

public class SendEmailActivity extends AppCompatActivity {

    EditText edt_name, edt_email, edt_content;
    Button btn_send;
    ImageView btn_back;
    String content, name, email;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.send_name, RegexTemplate.NOT_EMPTY, Integer.parseInt("Please input your name"));
        awesomeValidation.addValidation(this, R.id.send_content, RegexTemplate.NOT_EMPTY, Integer.parseInt("Please input your request"));
        awesomeValidation.addValidation(this, R.id.send_email, Patterns.EMAIL_ADDRESS, Integer.parseInt("Please input valid email"));

        String username = "yennhiluu2112@gmail.com";
        String password = "Nhinhi2112$";

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
                        String m = "Hello! My name is "+name+"\n"+content;
                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.port", "587");
                        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                        try{
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(username));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(edt_email.getText().toString()));
                            message.setSubject("Sending email without opening gmail app");
                            message.setText(content);
                            Transport.send(message);
                            Toast.makeText(getApplicationContext(), "Email sent successfully!", Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e){
                            e.printStackTrace();
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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