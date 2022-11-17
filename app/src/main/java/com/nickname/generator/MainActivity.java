package com.nickname.generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkUtils;
import com.example.nicknamegenerator.R;
import com.nickname.generator.Adapter.NickNameAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private EditText person_name;
    private MaxAdView MRECAdview;
    Boolean bool;

    RecyclerView nikNameRv;
    ArrayList<String> paperList;
    NickNameAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        person_name = findViewById(R.id.editTextTextPersonName);
        nikNameRv = findViewById(R.id.nickNameRv);
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                // AppLovin SDK is initialized, start loading ads
            }
        });
        paperList = new ArrayList<>();

//        list.add("hellow");


        adapter = new NickNameAdapter(this, paperList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        nikNameRv.setLayoutManager(layoutManager);
        nikNameRv.setNestedScrollingEnabled(false);
        nikNameRv.setAdapter(adapter);

        createMrecAd();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {

            case R.id.Rate_us:
                Uri uri = Uri.parse(Constant.Rate_us_Link + SplashScreen.PACKAGE_NAME); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

            case R.id.Privacy_Policy:
                Uri uri1 = Uri.parse(Constant.Policy_Link); // missing 'http://' will cause crashed
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                break;

            case R.id.AppInfo:
                startActivity(new Intent(MainActivity.this, AppInfo.class));
                break;

            case R.id.More_Apps:

                Uri uri2 = Uri.parse(Constant.More_Apps_Link); // missing 'http://' will cause crashed
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("SetTextI18n")
    public void onClickGenerateNick(View view) {


        String s = person_name.getText().toString().trim();
        try {
            if (s.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Your NickName", Toast.LENGTH_SHORT).show();
            } else {
                char charArray[] = s.toCharArray();
                bool = Character.isDigit(charArray[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (s.length() < 2) {
            Toast.makeText(MainActivity.this, "Minimum Length is 2", Toast.LENGTH_SHORT).show();

        } else if (bool) {
            Toast.makeText(MainActivity.this, "1st Character Should Be Alphabet", Toast.LENGTH_SHORT).show();
        } else {


            StringBuilder result = new StringBuilder("");
            StringBuilder nick = new StringBuilder("");
            StringBuilder vowels = new StringBuilder("");
            StringBuilder consonants = new StringBuilder("");
            Random random = new Random();
            int numberVowels = 0;
            int numberConsonants = 0;


            for (char c : person_name.getText().toString().trim().toLowerCase().toCharArray()) {
                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                    numberVowels += 1;
                    vowels.append(c);
                } else {
                    numberConsonants += 1;
                    Log.d("testtt", consonants.toString());
                    consonants.append(c);
                }
            }

            int length_name = random.nextInt(20);
            if (length_name < 10) length_name = 10;
            paperList.clear();
            for (int i = 0; i < length_name/2; i++) {
                if (vowels.length() > 0) {
                    nick.append(vowels.charAt(random.nextInt(vowels.length())));
                }
                if (consonants.length() > 0) {
                    nick.append(consonants.charAt(random.nextInt(consonants.length())));
                }
                paperList.add((nick.substring(0, 1).toUpperCase().trim() + nick.substring(1).trim()));
                Log.d("1234", paperList.toString());
            }
            nikNameRv.setAdapter(adapter);

        }

    }

    private void createMrecAd() {
        MRECAdview = new MaxAdView(Constant.MREC_ADD_KEY, MaxAdFormat.MREC, this);
        MRECAdview.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.d("onAdLoaded", "onAdLoaded: ");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("onAdLoaded", "onAdDisplayed: ");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("onAdLoaded", "onAdHidden: ");
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("onAdLoaded", "onAdClicked: ");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("onAdLoaded", "onAdLoadFailed: ");
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                Log.d("onAdLoaded", "onAdDisplayFailed: ");
            }
        });

        int width = AppLovinSdkUtils.dpToPx(this, 300);
        int height = AppLovinSdkUtils.dpToPx(this, 250);
        MRECAdview.setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        MRECAdview.setBackgroundColor(Color.WHITE);

        FrameLayout layout = findViewById(R.id.mrec);
        layout.addView(MRECAdview);
        MRECAdview.loadAd();
        MRECAdview.startAutoRefresh();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.isFirstTime = true;
    }
}