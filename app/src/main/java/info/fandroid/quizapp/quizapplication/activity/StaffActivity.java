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
        Staff Semykin = new Staff("Семыкин Виктор Дмитриевич", "Заведующий отделением, к.м.н.", R.drawable.pl_semykin01);
        Staff Glushenko = new Staff("Глушенко Юрий Алексеевич", "Врач-офтальмолог", R.drawable.pl_glushenko);
        Staff Fedotkina = new Staff("Федоткина Юлия Александровна", "Врач-офтальмолог", R.drawable.pl_fedotkina);
        Staff popov = new Staff("Попов Никита Андреевич", "Врач-офтальмолог", R.drawable.pl_popov);
        Staff neskubin = new Staff("Нескубин Олег Борисович", "Врач-офтальмолог", R.drawable.pl_neskubin);
        Staff koperchak = new Staff("Коперчак Илона Алексеевна", "Врач-офтальмолог", R.drawable.pl_koperchak);
        Staff pl_karagezova = new Staff("Карагезова\n" +
                "Кристина Александровна", "Врач-офтальмолог, к.м.н.", R.drawable.pl_karagezova);
        Staff bedikyan = new Staff("Бедикян Рузанна Саркисовна", "Врач-офтальмолог", R.drawable.pl_bedikyan);
        Staff gorelova = new Staff("Горелова Галина Леонидовна", "Врач-офтальмолог", R.drawable.pl_gorelova);
        staffList.add(Semykin);
        staffList.add(Glushenko);
        staffList.add(Fedotkina);
        staffList.add(popov);
        staffList.add(neskubin);
        staffList.add(koperchak);
        staffList.add(pl_karagezova);
        staffList.add(bedikyan);
        staffList.add(gorelova);
        return staffList;
    }
}