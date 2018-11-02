package tjuri.example.com.jsongetparse;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.design.widget.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //private String TAG = MainActivity.class.getSimpleName();


    Spinner spinner;
    Spinner spinner1;

    ArrayList<String> valutetList;
    ArrayList<String> valutetSelling;
    ArrayList<String> valutetBuying;
    ArrayList<String> valutetMedian;
    ArrayList<String> valutetValue;
    int sp = 0;
    int sp1 = 0;

    EditText etUnos;
    EditText etUnos1;

    RadioButton rbSelling;
    RadioButton rbMedian;
    RadioButton rbBuying;


    double izracun;
    DecimalFormat df;

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