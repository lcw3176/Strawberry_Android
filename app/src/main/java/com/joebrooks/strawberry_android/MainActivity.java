package com.joebrooks.strawberry_android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joebrooks.strawberry_android.services.FileService;
import com.joebrooks.strawberry_android.views.ChartFragment;
import com.joebrooks.strawberry_android.views.PlayListFragment;
import com.joebrooks.strawberry_android.views.SearchFragment;
import com.joebrooks.strawberry_android.views.SongFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment songFragment;
    private Fragment searchFragment;
    private Fragment chartFragment;
    private Fragment playListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songFragment = new SongFragment();
        searchFragment = new SearchFragment();
        chartFragment = new ChartFragment();
        playListFragment = new PlayListFragment();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, songFragment).commitAllowingStateLoss();


        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.song:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,songFragment).commitAllowingStateLoss();
                    break;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,searchFragment).commitAllowingStateLoss();
                    break;
                case R.id.chart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,chartFragment).commitAllowingStateLoss();
                    break;
                case R.id.playList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,playListFragment).commitAllowingStateLoss();
                    break;
                default:
                    break;
            }
            return true;
        });

        checkVerify();
    }

    public void checkVerify() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }
}