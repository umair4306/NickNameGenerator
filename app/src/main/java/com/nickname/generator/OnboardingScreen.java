package com.nickname.generator;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nicknamegenerator.R;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

public class OnboardingScreen extends AppCompatActivity {

    ImageView imageView;
    TextView heading, description;
    int currentPosition = 1;
    Button next_button;
    TemplateView template;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);


        template = findViewById(R.id.my_template);

        imageView = findViewById(R.id.imageView);
        heading = findViewById(R.id.title_text);
        description = findViewById(R.id.slider_desc);

        if (InternetConnection.checkConnection(this)) {
            AdLoader adLoader = new AdLoader.Builder(this, Constant.NativeAd)
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().build();

                            template.setStyles(styles);
                            template.setNativeAd(nativeAd);
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()

                            .build())
                    .build();

        }else{
            template.setVisibility(View.INVISIBLE);
        }


        if (currentPosition == 1) {
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.image1));
            heading.setText("Amzaing Nick Name");
            description.setText("Generate unlimited amazing nick name");
            currentPosition++;

        }

        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPosition == 2) {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.image2));
                    heading.setText("Share Nick Name");
                    description.setText("Share your nick name with your friends and family");
                    next_button.setText("Finish");
                    next_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(OnboardingScreen.this, MainActivity.class));
                            finish();
                        }
                    });
//
//                } else if (currentPosition == 3) {
//                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
//                    heading.setText("We are good to know");
//                    description.setText("We are very about you");
//                    next_button.setText("Finish");
//                    next_button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            startActivity(new Intent(OnboardingScreen.this, MainActivity.class));
//                            finish();
//                        }
//                    });
//                }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}