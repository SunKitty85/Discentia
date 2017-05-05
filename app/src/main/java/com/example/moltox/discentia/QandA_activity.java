package com.example.moltox.discentia;
// init testing branch
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import Interfaces.TabCommunicator;
import MiscHelper.CardManagement;
import SqliteHelper.Card;


public class QandA_activity extends AppCompatActivity implements TabCommunicator {
    private static final String TAG = QandA_activity.class.getName();

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    CardManagement cardManagement;

    OnCardSend mOnCardSend;
    Card mSubmitCache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qanda_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardManagement = new CardManagement(this);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                cardManagement.showCardListAsLog();
                cardManagement.showCardDoneListAsLog();
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void sendCard(Card card)
    {
        submitStringToFrag(card);
    }

    public void submitStringToFrag(final Card card) {
        if (mOnCardSend == null) {
            // if FragmentB doesn't exist jet, cache value
            mSubmitCache = card;
            return;
        }
        mOnCardSend.submit(card);
    }


    public void setOnCardSend(OnCardSend onCardSend) {
        mOnCardSend = onCardSend;
        // deliver cached string, if any
        if (mSubmitCache != null) {
            onCardSend.submit(mSubmitCache);
        }
    }

    public interface OnCardSend {
        public void submit(Card card);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new QandA_ActivityFragmentFrontside(), getString(R.string.tab_title_frontside));
        adapter.addFragment(new QandA_ActivityFragmentBackside(), getString(R.string.tab_title_backside));
        // adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
