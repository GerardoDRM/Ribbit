package doge.thecraftcod.ribbit;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by gerardo on 19/07/15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mCoxntext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mCoxntext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0) {
            return new InboxFragment();
        }
        else if (position == 1) {
            return new FriendsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mCoxntext.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mCoxntext.getString(R.string.title_section2).toUpperCase(l);
        }
        return null;
    }

    public int getIcon(int position) {
        switch (position) {
            case 0:
                return R.mipmap.ic_tab_inbox;
            case 1:
                return R.mipmap.ic_tab_friends;
        }
        return R.mipmap.ic_tab_inbox;
    }
}
