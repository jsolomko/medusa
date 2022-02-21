package info.fandroid.quizapp.quizapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.adapters.StaffAdapter;
import info.fandroid.quizapp.quizapplication.models.staff.Staff;

public class StaffActivity extends BaseActivity {
    RecyclerView recyclerView;
    String[] names;
    String[] describes;
    int[] imageArray = {R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_person_24, R.drawable.ic_baseline_person_24};
    List<Staff> staffList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        initToolbar(true);
        setToolbarTitle(getString(R.string.about_dev));
        enableUpButton();

        names = getResources().getStringArray(R.array.staff_name);
        describes = getResources().getStringArray(R.array.staff_description);
        recyclerView = findViewById(R.id.rvStaff);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StaffAdapter staffAdapter = new StaffAdapter(getStaffArrayList());
        recyclerView.setAdapter(staffAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Staff> getStaffArrayList() {
        Staff cat1 = new Staff("Мурзик", "Дворовый", R.drawable.ic_baseline_person_24);
        Staff cat2 = new Staff("Муся", "Норвежская", R.drawable.ic_baseline_person_24);
        Staff cat3 = new Staff("Рики", "Мусорный", R.drawable.ic_baseline_person_24);
        staffList.add(cat1);
        staffList.add(cat2);
        staffList.add(cat3);
        return staffList;
    }
}