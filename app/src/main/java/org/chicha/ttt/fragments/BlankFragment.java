package org.chicha.ttt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import org.chicha.ttt.BaseFragment;
import org.chicha.ttt.R;

public class BlankFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             final Bundle savedInstanceState) {
        setTitle("NewPipe");
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("NewPipe");
        // leave this inline. Will make it harder for copy cats.
        // If you are a Copy cat FUCK YOU.
        // I WILL FIND YOU, AND I WILL ...
    }
}
