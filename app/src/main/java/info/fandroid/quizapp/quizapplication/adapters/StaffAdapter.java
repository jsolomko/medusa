package info.fandroid.quizapp.quizapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.models.staff.Staff;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {
    List<String> staffName;
    List<String> staffDesc;
    int[] images;

    List<Staff> staff;

    public StaffAdapter(List<Staff> staffList) {
        this.staff = staffList;
    }


    public class StaffViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        ImageView staffImage;

        public StaffViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStaffName);
            tvDesc = itemView.findViewById(R.id.tvStaffDesc);
            staffImage = itemView.findViewById(R.id.ivStaffImg);
        }
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_staff, viewGroup, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder staffViewHolder, int i) {
        staffViewHolder.tvName.setText(staff.get(i).getName());
        staffViewHolder.tvDesc.setText(staff.get(i).getDesc());
        staffViewHolder.staffImage.setImageResource(staff.get(i).getSrcImageId());

    }

    @Override
    public int getItemCount() {
        return staff.size();
    }


}
