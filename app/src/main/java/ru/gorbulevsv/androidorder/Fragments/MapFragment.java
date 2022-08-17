package ru.gorbulevsv.androidorder.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import ru.gorbulevsv.androidorder.Models.JSInterface;
import ru.gorbulevsv.androidorder.R;

public class MapFragment extends Fragment {
    WebView webviewMap;
    Button buttonAddAddressToOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapFragment = inflater.inflate(R.layout.fragment_map, container, false);
        webviewMap = mapFragment.findViewById(R.id.webviewMap);
        webviewMap.addJavascriptInterface(new JSInterface(getContext()), "android");
        webviewMap.getSettings().setJavaScriptEnabled(true);
        webviewMap.loadUrl("file:///android_asset/index.html");

        buttonAddAddressToOrder =mapFragment.findViewById(R.id.buttonAddAddressToOrder);
        buttonAddAddressToOrder.setOnClickListener(view -> {
            webviewMap.loadUrl("javascript:returnAddressToAndroid()");
        });

        return mapFragment;
    }
}