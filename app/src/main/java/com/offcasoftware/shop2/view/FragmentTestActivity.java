package com.offcasoftware.shop2.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.offcasoftware.shop2.R;

public class FragmentTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_fragment_test,Fragment1.getInstance("Joachimek"),Fragment1.class.getCanonicalName())//ostatni parametr to tag
                .commit();
    }
}
