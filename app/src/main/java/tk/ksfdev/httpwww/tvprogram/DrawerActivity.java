package tk.ksfdev.httpwww.tvprogram;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
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
import android.widget.Toast;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;
    private CoordinatorLayout coordinatorLayout;

    //websites
    private String urlMojTvHr= "https://mojtv.hr/tv-program/-10/bih.aspx";
    private String urlTVprofil= "http://m.tvprofil.net/?grupaID=4";
    private String urlKlixBa= "https://www.klix.ba/tv-program";
    private String urlAladinInfo= "http://tv.aladin.info/";

    private String urlArenaSport= "http://www.tvarenasport.com/tv-program";
    private String urlSportKlub= "http://sportklub.rs/Programska-sema";
//    private String urlEurosport= "http://www.eurosport.rs/#watch-schedule__wrapper";
    private String urlEurosport= "https://www.eurosport.rs/tvschedule.shtml";

    //private String urlTemp= "";


    //TODO: add SwipeRefreshLayout (remove menu refresh icon)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //reference layout parts
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);


        if(savedInstanceState!=null)
            webView.loadUrl(savedInstanceState.getString("WEBLOCATION"));
        else
            webView.loadUrl(urlMojTvHr);
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
            coordinatorLayout= (CoordinatorLayout) findViewById(R.id.cordinatorLayout02);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Nema internet konekcije", Snackbar.LENGTH_INDEFINITE)
                    .setAction("POKUSAJ PONOVO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), DrawerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            //Changing button text color
            snackbar.setActionTextColor(Color.parseColor("#b0bec5"));

            // Changing text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.GRAY);

            snackbar.show();
        }



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
                        .setTitle("\tO aplikaciji")
                        .setMessage("Ova aplikacija nastala je iz potrebe da se grupisu informacije najboljih online provajdera pregleda TV programa u regionu" +
                                " i kao takva ne zadrzava nikakva prava na sadrzaj pregleda programa i nije odgovorna za njegovo odrzavanje. "
                                + "\n\nAdditional credits for graphic assets: \n" +
                                        "Icons made by DinosoftLabs from www.flaticon.com is licensed by Creative Commons BY 3.0"
                                )
                        .setPositiveButton(android.R.string.yes, null)
                        .show();
                break;

//            case R.id.drawerContactUs:
//                //email
//                sendMeEmail();
//                break;


            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WEBLOCATION", webView.getUrl());
        super.onSaveInstanceState(outState);
    }


    public void sendMeEmail(){
        //email intent
        String mail[]={"petars38@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, mail);
        intent.putExtra(Intent.EXTRA_SUBJECT, "TVprogram");
        //startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send email"));
        } else {
            Toast.makeText(this, "Mail me at: petars38@gmail.com", Toast.LENGTH_LONG).show();
        }
    }
}
