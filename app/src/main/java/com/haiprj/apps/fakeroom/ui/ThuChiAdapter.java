package com.haiprj.apps.fakeroom.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haiprj.apps.fakeroom.R;
import com.haiprj.apps.fakeroom.model.ThuChi;

import java.util.ArrayList;
import java.util.List;

public class ThuChiAdapter extends RecyclerView.Adapter<ThuChiAdapter.ThuChiHolder> {

    private final Context context;
    private final List<ThuChi> list = new ArrayList<>();

    public ThuChiAdapter(Context context) {
        this.context = context;
    }

    public List<ThuChi> getList() {
        return list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<ThuChi> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThuChiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThuChiHolder(LayoutInflater.from(this.context).inflate(R.layout.item_thu_chi, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ThuChiHolder holder, int position) {
        holder.load(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ThuChiHolder extends RecyclerView.ViewHolder{

        LinearLayout title, body;
        TextView tenLoai, ngayThang, tenKhoan, soTien;

        public ThuChiHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            tenKhoan = itemView.findViewById(R.id.tenKhoan);
            tenLoai = itemView.findViewById(R.id.tenLoai);
            ngayThang = itemView.findViewById(R.id.ngayThang);
            soTien = itemView.findViewById(R.id.soTien);
        }

        public void load(ThuChi thuChi) {
            if (!thuChi.isThu()) {
                title.setBackgroundColor(Color.parseColor("#252525"));
                body.setBackgroundColor(Color.parseColor("#383838"));
                tenLoai.setTextColor(Color.parseColor("#ffffff"));
                tenKhoan.setTextColor(Color.parseColor("#ffffff"));
                ngayThang.setTextColor(Color.parseColor("#ffffff"));
                soTien.setTextColor(Color.parseColor("#ffffff"));

                tenLoai.setText("Chi");
            }
            else {
                title.setBackgroundColor(Color.parseColor("#D5D5D5"));
                body.setBackgroundColor(Color.parseColor("#ffffff"));
                tenLoai.setTextColor(Color.parseColor("#000000"));
                tenKhoan.setTextColor(Color.parseColor("#000000"));
                ngayThang.setTextColor(Color.parseColor("#000000"));
                soTien.setTextColor(Color.parseColor("#000000"));

                tenLoai.setText("Thu");
            }

            ngayThang.setText(thuChi.getNgayThang());
            tenKhoan.setText(thuChi.getTenKhoan());
            soTien.setText(String.valueOf(thuChi.getSoTien()));


        }
    }
}
