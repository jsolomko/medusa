package info.fandroid.quizapp.quizapplication.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import info.fandroid.quizapp.quizapplication.R;
import info.fandroid.quizapp.quizapplication.adapters.CategoryAdapter;
import info.fandroid.quizapp.quizapplication.constants.AppConstants;
import info.fandroid.quizapp.quizapplication.listeners.ListItemClickListener;
import info.fandroid.quizapp.quizapplication.models.quiz.CategoryModel;
import info.fandroid.quizapp.quizapplication.utilities.ActivityUtilities;
import info.fandroid.quizapp.quizapplication.utilities.AppUtilities;
import info.fandroid.quizapp.quizapplication.utilities.DialogUtilities;

public class MainActivity extends BaseActivity implements DialogUtilities.OnCompleteListener {

    private Activity activity;
    private Context context;

    private Toolbar toolbar;

    private RelativeLayout mNotificationView;
    private AccountHeader header = null;
    private Drawer drawer = null;

    private ArrayList<CategoryModel> categoryList;
    private CategoryAdapter adapter = null;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = MainActivity.this;
        context = getApplicationContext();
        categoryList = new ArrayList<>();


        mNotificationView = (RelativeLayout) findViewById(R.id.notificationView);
        recyclerView = (RecyclerView) findViewById(R.id.rvContentScore);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false));

        adapter = new CategoryAdapter(context, activity, categoryList);
        recyclerView.setAdapter(adapter);

        initLoader();
        loadData();
        initListener();

        final IProfile profile = new ProfileDrawerItem().withIcon(R.drawable.ic_dev);

        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("О приложении").withIcon(R.drawable.ic_dev).withIdentifier(10).withSelectable(false),
                        new SecondaryDrawerItem().withName("ГБ4 Сочи - ОПУ").withIcon(R.drawable.ic_youtube).withIdentifier(20).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Поделитесь").withIcon(R.drawable.ic_share).withIdentifier(32).withSelectable(false),
                        new SecondaryDrawerItem().withName("Соглашения").withIcon(R.drawable.ic_privacy_policy).withIdentifier(33).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Выход").withIcon(R.drawable.ic_exit).withIdentifier(40).withSelectable(false)


                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 10) {
                                ActivityUtilities.getInstance().invokeNewActivity(activity, AboutDevActivity.class, false);

                            } else if (drawerItem.getIdentifier() == 20) {
                                AppUtilities.youtubeLink(activity);
                            } else if (drawerItem.getIdentifier() == 21) {
                                AppUtilities.facebookLink(activity);
                            } else if (drawerItem.getIdentifier() == 22) {
                                AppUtilities.twitterLink(activity);
                            } else if (drawerItem.getIdentifier() == 23) {
                                AppUtilities.googlePlusLink(activity);
                            } else if (drawerItem.getIdentifier() == 31) {
                                AppUtilities.rateThisApp(activity);
                            } else if (drawerItem.getIdentifier() == 32) {
                                AppUtilities.shareApp(activity);
                            } else if (drawerItem.getIdentifier() == 40) {

                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withShowDrawerUntilDraggedOpened(false)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            AppUtilities.tapPromtToExit(this);
        }
    }

    private void loadData() {
        showLoader();
        loadJson();
    }

    private void loadJson() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(AppConstants.CONTENT_FILE)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parseJson(sb.toString());
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(AppConstants.JSON_KEY_ITEMS);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                String categoryId = object.getString(AppConstants.JSON_KEY_CATEGORY_ID);
                String categoryName = object.getString(AppConstants.JSON_KEY_CATEGORY_NAME);

                categoryList.add(new CategoryModel(categoryId, categoryName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hideLoader();
        adapter.notifyDataSetChanged();
    }

    private void initListener() {

        // recycler list item click listener
        adapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

                boolean purchased = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(AppConstants.PRODUCT_ID_BOUGHT, false);
                boolean subscribed = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(AppConstants.PRODUCT_ID_SUBSCRIBE, false);

                CategoryModel model = categoryList.get(position);
                ActivityUtilities.getInstance().invokeCommonQuizActivity(activity, QuizPromptActivity.class, model.getCategoryId(), true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(AppConstants.NEW_NOTI);
    }


    @Override
    public void onComplete(Boolean isOkPressed, String viewIdText) {
        if (isOkPressed) {
            if (viewIdText.equals(AppConstants.BUNDLE_KEY_EXIT_OPTION)) {
                activity.finishAffinity();
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();

    }


}



