package com.example.gymfinder.ui.auth;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

/**
 * Фрагмент, который вроде как не используется:)
 */
public class BaseAuth extends Fragment {
    @VisibleForTesting
    public ProgressBar progressBar;

    public void setProgressBar(ProgressBar progressBar){
        this.progressBar = progressBar;
    }

    public void showProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }
}
