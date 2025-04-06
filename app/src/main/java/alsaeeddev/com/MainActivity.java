package alsaeeddev.com;


import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;


import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText urlInput;
    private ProgressBar progressBar;
    private ImageButton backButton, forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOnBackPressedDispatcher().addCallback(this, backPressedCallback);

        urlInput = findViewById(R.id.urlInput);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.backButton);
        forwardButton = findViewById(R.id.forwardButton);


        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) progressBar.setVisibility(View.GONE);
                else progressBar.setVisibility(View.VISIBLE);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com");

        urlInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                loadUrlFromInput();
                return true;
            }
            return false;
        });


        urlInput.setOnEditorActionListener((v, actionId, event) -> {
            // Check if the actionId corresponds to the "GO" key
            if (actionId == EditorInfo.IME_ACTION_GO) {
                // Perform the desired action when the GO button is pressed
            loadUrlFromInput();
                return true;  // Return true if the action is handled
            }
            return false;  // Return false for other actions
        });



        backButton.setOnClickListener(v -> {
            if (webView.canGoBack()) webView.goBack();
        });
        forwardButton.setOnClickListener(v -> {
            if (webView.canGoForward()) webView.goForward();
        });

    }

    private void loadUrlFromInput() {
        String url = urlInput.getText().toString().trim();
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        webView.loadUrl(url);
    }



    // Set up the back pressed callback
    private final OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
    };
}
