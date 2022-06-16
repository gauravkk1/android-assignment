package android.example.pathcrex;

import android.example.pathcrex.adapters.FinishedAdapter;
import android.example.pathcrex.adapters.UpcomingAdapter;
import android.example.pathcrex.models.FinishedMatchDetailModel;
import android.example.pathcrex.models.UpcomingMatchDetailModel;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class FinishedFragments extends Fragment {

    private final String  FINISHED_MATCHES_API_URL = "https://mocki.io/v1/2389d44c-81aa-4e04-bd2e-b8c7e17572c0";
    private ArrayList<FinishedMatchDetailModel> matchesDetails = new ArrayList<>();
    private RecyclerView recyclerView;
    private FinishedAdapter finishedAdapter;

    private RequestQueue mRequestQueue;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finished, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_finished_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        mRequestQueue = Volley.newRequestQueue(requireContext());

        fetchJsonData();

    }

    private void fetchJsonData(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, FINISHED_MATCHES_API_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject fields = response.getJSONObject(i);
                        JSONArray matches = fields.getJSONArray("m");
                        String club_date = fields.getString("date");
                        matchesDetails.add(new FinishedMatchDetailModel(club_date, 0));

                        for (int j = 0; j < matches.length(); j++) {
                            JSONObject temp = matches.getJSONObject(j);

                            String t1 = temp.getString("t1");
                            String t2 = temp.getString("t2");
                            String t1flag = temp.getString("t1flag");
                            String t2flag = temp.getString("t2flag");
                            String match_no = temp.getString("match_no");
                            String date = temp.getString("date");
                            String time_stamp = temp.getString("t");
                            String score1 = temp.getString("score1");
                            String score2 = temp.getString("score2");
                            String overs1 = temp.getString("overs1");
                            String overs2 = temp.getString("overs2");
                            String winner = temp.getString("winner");
                            String result = temp.getString("result");
                            Log.d("jsonData", "onResponse: " + score1 + "   " + score2);

                            matchesDetails.add(new FinishedMatchDetailModel(t1, t2, t1flag, t2flag, score1, score2, overs1, overs2, winner, match_no, date, 1, time_stamp, result));
                        }


                    }



                    finishedAdapter = new FinishedAdapter(matchesDetails);
                    recyclerView.setAdapter(finishedAdapter);
                    finishedAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, error -> {

        }
        );

        mRequestQueue.add(jsonArrayRequest);


    }
}