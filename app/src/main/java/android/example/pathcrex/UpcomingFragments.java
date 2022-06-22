package android.example.pathcrex;

import android.example.pathcrex.adapters.UpcomingAdapter;
import android.example.pathcrex.models.UpcomingMatchDetailModel;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class UpcomingFragments extends Fragment {

    public static final String API_URL = "https://mocki.io/v1/30786c0a-390e-41d5-9ad8-549ed26cba64";
    public static final String TAG = "Upcominggg";

    private RecyclerView recyclerView;
    private List<UpcomingMatchDetailModel> getModels = new ArrayList<>();
    private RequestQueue mRequestQueue;
    private UpcomingAdapter upcomingAdapter;
    private Timer timer;
    private TimerTask timerTask;


    UpcomingFragments(List<UpcomingMatchDetailModel> models){
        this.getModels = models;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRequestQueue = Volley.newRequestQueue(getContext());
        recyclerView = view.findViewById(R.id.rv_upcoming_fragment);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        upcomingAdapter = new UpcomingAdapter( getModels);
        recyclerView.setAdapter(upcomingAdapter);
        setTimer();









       // fetchJsonData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTimer();
    }

    public void setTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {

                    upcomingAdapter.notifyDataSetChanged();
                    Date dt = new Date();
                    long milli = dt.getTime();

                    Log.d(TAG, "run: We are running " + milli);
                });
            }
        };

        timer.schedule(timerTask, 1, 1000);
    }

    public void fetchJsonData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject fields = response.getJSONObject(i);
                        JSONArray matches = fields.getJSONArray("m");
                        String club_date = fields.getString("date");
                        getModels.add(new UpcomingMatchDetailModel( club_date,0));

                        for (int j = 0; j < matches.length(); j++) {
                            JSONObject temp = matches.getJSONObject(j);

                            String t1 = temp.getString("t1");
                            String t2 = temp.getString("t2");
                            String t1flag = temp.getString("t1flag");
                            String t2flag = temp.getString("t2flag");
                            String match_no = temp.getString("match_no");
                            String date = temp.getString("date");
                            String time_stamp = temp.getString("t");

                            Log.d(TAG, "onResponse: "+date+" "+time_stamp);

                            if (temp.has("odds")) {
                                Log.d(TAG, "onResponse: Odds are available");
                                JSONObject odds = temp.getJSONObject("odds");
                                String rate = odds.getString("rate");
                                String rate2 = odds.getString("rate2");
                                String rate_team = odds.getString("rate_team");
                                Log.d(TAG, "onResponse: " + rate + " " + rate2 + " " + rate_team);
                                getModels.add(new UpcomingMatchDetailModel( club_date,t1, t2, t1flag, t2flag, match_no, getDate(date), time_in_AM_PM(Long.parseLong((time_stamp))), rate, rate2, rate_team,1,1, Long.parseLong(time_stamp)));
                            } else {
                                getModels.add(new UpcomingMatchDetailModel(club_date,t1, t2, t1flag, t2flag, match_no, getDate(date), time_in_AM_PM(Long.parseLong((time_stamp))),1,0, Long.parseLong(time_stamp)));
                            }
                        }


                    }

                    upcomingAdapter = new UpcomingAdapter( getModels);
                    recyclerView.setAdapter(upcomingAdapter);
                    upcomingAdapter.notifyDataSetChanged();

                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }


            }
        }, error -> {

        }
        );

        mRequestQueue.add(jsonArrayRequest);


    }

    private String getDate(String date) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("MM/DD/yyyy");

        Date readDate = df.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(readDate.getTime());

        String []month = {"Jan","Feb","Mar","April","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        return ("" +Calendar.DAY_OF_MONTH+ " " + month[Calendar.MONTH]);
    }

    String time_in_AM_PM(long mili)
    {
        Date dt = new Date(mili);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        String time1 = sdf.format(dt);
        return time1;
    }






}