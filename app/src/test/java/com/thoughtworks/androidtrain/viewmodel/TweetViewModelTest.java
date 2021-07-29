package com.thoughtworks.androidtrain.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.thoughtworks.androidtrain.MainApplication;
import com.thoughtworks.androidtrain.data.model.Tweet;
import com.thoughtworks.androidtrain.data.source.TweetRepository;
import com.thoughtworks.androidtrain.schedulers.SchedulersProvider;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public class TweetViewModelTest {


    private TweetRepository tweetRepository;
    private SchedulersProvider schedulersProvider;

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();


    @Before
    public void beforeEach() {
        tweetRepository = Mockito.mock(TweetRepository.class);
        schedulersProvider = Mockito.mock(SchedulersProvider.class);
    }

    private List<Tweet> mockData() {
        List<Tweet> tweets = new ArrayList<>();
        Tweet tweet = new Tweet();
        tweet.setContent("tweet_content_test");
        tweets.add(tweet);
        return tweets;
    }

    @Test
    public void testFetchData() {
        // given
        when(schedulersProvider.io()).thenReturn(Schedulers.trampoline());
        when(schedulersProvider.ui()).thenReturn(Schedulers.trampoline());

        when(tweetRepository.fetchTweets()).thenReturn(Flowable.just(mockData()));

        // when
        TweetViewModel tweetViewModel = new TweetViewModel();
        tweetViewModel.init(schedulersProvider, tweetRepository);
        tweetViewModel.fetchData(throwable -> {
        });

        // then
        Assert.assertEquals(1, tweetViewModel.getLiveData().getValue().size());
        Assert.assertEquals("tweet_content_test", tweetViewModel.getLiveData().getValue().get(0).getContent());
    }
}