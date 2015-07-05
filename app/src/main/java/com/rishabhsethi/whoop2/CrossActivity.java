package com.rishabhsethi.whoop2;

import android.content.Intent;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.squareup.seismic.ShakeDetector;
import org.xwalk.core.XWalkView;
//import org.xwalk.core.XWalkView;


public class CrossActivity extends AppCompatActivity implements ShakeDetector.Listener{
    private XWalkView mXWalkView;
    //private WebView webView;
    //String url = getIntent().getStringExtra("GAME_URL");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross);
        Intent intent=getIntent();
        String url = intent.getStringExtra("GAME_URL");
        mXWalkView = (XWalkView) findViewById(R.id.cross_activity);
        mXWalkView.load(url, null);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        //webView = (WebView) findViewById(R.id.webView1);
        //webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(url);
    }

    public void hearShake() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cross, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
