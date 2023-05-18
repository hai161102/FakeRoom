package com.haiprj.apps.fakeroom.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.haiprj.apps.fakeroom.DBManager;
import com.haiprj.apps.fakeroom.R;
import com.haiprj.apps.fakeroom.model.ThuChi;

public class ThemActivity extends AppCompatActivity {

    private EditText nt, tk, st;
    private RadioGroup rdg;
    private RadioButton rdThu, rdChi;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        nt = findViewById(R.id.ngayThang);
        tk = findViewById(R.id.tenKhoan);
        st = findViewById(R.id.soTien);
        rdg = findViewById(R.id.rdg);
        rdThu = findViewById(R.id.rdThu);
        rdChi = findViewById(R.id.rdChi);


        findViewById(R.id.ok).setOnClickListener(v -> {

            boolean isThu = false;
            int radioId = rdg.getCheckedRadioButtonId();
            if (radioId == rdThu.getId()) isThu = true;
            else if (radioId == rdChi.getId()) isThu = false;
            new DBManager(this).insert(new ThuChi(
                    tk.getText().toString(),
                    nt.getText().toString(),
                    Float.parseFloat(st.getText().toString()),
                    isThu
            ));
            setResult(RESULT_OK);
            finish();
        });

    }
}
