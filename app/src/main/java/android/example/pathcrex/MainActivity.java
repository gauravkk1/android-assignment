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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity {
    private static Object Constants;
    private RequestQueue reqQ;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ArrayList<UpcomingMatchDetailModel> upcomingList = new ArrayList<>();

    private ArrayList<FinishedMatchDetailModel> finishedList = new ArrayList<>();
    private JSONArray upcomingApiResponse;
    //private ArrayList<MatchDetailModel> upcoming;

    private FirebaseDatabase  firebaseDatabase;
    private DatabaseReference databaseReference;
    private HashMap<String, ArrayList<UpcomingMatchDetailModel>> upcomingMatchesMap;
    private HashMap<String, ArrayList<FinishedMatchDetailModel>> finishedMatchesMap;
    private String TAG = "MainActivity";
    private FirebaseFirestore db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        firebaseDatabase =  FirebaseDatabase.getInstance("https://parthcrex-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("matchDetails");

        db = FirebaseFirestore.getInstance();


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

    private void addDataToFirebase(){
        CollectionReference matchDetails = db.collection("matchDetails");
        for(UpcomingMatchDetailModel upcomingMatch : upcomingList){
            if(upcomingMatch.getT1Flag() != null) {
                matchDetails.add(upcomingMatch)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "onSuccess: Upcoming  match details are added");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Some error came while adding upcoming match" + e.toString());
                            }
                        });
            }
        }

        for(FinishedMatchDetailModel finishedMatch : finishedList){
            if(finishedMatch.getT1Flag() != null) {
                matchDetails.add(finishedMatch)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "onSuccess: Finished match details are added");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Some error came while adding finished match");
                            }
                        });
            }
        }


    }

    private void getData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: changeddddd");
// clear previous array
                upcomingList.clear();
                finishedList.clear();
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
                            matchDetail = new FinishedMatchDetailModel(t1, t2, t1_flag, t2_flag, score1, score2, overs1, overs2, winner, match_no, getDate(date), 1,time_in_AM_PM( time_stamp), result);


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

                                long epochTime = 0;
                                try {
                                    epochTime = giveEpocTime(clubDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                UpcomingMatchDetailModel matchDetail = new UpcomingMatchDetailModel(clubDate ,t1, t2, t1_flag, t2_flag, match_no, date, rate1, rate2 , rateTeam,time_in_AM_PM( time_stamp), 1, 1, Long.parseLong(time_stamp));
                                if(upcomingMatchesMap.get(clubDate) == null){
                                    upcomingMatchesMap.put(clubDate, new ArrayList<>());

                                }

                                upcomingMatchesMap.get(clubDate).add(matchDetail);

                            }
                            else {
                                UpcomingMatchDetailModel matchDetail = null;
                                matchDetail = new UpcomingMatchDetailModel(clubDate, t1, t2, t1_flag, t2_flag, match_no,getDate( date) ,time_in_AM_PM( time_stamp), 1, 0, Long.parseLong(time_stamp));

                                if(upcomingMatchesMap.get(clubDate) == null){
                                    upcomingMatchesMap.put(clubDate, new ArrayList<>());

                                }

                                upcomingMatchesMap.get(clubDate).add(matchDetail);

                            }

//                            Log.d(TAG, "onDataChange: Everything fine till here  ");

                            upcomingList = new ArrayList<>();
                            finishedList = new ArrayList<>();

                            for(String key : upcomingMatchesMap.keySet()){
                                ArrayList<UpcomingMatchDetailModel > newModels = upcomingMatchesMap.get(key);
                                upcomingList.add(new UpcomingMatchDetailModel(key ,0));
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

                            //addDataToFirebase();



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

    private String getDate(String date) {

        SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy");

        Date readDate = null;
        try {
            readDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    private long giveEpocTime(String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy");

        Date readDate = df.parse(dateStr);
        return readDate.getTime();
    }

    private String giveDateString(long milli){
        Date dt = new Date(milli / 1000);
        SimpleDateFormat jdf = new SimpleDateFormat("MM/DD/yyyy");

        String java_date = jdf.format(dt);

        return java_date;
    }

}

class SetHelper implements Comparator<UpcomingMatchDetailModel> {

    @Override
    public int compare(UpcomingMatchDetailModel o1, UpcomingMatchDetailModel o2) {
        if(o1.getTimeStamp() > o2.getTimeStamp()) return -1;
        return 1;
    }
}

