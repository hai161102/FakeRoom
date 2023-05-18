package com.haiprj.apps.fakeroom.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haiprj.apps.fakeroom.DBManager;
import com.haiprj.apps.fakeroom.R;
import com.haiprj.apps.fakeroom.model.ThuChi;
import com.haiprj.apps.fakeroom.receivers.NetworkChangeReceiver;
import com.haiprj.apps.fakeroom.ui.ThuChiAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ThuChiAdapter adapter;
    private BroadcastReceiver mNetworkReceiver;
    private final List<ThuChi> defaultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ThuChiAdapter(this);
        ((RecyclerView) findViewById(R.id.thuchiRcv)).setAdapter(adapter);
        adapter.update(getListThuChi());

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();


        findViewById(R.id.addThuChi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, ThemActivity.class), 1);
            }
        });

        EditText search = findViewById(R.id.edtSearch);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == "") {
                    adapter.update(defaultList);
                    return;
                }
                List<ThuChi> thuChis = new ArrayList<>();
                defaultList.forEach(thuChi -> {
                    if (thuChi.getTenKhoan().toLowerCase().contains(s.toString().toLowerCase())) {
                        thuChis.add(thuChi);
                    }
                });
                adapter.update(thuChis);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private List<ThuChi> getListThuChi() {
        List<ThuChi> thuChis = new ArrayList<>(new DBManager(this).getAll());
        defaultList.clear();
        defaultList.addAll(thuChis);
        float tongThu = 0;
        float tongChi = 0;
        for (ThuChi tc :
                thuChis) {
            if (tc.isThu()) {
                tongThu += tc.getSoTien();
            }
            else tongChi += tc.getSoTien();
        }
        ((TextView) findViewById(R.id.tongTien)).setText(String.valueOf(tongThu - tongChi));

        return thuChis;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1) {
            adapter.update(getListThuChi());
        }
    }
    private void registerNetworkBroadcastForNougat() {
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}