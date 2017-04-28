package com.nejitawo.audiohub.TabSetup;

/**
 * Created by Neji on 01/06/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nejitawo.audiohub.Comedy.Comedy;
import com.nejitawo.audiohub.Inspirational.Inspirational;
import com.nejitawo.audiohub.LatestWorks.Latest;
import com.nejitawo.audiohub.MusicGenres.Music;
import com.nejitawo.audiohub.R;


public class TabMakerFragment extends Fragment {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 4 ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout,null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);


        /**
         *Set an Adapater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         *
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(0);
              //  tabLayout.getTabAt(0).setIcon(tabIcons[0]);
              //  tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            }
        });



        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new Latest();
               case 1 : return new Music();
                case 2 : return new Comedy();
                case 3 : return new Inspirational();
              /*  case 4 : return new BlankFragment();*/
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return " LATEST ";
                case 1 :
                  return " MUSIC ";
                case 2 :
                    return " COMEDY ";
                case 3 :

                    return " INSPIRATIONAL";


            }
            return null;
        }
    }
}
