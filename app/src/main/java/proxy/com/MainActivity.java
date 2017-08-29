package proxy.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGhostActivity(View view) {
        startActivity(new Intent(this,GhostActivity.class));
    }

    public void startRealActivity(View view) {
        startActivity(new Intent(this,RealActivity.class));
    }
}
