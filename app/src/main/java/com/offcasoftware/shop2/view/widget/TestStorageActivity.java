package com.offcasoftware.shop2.view.widget;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.offcasoftware.shop2.R;
import com.offcasoftware.shop2.model.Product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.PendingIntent.getActivity;

public class TestStorageActivity extends AppCompatActivity {

    @BindView(R.id.textView1)
    TextView mInternalDir;
    @BindView(R.id.textView2)
    TextView mCacheDir;
    @BindView(R.id.textView3)
    TextView ReadPath;
    @BindView(R.id.imageView)
    ImageView imageView1;
    @BindView(R.id.textView4)
    TextView ReadPath2;
    @BindView(R.id.textView5)
    TextView textViewSP;


    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("KEY")) {


                Toast.makeText(TestStorageActivity.this, sharedPreferences.getString(key, null), Toast.LENGTH_LONG).show();

            }
        }

        ;
    };

    private String filename = "myfile";
    private String filename2 = "myfile2";
    private String filename3 = "myfile3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_storage);
        ButterKnife.bind(this);
        setup();
        save();
        read();
        imageView1.setImageBitmap(BitmapImage());

        try {
            ObjectinPut();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ReadPath2.setText(ObjectoutPut());
        registerOnSharedPreferenceChangeListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }

    private void setup() {
        mInternalDir.setText("Internal Dir: " + getFilesDir().getAbsolutePath());
        mCacheDir.setText("Cache Dir: " + getFilesDir().getAbsolutePath());
    }

    private void save() {


        String string = "Hello world!";
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void read() {
        FileInputStream inputStream;
        String fileAsString = null;
        try {
            inputStream = openFileInput(filename);
            BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            fileAsString = sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadPath.setText(fileAsString);

    }

    private Bitmap BitmapImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dom1);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(filename2, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            bitmap = BitmapFactory.decodeStream(openFileInput(filename2), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private String ObjectoutPut() {
        Product product = new Product(12, "domek", 1200000, "mojachata");

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(openFileOutput(filename3, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(product);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product.toString();


    }

    private String ObjectinPut() throws ClassNotFoundException {
        Product productloaded = null;
        FileInputStream fis = null;
        try {
            fis = openFileInput(filename3);
            ObjectInputStream is = new ObjectInputStream(fis);
            productloaded = (Product) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return productloaded.toString();
//        ReadPath2.setText(productloaded.toString());


    }

    private void testSharedPreferences() {
        final String key = "KEY";
        final SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putString(key, "BUM").apply();

        String value = preferences.getString(key, null);
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
        textViewSP.setText(value);
    }

    @OnClick(R.id.test_shared)
    public void onButtonClick(View view) {
        testSharedPreferences();

    }

    public void registerOnSharedPreferenceChangeListener() {

    }


}








