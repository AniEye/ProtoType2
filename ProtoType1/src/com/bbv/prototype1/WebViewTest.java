package com.bbv.prototype1;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewTest extends Activity {

	WebView _webView;
	AssetManager _as;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		_webView = (WebView) findViewById(R.id.WVTest);
		_webView.getSettings().setBuiltInZoomControls(true);
		_as = getAssets();
		String filename = "file:///android_asset/pros_og_teori_text/1. Leire og vektmaterialer/1.1 Introduksjon/_.html";
		_webView.loadUrl(filename);
	}

}
