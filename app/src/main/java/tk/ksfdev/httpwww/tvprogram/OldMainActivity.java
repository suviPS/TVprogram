package tk.ksfdev.httpwww.tvprogram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OldMainActivity extends AppCompatActivity {


    private WebView webView;
    private String urlMojTvHr= "https://mojtv.hr/tv-program/-10/bih.aspx";
    private String urlTVprofil= "http://m.tvprofil.net/?grupaID=4";
    private String urlKlixBa= "https://www.klix.ba/tv-program";
    private String urlAladinInfo= "http://tv.aladin.info/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //reference layout parts
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urlMojTvHr);

    }


    //for Back button in webView
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else
            super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuMojTvHr:
                webView.loadUrl(urlMojTvHr);
                break;
            case R.id.menuTVprofilNet:
                webView.loadUrl(urlTVprofil);
                break;
            case R.id.menuKlixBa:
                webView.loadUrl(urlKlixBa);
                break;

            case R.id.menuAladinInfo:
                webView.loadUrl(urlAladinInfo);
                break;

            case R.id.menuRefresh:
                webView.reload();
                break;
            case R.id.menuAbout:
                //classic about message, maybe new layout for notification
                new AlertDialog.Builder(this)
                        .setTitle("\tInfo")
                        .setMessage("Info nije potreban...\n\nCreated by \t/*-Petar Suvajac-*/")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return true;
    }





}
