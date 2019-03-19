package com.example.pedro.helloworld;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class redeSocial extends AppCompatActivity {

    Button botaoCompLink, botaoCompFoto;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    TextView txtLink, txtComentario;

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();

            if(ShareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                        shareDialog.show(content);
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_rede_social);

        botaoCompLink = (findViewById(R.id.botaoCompLink));
        botaoCompFoto = (findViewById(R.id.botaoCompFoto));

        //inicializando facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        txtLink = (EditText) findViewById(R.id.txtLink);
        txtComentario = (EditText) findViewById(R.id.txtComentario);



        botaoCompLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(redeSocial.this, "@string/compComSucesso", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(redeSocial.this, "@string/compCancelado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(redeSocial.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote(""+txtComentario.getText().toString()+"")
                        .setContentUrl(Uri.parse(""+txtLink.getText().toString()+""))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class))
                {
                    shareDialog.show(linkContent);
                }
            }
        });

        botaoCompFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(redeSocial.this, "@string/compComSucesso", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(redeSocial.this, "@string/compCancelado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(redeSocial.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


                Picasso.with(getBaseContext())
                        .load(txtLink.getText().toString())
                        .into(target);
            }
        });

    }

    public void onClick(View v){
        if(v.getId() == R.id.botaoVoltar){
            this.finish();
        }
    }

}
