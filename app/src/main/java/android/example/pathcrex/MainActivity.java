package android.example.pathcrex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.example.pathcrex.adapters.UpcomingAdapter;
import android.example.pathcrex.models.UpcomingMatchDetailModel;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RequestQueue reqQ;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<UpcomingMatchDetailModel> upcoming;
    private JSONArray upcomingApiResponse;
    //private ArrayList<MatchDetailModel> upcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home_tab);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayoutHome);

        AdapterFrag adapterFrag = new AdapterFrag(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapterFrag);

        tabLayout.addTab(tabLayout.newTab().setText("  Upcoming  "));
        tabLayout.addTab(tabLayout.newTab().setText("  Finished   "));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("tab selected", "onTabSelected: " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });



//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        viewPagerAdapter.add(new UpcomingFragments(), "Upcoming");
//        viewPagerAdapter.add(new FinishedFragments(), "Finished");
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//
//        tabLayout.setupWithViewPager(viewPager);











    }
}