package android.example.pathcrex.adapters;

import android.annotation.SuppressLint;
import android.example.pathcrex.R;
import android.example.pathcrex.models.FinishedMatchDetailModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.ViewHolder> {
    private final ArrayList<FinishedMatchDetailModel> matchDetails;
    private static final int LayoutOne = 0;
    private static final int LayoutTwo = 1;
    private static final int LayoutThree = 2;




    public FinishedAdapter(ArrayList<FinishedMatchDetailModel> matchDetails) {
        this.matchDetails = matchDetails;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position== matchDetails.size())
        {
            return LayoutThree;
        }
        else if (matchDetails.get(position).getViewType() == 0) {
            return LayoutOne;
        } else if (matchDetails.get(position).getViewType() == 1) {
            return LayoutTwo;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return matchDetails.size() + 1;
    }

    @NonNull
    @Override
    public FinishedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==LayoutOne)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_date_layout,parent,false);
            return new FinishedAdapter.ViewHolder(view);
        }
        else if(viewType==LayoutTwo)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finished_card,parent,false);
            return new FinishedAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_finished_matches_layout,parent,false);
            return new FinishedAdapter.ViewHolder(view);
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FinishedAdapter.ViewHolder holder, int position) {
        if(position== matchDetails.size())
        {
            holder.tv_finished_matches.setText("All Finished Matches");
            holder.cl_layout.setOnClickListener(v -> {
                Toast.makeText(v.getContext(), "Open All Finished matches", Toast.LENGTH_SHORT).show();
            });
        }
        else if(matchDetails.get(position).getViewType()==LayoutOne)
        {
            String date_temp = null;
            try {
                date_temp = getDayAndMonth(matchDetails.get(position).getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.dateHeading.setText(date_temp);
        }
        else
        {
            holder.cardHeading.setText(matchDetails.get(position).getMatchNo()+" at Sharjah International Ground");
            holder.team1Name.setText(matchDetails.get(position).getTeam1());
            holder.team2Name.setText(matchDetails.get(position).getTeam2());
            String time_tag = (matchDetails.get(position).getT());
            holder.winner.setText(matchDetails.get(position).getWinner()+" Won");
            String win_tag = get_just_tag(matchDetails.get(position).getResult());
            holder.result.setText(win_tag);
            Glide.with(holder.team1Flag.getContext()).load(matchDetails.get(position).getT1Flag()).into(holder.team1Flag);
            Glide.with(holder.team2Flag.getContext()).load(matchDetails.get(position).getT2Flag()).into(holder.team2Flag);


        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cardHeading;
        ImageView team1Flag, team2Flag;
        TextView team1Name, team2Name;
        TextView score1, score2, overs1, overs2;
        TextView winner, result;
        TextView dateHeading;
        TextView tv_finished_matches;
        TextView matchDate;

        ConstraintLayout cl_layout;




       public ViewHolder(@NonNull View itemView) {
           super(itemView);

           cardHeading = itemView.findViewById(R.id.tv_card_heading_finished);
           team1Flag = itemView.findViewById(R.id.iv_t1Flag_finished);
           team2Flag = itemView.findViewById(R.id.iv_t2Flag_finished);
           team1Name = itemView.findViewById(R.id.tv_t1Name_finished);
           team2Name = itemView.findViewById(R.id.tv_t2Name_finished);
           score1 = itemView.findViewById(R.id.tv_t1Score_finished);
           score2 = itemView.findViewById(R.id.tv_team2_score);
           overs1 = itemView.findViewById(R.id.tv_t1over_finished);
           overs2 = itemView.findViewById(R.id.tv_t2over_finished);
           winner = itemView.findViewById(R.id.tv_winner_finished);
           result = itemView.findViewById(R.id.tv_result_finished);
           dateHeading = itemView.findViewById(R.id.tv_matchDate);
           tv_finished_matches = itemView.findViewById(R.id.tv_upcoming_finished);
           cl_layout = itemView.findViewById(R.id.cl_layout);


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

    private String get_just_tag(String result) {
        String temp = "";
        int f = -1,c=0;
        for(int i=0;i<result.length();i++)
        {
            if(result.charAt(i)==' ')
            {
                c++;
                if(c==2)
                {
                    f=i;break;
                }
            }
        }
        if(f==-1)
            return result;
        for(int i=f;i<result.length();i++)
        {
            temp+=result.charAt(i);
        }

        return temp;
    }
}
