package wkwkw.asek.cataloguemovie.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wkwkw.asek.cataloguemovie.FragmentNowPlaying;
import wkwkw.asek.cataloguemovie.FragmentUpcoming;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    //nama tab nya
    String[] title = new String[]{
            "Popular", "Top Rated"
    };

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        }

        //method ini yang akan memanipulasi penampilan Fragment dilayar
        @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
        case 0:
            fragment = new FragmentNowPlaying();
            break;
        case 1:
            fragment = new FragmentUpcoming();
            break;
            default:
            fragment = null;
            break;
            }

        return fragment;
        }

        @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
        }

        @Override
    public int getCount() {
        return title.length;
         }
        }