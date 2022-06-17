package android.example.pathcrex.adapters;

import android.example.pathcrex.R;
import android.example.pathcrex.models.UpcomingMatchDetailModel;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {

    private final ArrayList<UpcomingMatchDetailModel> matchDetails;
    private static final int LayoutOne = 0;
    private static final int LayoutTwo = 1;
    private static final int LayoutThree = 2;


    public UpcomingAdapter( ArrayList<UpcomingMatchDetailModel> matchDetails) {
        this.matchDetails = matchDetails;
    }

    @Override
    public int getItemViewType(int position){
        if(position == matchDetails.size()) return LayoutThree;
        else if(matchDetails.get(position).getViewType() == 0) return LayoutOne;
        else if(matchDetails.get(position).getViewType() == 1) return LayoutTwo;

        return -1;
    }



    @NonNull
    @Override
    public UpcomingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == LayoutOne){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_date_layout, parent, false);
            return new UpcomingAdapter.ViewHolder(view);
        }
        else if(viewType == LayoutTwo){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
            return new UpcomingAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_finished_matches_layout, parent, false);
            return new UpcomingAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAdapter.ViewHolder holder, int position) {
        if(position == matchDetails.size()){
            holder.tv_upcoming_matches.setText("All Upcoming Matches");

            holder.cl_layout.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Open All Upcoming matches", Toast.LENGTH_SHORT).show();
            });
        }
        else if(matchDetails.get(position).getViewType() == LayoutOne){
            String dateStr = null;
            try {
                dateStr = getDayAndMonth(matchDetails.get(position).getClubDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            Log.d("ddd", "onBindViewHolder: "+dateStr);
            holder.dateHeading.setText(dateStr);


        }
        else{
            holder.cardHeading.setText(matchDetails.get(position).getMatchNo() + "at Sharjah International Ground");
            holder.team1Name.setText(matchDetails.get(position).getT1());
            holder.team2Name.setText(matchDetails.get(position).getT2());
            String time = (matchDetails.get(position)).getT();
//            Log.d("ddd", "onBindViewHolder: " + matchDetails.get(position).getT());
            try {
                if(checkWhetherTimeWithinThreeHours(time)) {
//                    Log.d("ddd", "onBindViewHolder: "+"sssssssss");
                    holder.matchTime.setText("Starting in: ");
                    long tempTime = Long.parseLong(matchDetails.get(position).getDate());

                    CountDownTimer countDownTimer = new CountDownTimer(tempTime, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String hms = String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                            holder.matchDate.setText(hms);

                        }

                        @Override
                        public void onFinish() {
                            holder.matchDate.setText("TIME'S UP");
                        }
                    }.start();
                }else {
                    Log.d("dddd", "onBindViewHolder       : " + matchDetails.get(position).getT());
                    holder.matchTime.setText(matchDetails.get(position).getT());
                    holder.matchDate.setText(matchDetails.get(position).getDate());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Glide.with(holder.team1Flag.getContext()).load(matchDetails.get(position).getT1Flag()).into(holder.team1Flag);
            Glide.with(holder.team2Flag.getContext()).load(matchDetails.get(position).getT2Flag()).into(holder.team2Flag);

            if(matchDetails.get(position).getOdds() == 0){
                Log.d("Odds", "onBindViewHolder: No odds ");
                holder.ll_horizontalBar.setVisibility(View.GONE);
                holder.tv_rate1.setVisibility(View.GONE);
                holder.tv_rate2.setVisibility(View.GONE);
                holder.tv_rateTeam.setVisibility(View.GONE);
            }
            else {
                Log.d("Odds", "onBindViewHolder: Odds are available ");
                holder.ll_horizontalBar.setVisibility(View.VISIBLE);
                holder.tv_rate1.setVisibility(View.VISIBLE);
                holder.tv_rate2.setVisibility(View.VISIBLE);
                holder.tv_rateTeam.setVisibility(View.VISIBLE);

                holder.tv_rate1.setText(matchDetails.get(position).getRate1());
                holder.tv_rate2.setText(matchDetails.get(position).getRate2());
                holder.tv_rateTeam.setText(matchDetails.get(position).getRateTeam());
            }




        }
    }

    @Override
    public int getItemCount() {
        return matchDetails.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardHeading;
        ImageView team1Flag, team2Flag;
        TextView team1Name, team2Name;
        TextView matchTime , matchDate, dateHeading ;
        TextView tv_upcoming_matches;
        View ll_horizontalBar;
        TextView tv_rateTeam, tv_rate1, tv_rate2;

        ConstraintLayout cl_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardHeading = itemView.findViewById(R.id.cardHeading);
            team1Flag = itemView.findViewById(R.id.team1Flag);
            team2Flag = itemView.findViewById(R.id.team2Flag);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            matchTime = itemView.findViewById(R.id.matchTime);
            matchDate = itemView.findViewById(R.id.matchDate);
            tv_upcoming_matches = itemView.findViewById(R.id.tv_upcoming_finished);
            cl_layout = itemView.findViewById(R.id.cl_layout);
            dateHeading = itemView.findViewById(R.id.tv_matchDate);
            ll_horizontalBar = itemView.findViewById(R.id.tv_horizonalLine);
            tv_rateTeam = itemView.findViewById(R.id.tv_rateTeam2);
            tv_rate1 = itemView.findViewById(R.id.tv_rate2);
            tv_rate2 = itemView.findViewById(R.id.tv_rate3);






        }






    }

    public String getDayAndMonth(String dateStr) throws ParseException {
        SimpleDateFormat currSdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateObj = currSdf.parse(dateStr);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObj);

        DateTime startdate = new DateTime(calendar.getTimeInMillis());
        DateTime today = new DateTime();
        int days = Days.daysBetween(today.withTimeAtStartOfDay(), startdate.withTimeAtStartOfDay()).getDays();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM");


        String date;
        switch (days){
            case -1 : date = "Yesterday";
                     break;
            case 0: date = "Today";
                    break;

            case 1 : date = "Tommorrow" ;
                    break;

            default: date = DATE_FORMAT.format(calendar.getTime());
                    break;

        }

        String day;
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1 :
                day = "Sun";
                break;
            case 2 :
                day = "Mon";
                break;
            case 3 :
                day = "Tue";
                break;
            case 4 :
                day = "Wed";
                break;
            case 5 :
                day = "Thur";
                break;
            case 6 :
                day = "Fri";
                break;
            case 7 :
                day = "Sat";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + calendar.get(Calendar.DAY_OF_MONTH));
        }
        return day+", "+date;

    }

    private boolean checkWhetherTimeWithinThreeHours(String timeStr) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
        Date date = (Date)formatter.parse(timeStr);
        long DAY = 3 * 60 * 60 * 1000;
        return date.getTime() > System.currentTimeMillis() - DAY;
    }
}
