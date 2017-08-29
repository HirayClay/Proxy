package proxy.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
    }
}
