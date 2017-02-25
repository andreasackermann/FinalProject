package com.udacity.gradle.builditbigger;

import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Andreas on 19.02.2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestAsyncTaskReturnNotEmpty   {

    @Rule
    public UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

    @Test
    public void verifyAsyncTaskReturnNotEmpty() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        uiThreadTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new EndpointsAsyncTask().execute(new EndpointsAsyncTask.JokeCallback() {
                    @Override
                    public void deliverJoke(String joke) {
                        Assert.assertNotNull(joke);
                        signal.countDown();
                    }
                });
            }
        });
        signal.await();
    }
}
