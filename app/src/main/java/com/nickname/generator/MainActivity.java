package com.nickname.generator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nicknamegenerator.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nickname.generator.Adapter.NickNameAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private EditText person_name;

    Boolean bool;

    RecyclerView nikNameRv;
    ArrayList<String> paperList;
    NickNameAdapter adapter;
    AdView mAdView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        person_name = findViewById(R.id.editTextTextPersonName);
        nikNameRv = findViewById(R.id.nickNameRv);
        mAdView = findViewById(R.id.adView);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        paperList = new ArrayList<>();

//        list.add("hellow");


        adapter = new NickNameAdapter(this, paperList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        nikNameRv.setLayoutManager(layoutManager);
        nikNameRv.setNestedScrollingEnabled(false);
        nikNameRv.setAdapter(adapter);


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

        if (mInterstitialAd != null) {

            showInterstitialAd();
        } else {
            loadInterstitialAd();
        }
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
            for (int i = 0; i < length_name / 2; i++) {
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

    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, Constant.InterstitialAd, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d("TAG", loadAdError.toString());
                mInterstitialAd = null;

            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                Log.i("TAG", "onAdLoaded");

                showInterstitialAd();
            }
        });
    }

    private void showInterstitialAd() {

        mInterstitialAd.show(this);
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d("TAG", "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("TAG", "Ad dismissed fullscreen content.");
                mInterstitialAd = null;
             
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e("TAG", "Ad failed to show fullscreen content.");
                mInterstitialAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("TAG", "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("TAG", "Ad showed fullscreen content.");
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}