package com.joebrooks.strawberry_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joebrooks.strawberry_android.views.SearchFragment;
import com.joebrooks.strawberry_android.views.SongFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment songFragment;
    private Fragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songFragment = new SongFragment();
        searchFragment = new SearchFragment();

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
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_group).commitAllowingStateLoss();
                    break;
                case R.id.playList:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_hotel).commitAllowingStateLoss();
                    break;
                default:
                    break;
            }
            return true;
        });
    }
}