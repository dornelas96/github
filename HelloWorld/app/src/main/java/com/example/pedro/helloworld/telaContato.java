package com.example.pedro.helloworld;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class telaContato extends AppCompatActivity {

    EditText txtNome;
    EditText txtTelefone;
    EditText txtEmail;

    Pessoa pessoa = new Pessoa();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_contato);

        txtNome = (EditText) findViewById(R.id.textNomeContato);
        txtTelefone = (EditText) findViewById(R.id.textTelefoneContato);
        txtEmail = (EditText) findViewById(R.id.textEmailContato);


    }


    public void onClick(View v) {
        if (v.getId() == R.id.botaoInserirContato) {
            pessoa.setNome(txtNome.getText().toString());
            pessoa.setTelefone(txtTelefone.getText().toString());
            pessoa.setEmail(txtEmail.getText().toString());
            insertContact(pessoa);

        } else if (v.getId() == R.id.botaoVoltar) {
            this.finish();
        }
        else if(v.getId() == R.id.botaoEnviarMsg){
            enviarMsgWpp(pessoa);
            //sendWpp(pessoa);
        }
        else if(v.getId() == R.id.botaoEnviarEmail){
            sendEmail(pessoa);
        }
    }

    public void insertContact(Pessoa pessoa) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        //Intent intent = new Intent(Intent.ACTION_INSERT);
        //intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, pessoa.getNome());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, pessoa.getTelefone());
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, pessoa.getEmail());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void enviarMsgWpp(Pessoa pessoa) {
        String message = "Contato adicionado com sucesso";
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);

        //Directly send to specific mobile number...
        String smsNumber = ("5531" + pessoa.getTelefone());//Number without with country code and without '+' prifix
        whatsappIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
        if (whatsappIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(getBaseContext(), "Whatsapp not installed.", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(whatsappIntent);
    }

    protected void sendEmail(Pessoa pessoa) {
        //Log.i("Send email", "");

        //String[] TO = {"someone@gmail.com"};
        // String[] CC = {"xyz@gmail.com"};
        //tentar colocar SENDTO
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        String[] campoEmail = new String[1];
        campoEmail[0] = pessoa.getEmail();
        String campoAssu = "Contato";
        String campoMensagem = "Contato adicionado com sucesso";
        emailIntent.putExtra(Intent.EXTRA_EMAIL, campoEmail);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, campoAssu);
        emailIntent.putExtra(Intent.EXTRA_TEXT, campoMensagem);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //    Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getBaseContext(),
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    public void sendWpp(Pessoa pessoa) {
        String message = "Contato adicionado com sucesso";
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("jid", "5531" + pessoa.getTelefone() + "@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }*/

}
