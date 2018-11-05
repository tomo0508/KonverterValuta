package tjuri.example.com.jsongetparse;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.*;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager vp = findViewById(R.id.viewPager);
        TabLayout tl = findViewById(R.id.sliding_tabs);
        FragmentAdapter fa = new FragmentAdapter(getSupportFragmentManager(), this);
        vp.setAdapter(fa);
        tl.setupWithViewPager(vp);
    }
}