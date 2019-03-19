package com.example.pedro.helloworld;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.icu.text.DateIntervalInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

import static android.content.Intent.ACTION_DIAL;
import static android.provider.LiveFolders.INTENT;

public class MainActivity extends AppCompatActivity {

    //Pessoa contato = new Pessoa();
    Intent newInt;

    TextView olamundo;
    TextView tempo;
    TextView ligacao;
    TextView mensagem;

    long tempoNaPausa = 0;
    long tempoDepois = 0;


    private boolean isPaused = false;
    private boolean isCanceled = false;

    //Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        olamundo = findViewById(R.id.olamundo);
        tempo = findViewById(R.id.textTime);
        mensagem = findViewById(R.id.textMensagem);
        ligacao = findViewById(R.id.textLigar);
        olamundo.setText("Ola camaradas");



        Log.i("MeuApp", "bem vindo ao Android Studio"); //fazer log

        /*Button botao = findViewById(R.id.button1);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                olamundo.setText("Clicado");
                //dialNumber();
            }
        });*/
    }


    @Override
    protected void onPause(){
        super.onPause();
        olamundo.setText("Aplicação Pausada");
        tempoNaPausa = System.currentTimeMillis();
        //System.out.println("temponapausa: " + tempoNaPausa);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        olamundo.setText("Aplicação Iniciada");
        tempoDepois = System.currentTimeMillis();
        //DateTimeDifference(tempoNaPausa,tempoDepois);
        tempo.setText(DateTimeDifference(tempoDepois, tempoNaPausa));
        //System.out.println("depois: " + tempoDepois);

    }

    //metodo para botão
    public void onClick(View v){
        if(v.getId() == R.id.botaoLigar) {
            olamundo.setText("Clicado");
            dialNumber();
        }
        else if(v.getId() == R.id.botaoTelaEmail){
            olamundo.setText("email clicado");
            Intent itDuasTelas = new Intent(this, TelaEmail.class);
            startActivity(itDuasTelas);
        }
        else if(v.getId() == R.id.botaoCriarContato) {
            Intent itTelaCriarContato = new Intent(this, telaContato.class);
            startActivity(itTelaCriarContato);
        }
        else if(v.getId() == R.id.botaoRedeSocial) {
            Intent itTelaRedeSocial = new Intent(this, redeSocial.class);
            startActivity(itTelaRedeSocial);
        }
    }



    public static String DateTimeDifference(long currentTime, long previousTime) {

        return "" + (((currentTime - previousTime) / 1000) / 60) + " : " + (((currentTime - previousTime) / 1000) % 60);

    }

    public void dialNumber() {
        //TextView textView = (TextView) findViewById(R.id.number_to_call);
        String numeroTel = ("tel:04131" + ligacao.getText().toString());
        //String numeroTel = ("tel:04131975155160");
        // Use format with "tel:" and phone number to create phoneNumber.
        //String phoneNumber = String.format("tel: %s", textView.getText().toString());
        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL); // ACTION_CALL liga instantaneo.
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(numeroTel));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            //Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
        }
    }

    public void onClickWhatsApp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = mensagem.getText().toString();
            //String text = "Estou enviando esta mensagem pelo meu aplicativo";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            //startActivity(waIntent);
            //startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

        //clean textbox
        mensagem.setText("");

    }



}
