package android.example.pathcrex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.example.pathcrex.adapters.UpcomingAdapter;
import android.example.pathcrex.models.FinishedMatchDetailModel;
import android.example.pathcrex.models.UpcomingMatchDetailModel;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {
    private static Object Constants;
    private RequestQueue reqQ;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<UpcomingMatchDetailModel> upcomingList;

    private ArrayList<FinishedMatchDetailModel> finishedList;
    private JSONArray upcomingApiResponse;
    //private ArrayList<MatchDetailModel> upcoming;

    private FirebaseDatabase  firebaseDatabase;
    private DatabaseReference databaseReference;
    private HashMap<String, ArrayList<UpcomingMatchDetailModel>> upcomingMatchesMap;
    private HashMap<String, ArrayList<FinishedMatchDetailModel>> finishedMatchesMap;
    private String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        firebaseDatabase =  FirebaseDatabase.getInstance("https://parthcrex-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("matchDetails");

        getData();

        setContentView(R.layout.activity_home_tab);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayoutHome);

//        AdapterFrag adapterFrag = new AdapterFrag(getSupportFragmentManager(), getLifecycle());
//        viewPager.setAdapter(adapterFrag);

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

    private void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: changeddddd");

                    upcomingMatchesMap = new HashMap<>();
                    finishedMatchesMap = new HashMap<>();
                    //MainActivity.Constants = new MainActivity.Constants();

                    for(DataSnapshot ds : dataSnapshot.getChildren()){
//                       Log.d(TAG, "onDataChange: " + ds.child("result"));

                        if(ds.hasChild("result")){
                            String clubDate = ds.child("date").getValue().toString();
                            String t1 = ds.child("t1").getValue().toString();
                            String t2 = ds.child("t2").getValue().toString();
                            String date = ds.child("date").getValue().toString();
                            String match_no = ds.child("match_no").getValue().toString();
                            String time_stamp = ds.child("t").getValue().toString();
                            String t1_flag = ds.child("t1flag").getValue().toString();
                            String t2_flag = ds.child("t2flag").getValue().toString();
                            String overs1 = ds.child("overs1").getValue().toString();
                            String overs2 = ds.child("overs2").getValue().toString();
                            String winner = ds.child("winner").getValue().toString();
                            String result = ds.child("result").getValue().toString();
                            String score1 = ds.child("score1").getValue().toString();
                            String score2 = ds.child("score2").getValue().toString();

                            FinishedMatchDetailModel matchDetail = null;
                            try {
                                matchDetail = new FinishedMatchDetailModel(t1, t2, t1_flag, t2_flag, score1, score2, overs1, overs2, winner, match_no, getDate(date), 1,time_in_AM_PM( time_stamp), result);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(finishedMatchesMap.get(clubDate) == null){
                                finishedMatchesMap.put(clubDate, new ArrayList<>());

                            }
                            finishedMatchesMap.get(clubDate).add(matchDetail);


                        }
                        else {
                            String clubDate = ds.child("date").getValue().toString();
                            String t1 = ds.child("t1").getValue().toString();
                            String t2 = ds.child("t2").getValue().toString();
                            String date = ds.child("date").getValue().toString();
                            String match_no = ds.child("match_no").getValue().toString();
                            String time_stamp = ds.child("t").getValue().toString();
                            String t1_flag = ds.child("t1flag").getValue().toString();
                            String t2_flag = ds.child("t2flag").getValue().toString();

                            if(ds.hasChild("odds")){
                                String rate1 = ds.child("odds").child("rate").getValue().toString();
                                String rate2 = ds.child("odds").child("rate2").getValue().toString();
                                String rateTeam = ds.child("odds").child("rate_team").getValue().toString();

                                UpcomingMatchDetailModel matchDetail = new UpcomingMatchDetailModel(t1, t2, t1_flag, t2_flag, match_no, date, rate1, rate2 , rateTeam,time_in_AM_PM( time_stamp), 1, 1);
                                if(upcomingMatchesMap.get(clubDate) == null){
                                    upcomingMatchesMap.put(clubDate, new ArrayList<>());

                                }

                                upcomingMatchesMap.get(clubDate).add(matchDetail);

                            }
                            else {
                                UpcomingMatchDetailModel matchDetail = null;
                                try {
                                    matchDetail = new UpcomingMatchDetailModel(t1, t2, t1_flag, t2_flag, match_no,getDate( date) ,time_in_AM_PM( time_stamp), 1, 0);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(upcomingMatchesMap.get(clubDate) == null){
                                    upcomingMatchesMap.put(clubDate, new ArrayList<>());

                                }

                                upcomingMatchesMap.get(clubDate).add(matchDetail);

                            }

//                            Log.d(TAG, "onDataChange: Everything fine till here  ");

                            upcomingList = new ArrayList<>();
                            finishedList = new ArrayList<>();

                            for(String day : upcomingMatchesMap.keySet()){
                                ArrayList<UpcomingMatchDetailModel > newModels = upcomingMatchesMap.get(day);
                                upcomingList.add(new UpcomingMatchDetailModel(day,0));
                                for(int i = 0; i < Objects.requireNonNull(newModels).size(); i++){
                                    upcomingList.add(newModels.get(i));
                                }
                            }

                            for(String key : finishedMatchesMap.keySet()){
                                ArrayList<FinishedMatchDetailModel > newModels = finishedMatchesMap.get(key);
                                finishedList.add(new FinishedMatchDetailModel(key, 0));
                                for(int i = 0; i < Objects.requireNonNull(newModels).size(); i++){
                                    finishedList.add(newModels.get(i));
                                }
                            }

                            init();

                        }
                    }
                    // adapter


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getDate(String date) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy");

        Date readDate = df.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(readDate.getTime());

        String []month = {"Jan","Feb","Mar","April","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        return ("" +Calendar.DAY_OF_MONTH+ " " + month[Calendar.MONTH]);
    }

    String time_in_AM_PM(String milis)
    {
        long mili = Long.parseLong(milis);
        Date dt = new Date(mili);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String time1 = sdf.format(dt);
        return time1;
    }

    private void init(){
        AdapterFrag adapterFrag = new AdapterFrag(getSupportFragmentManager(), getLifecycle(), upcomingList, finishedList);
        viewPager.setAdapter(adapterFrag);

    }

}