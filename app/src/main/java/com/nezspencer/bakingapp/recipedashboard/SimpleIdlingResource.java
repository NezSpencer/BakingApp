package com.nezspencer.bakingapp.recipedashboard;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by nezspencer on 6/25/17.
 */

public class SimpleIdlingResource implements IdlingResource{

    private AtomicBoolean mIdleNow = new AtomicBoolean(true);
    @Nullable private volatile ResourceCallback mCallback;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;

    }

    public void setmIdleNow(boolean isIdle){
        mIdleNow.set(isIdle);

        if (isIdle && mCallback != null)
            mCallback.onTransitionToIdle();
    }
}

