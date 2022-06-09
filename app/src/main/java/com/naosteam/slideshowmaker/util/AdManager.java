package com.naosteam.slideshowmaker.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.naosteam.slideshowmaker.R;

import java.util.Arrays;
import java.util.List;


public class AdManager {
    public static int adCounter = 1;
    public static int adDisplayCounter = 5;

    public static boolean isloadFbMAXAd = false;
    public static boolean isEnableBanner = true;
    public static boolean isEnableInterstitial = true;


    public static void initAd(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
        });
        List<String> testDeviceIds = Arrays.asList("33BE2250B43518CCDA7DE426D04EE231");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
    }

    static AdView gadView;

    //TODO: adbanner
    public static void loadAdmobBanner(Context context, LinearLayout linearLayout){
        if(isEnableInterstitial){
            AdView adView = new AdView(context);
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            AdRequest adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
            //adView.setAdUnitId(Constants.ad_banner_id);
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            adView.setAdSize(AdSize.SMART_BANNER);
            linearLayout.addView(adView);
            adView.loadAd(adRequest);
        }
    }

    //TODO: interAds
    public static void showAdmobInterAd(final Activity context, InterAdsListener listener) {
        if (adCounter == adDisplayCounter && mInterstitialAd != null && isEnableInterstitial) {
            adCounter = 1;
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    loadInterAd(context);
                    listener.onClick();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    mInterstitialAd = null;
                }
            });
            mInterstitialAd.show((Activity) context);
        } else {
            adCounter++;
            listener.onClick();
        }
    }

    public static void loadBannerAd(Context context, LinearLayout adContainer) {
        gadView = new AdView(context);
        gadView.setAdUnitId(context.getString(R.string.admob_banner_id));
        adContainer.addView(gadView);
        loadBanner(context);
    }



    static void loadBanner(Context context) {
        AdRequest adRequest =
                new AdRequest.Builder()
                        .build();

        AdSize adSize = getAdSize((Activity) context);
        gadView.setAdSize(adSize);
        gadView.loadAd(adRequest);
    }

    static AdSize getAdSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static void adptiveBannerAd(Context context, LinearLayout adContainer) {
        AdView adView = new AdView(context);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        adView.setAdUnitId(context.getString(R.string.admob_banner_id));
        adView.loadAd(adRequest);
        adContainer.addView(adView);
    }

    static InterstitialAd mInterstitialAd;

    public static void loadInterAd(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, context.getString(R.string.admob_interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });
    }

    public static void showInterAd(final Activity context, final Intent intent, final int requestCode) {
        if (adCounter == adDisplayCounter && mInterstitialAd != null) {
            adCounter = 1;
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
//                    Log.d("TAG", "The ad was dismissed.");
                    loadInterAd(context);
                    startActivity(context, intent, requestCode);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
//                     Called when fullscreen content failed to show.
//                    Log.d("TAG", "The ad failed to show.");
                }


                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
//                    Log.d("TAG", "The ad was shown.");
                }
            });
            mInterstitialAd.show((Activity) context);
        } else {
            if (adCounter == adDisplayCounter) {
                adCounter = 1;
            }
            startActivity(context, intent, requestCode);
        }
    }

    public static void loadNativeAd(Activity context, FrameLayout frameLayout) {

        AdLoader.Builder builder = new AdLoader.Builder(context, context.getString(R.string.admob_native))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        // Assumes you have a placeholder FrameLayout in your View layout
                        // (with id fl_adplaceholder) where the ad is to be placed.
//                        FrameLayout frameLayout =
//                                context.findViewById(R.id.fl_adplaceholder);
                        // Assumes that your ad layout is in a file call native_ad_layout.xml
                        // in the res/layout folder
                        NativeAdView adView = (NativeAdView) context.getLayoutInflater()
                                .inflate(R.layout.ad_unified, null);
                        // This method sets the text, images and the native ad, etc into the ad
                        // view.
                        populateNativeAdView(nativeAd, adView);
                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());


    }

    static void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);


    }


    static void startActivity(Activity context, Intent intent, int requestCode) {
        if (intent != null) {
            context.startActivityForResult(intent, requestCode);
        }
    }


    static MaxAdView maxAdView;

    public static void initMAX(Activity activity) {
        AppLovinSdk.getInstance(activity).setMediationProvider("max");
        AppLovinSdk.initializeSdk(activity, configuration -> {
        });
    }

    public static void maxBanner(Activity activity, LinearLayout linearLayout) {
        maxAdView = new MaxAdView(activity.getResources().getString(R.string.max_banner), activity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);

        maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));

        // Set background or background color for banners to be fully functional
        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.purple_200));

        if (isNetworkConnected(activity)) {
            linearLayout.addView(maxAdView);

            // Load the banner
            if (maxAdView != null) {
                maxAdView.loadAd();
            }
        }

    }

    public static void maxBannerBg(Activity activity, LinearLayout linearLayout, int bannerBGColor) {
        MaxAdView maxAdView = new MaxAdView(activity.getResources().getString(R.string.max_banner), activity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);

        maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));

        // Set background or background color for banners to be fully functional
        maxAdView.setBackgroundColor(bannerBGColor);

        if (isNetworkConnected(activity)) {
            linearLayout.addView(maxAdView);

            // Load the banner
            if (maxAdView != null) {
                maxAdView.loadAd();
            }
        }

    }

    static MaxAdView maxAdAdaptive;

    public static void maxBannerAdaptive(Activity activity, LinearLayout linearLayout) {
        maxAdAdaptive = new MaxAdView(activity.getResources().getString(R.string.max_banner), activity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp);

        maxAdAdaptive.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
        maxAdAdaptive.setExtraParameter("adaptive_banner", "true");

        // Set background or background color for banners to be fully functional
        maxAdAdaptive.setBackgroundColor(activity.getResources().getColor(R.color.purple_200));

        if (isNetworkConnected(activity)) {
            linearLayout.addView(maxAdAdaptive);

            // Load the adaptive
            if (maxAdAdaptive != null) {
                maxAdAdaptive.loadAd();
            }
        }
    }

    static MaxAd nativeAd = null;
    static MaxNativeAdLoader nativeAdLoader;

    public static void loadNativeMAX(Activity context, FrameLayout nativeAdContainer) {
        nativeAdLoader = new MaxNativeAdLoader(context.getResources().getString(R.string.max_native), context);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(nativeAdView);
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad) {
                // Optional click callback
            }
        });

        nativeAdLoader.loadAd();
    }


    static Intent maxIntent;
    static int maxRequstCode;
    static MaxInterstitialAd maxInterstitialAd;

    public static void maxInterstital(Activity activity) {
        maxInterstitialAd = new MaxInterstitialAd(activity.getResources().getString(R.string.max_interstitial), activity);
        maxInterstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                maxInterstitialAd.loadAd();
                startActivity(activity, maxIntent, maxRequstCode);
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                if (isNetworkConnected(activity)) {
                    maxInterstitialAd.loadAd();
                }
                startActivity(activity, maxIntent, maxRequstCode);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                if (isNetworkConnected(activity)) {
                    maxInterstitialAd.loadAd();
                }
                startActivity(activity, maxIntent, maxRequstCode);
            }
        });

        if (isNetworkConnected(activity)) {
            // Load the first ad
            maxInterstitialAd.loadAd();
        }
    }

    public static void showMaxInterstitial(final Activity context, final Intent intent, final int requestCode) {
        maxIntent = intent;
        maxRequstCode = requestCode;
        if (adCounter == adDisplayCounter && maxInterstitialAd != null && maxInterstitialAd.isReady()) {
            adCounter = 1;
            maxInterstitialAd.showAd();
        } else {
            if (adCounter == adDisplayCounter) {
                adCounter = 1;
            }
            startActivity(context, intent, requestCode);
        }
    }

    static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public interface InterAdsListener{
        void onClick();
    }
}
