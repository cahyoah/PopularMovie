package wkwkw.asek.cataloguemovie.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wkwkw.asek.cataloguemovie.FragmentReviews;
import wkwkw.asek.cataloguemovie.FragmentTrailer;

/**
 * Created by ASUS on 16/12/2017.
 */

public class TabFragmentDetailFIlmAdapter  extends FragmentPagerAdapter {
    //nama tab nya
    String[] title = new String[]{
            "Trailer", "Review"
    };

    public TabFragmentDetailFIlmAdapter(FragmentManager fm) {
        super(fm);
    }

    //method ini yang akan memanipulasi penampilan Fragment dilayar
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentTrailer();
                break;
            case 1:
                fragment = new FragmentReviews();
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