package com.example.jatin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        if(isNetworkAvailable(MainActivity.this)){
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Loading...Please wait...");
            webView.setWebViewClient(new WebViewClient(){

                @Override
                public void onPageFinished(WebView view, String url) {
                    webView.loadUrl("javascript:(function() { " +
                            "document.querySelector('[role=\"toolbar\"]').remove();})()");
                    progressDialog.dismiss();
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    progressDialog.dismiss();
                    try {
                        webView.stopLoading();
                    } catch (Exception e) {
                    }

                    if (webView.canGoBack()) {
                        webView.goBack();
                    }

                    //webView.loadUrl("about:blank");
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Check your internet connection and try again.");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    alertDialog.show();
                    super.onReceivedError(webView, errorCode, description, failingUrl);
                }

            });
            webView.loadUrl("http://secure-nots.herokuapp.com/home");

        }else {
            alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            String title = "Oops !!!";
            String desc = "No Internet Connection Available :(";
            String ok = "Try Again";
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder
                    .setMessage(desc)
                    .setCancelable(true)
                    .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
            alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}