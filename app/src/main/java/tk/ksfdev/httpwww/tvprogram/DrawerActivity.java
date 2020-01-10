package tk.ksfdev.httpwww.tvprogram;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;
    private ActionBarDrawerToggle toggleListener;
    private DrawerLayout drawerLayout;

    private String instanceTag = "WEBLOCATION";

    //websites
    private String urlMojTvHr= "https://mojtv.hr/tv-program/-10/bih.aspx";
    private String urlTVprofil= "http://m.tvprofil.net/?grupaID=4";
    private String urlKlixBa= "https://www.klix.ba/tv-program";
    private String urlAladinInfo= "http://tv.aladin.info/";
    private String urlArenaSport= "http://www.tvarenasport.com/tv-program";
    private String urlSportKlub= "http://sportklub.rs/Programska-sema";
    private String urlEurosport= "https://www.eurosport.rs/tvschedule.shtml";

    //TODO: add SwipeRefreshLayout (remove menu refresh icon)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggleListener = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggleListener);
        toggleListener.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //reference layout parts
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        if(savedInstanceState!=null)
            webView.loadUrl(savedInstanceState.getString(instanceTag));
        else
            webView.loadUrl(urlTVprofil);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            //Do nothing
        } else {
            //No internet connection
            webView.setVisibility(View.INVISIBLE);
            CoordinatorLayout coordinatorLayout= findViewById(R.id.rootLayout);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.try_again), view -> {
                        Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                        startActivity(intent);
                        finish();
                    });

            //Changing button text color
            snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbarTextColor));

            // Changing text color
            TextView textView = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.GRAY);

            snackbar.show();
        }

    }

    @Override
    protected void onDestroy() {
        drawerLayout.removeDrawerListener(toggleListener);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuRefresh) {
            webView.reload();
            return true;
        }
        return false;
    }

    //items from Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.drawerMojTvHr:
                webView.loadUrl(urlMojTvHr);
                break;
            case R.id.drawerTVprofilNet:
                webView.loadUrl(urlTVprofil);
                break;
            case R.id.drawerKlixBa:
                webView.loadUrl(urlKlixBa);
                break;

            case R.id.drawerAladinInfo:
                webView.loadUrl(urlAladinInfo);
                break;

            case R.id.drawerArenaSport:
                webView.loadUrl(urlArenaSport);
                break;

            case R.id.drawerSportKlub:
                webView.loadUrl(urlSportKlub);
                break;

            case R.id.drawerEurosport:
                webView.loadUrl(urlEurosport);
                break;

            case R.id.menuRefresh:
                webView.reload();
                break;
            case R.id.drawerAbout:
                //classic about message, maybe new layout in future
                new AlertDialog.Builder(this)
                        .setTitle(String.format("\t %s", getString(R.string.m_about_app)))
                        .setMessage(getString(R.string.about_info))
                        .setPositiveButton(android.R.string.yes, null)
                        .show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(instanceTag, webView.getUrl());
        super.onSaveInstanceState(outState);
    }

}
