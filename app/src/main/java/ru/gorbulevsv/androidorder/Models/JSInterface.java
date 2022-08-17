package ru.gorbulevsv.androidorder.Models;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JSInterface {
    Context context;

    public JSInterface(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void returnAddress(String address) {
        Toast.makeText(context, address, Toast.LENGTH_SHORT).show();
    }
}
