package com.example.pedro.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TelaEmail extends AppCompatActivity {

    TextView email,assunto,mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_email);

        email = findViewById(R.id.textEmail);
        assunto = findViewById(R.id.textAssunto);
        mensagem = findViewById(R.id.textMensagem);

    }

    //metodo para botão
    public void onClick(View v){
        if(v.getId() == R.id.botaoEnviarEmail) {
            sendEmail();

        }
        else if(v.getId() == R.id.botaoVoltar){
            this.finish();
        }



    }

    protected void sendEmail() {
        //Log.i("Send email", "");

        //String[] TO = {"someone@gmail.com"};
       // String[] CC = {"xyz@gmail.com"};
        //tentar colocar SENDTO
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        String[] campoEmail = new String[1];
        campoEmail[0] = email.getText().toString();
        String campoAssu = assunto.getText().toString();
        String campoMensagem = mensagem.getText().toString();
        emailIntent.putExtra(Intent.EXTRA_EMAIL, campoEmail);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, campoAssu);
        emailIntent.putExtra(Intent.EXTRA_TEXT, campoMensagem);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        //    Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(TelaEmail.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
