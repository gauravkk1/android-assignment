package android.example.pathcrex;

import android.example.pathcrex.models.FinishedMatchDetailModel;
import android.example.pathcrex.models.UpcomingMatchDetailModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterFrag extends FragmentStateAdapter {

    List<UpcomingMatchDetailModel> upcoming;
    ArrayList<FinishedMatchDetailModel> finished;



    public AdapterFrag(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<UpcomingMatchDetailModel> upcoming, ArrayList<FinishedMatchDetailModel> finished) {
        super(fragmentManager, lifecycle);
        this.upcoming = upcoming;
        this.finished = finished;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
        {
            return new UpcomingFragments(upcoming);
        }
        else
        {
            return new FinishedFragments(finished);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
