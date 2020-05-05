package com.sam.t3prorssreader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditLinkActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    RadioButton btnTrangChu,btnKhoaHoc,btnSoHoa,btnDoiSong,btnTamSu;
    private Button btnSubmit;
    private String link;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_link_layout);
        initWidget();
        listenAction();
    }

    private void listenAction() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_btn_trang_chu:
                        link = "https://vnexpress.net/rss/tin-moi-nhat.rss";
                        break;
                    case R.id.radio_btn_tam_su:
                        link = "https://vnexpress.net/rss/tam-su.rss";
                        break;
                    case R.id.radio_btn_so_hoa:
                        link = "https://vnexpress.net/rss/so-hoa.rss";
                        break;
                    case R.id.radio_btn_khoa_hoc:
                        link = "https://vnexpress.net/rss/khoa-hoc.rss";
                        break;
                    case R.id.radio_btn_doi_song:
                        link = "https://vnexpress.net/rss/gia-dinh.rss";
                        break;
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultLink = new Intent();
                resultLink.putExtra("link",link);
                setResult(1,resultLink);
                finish();
            }
        });
    }

    private void initWidget() {
        link = "";
        radioGroup = findViewById(R.id.radio_group);
        btnTrangChu = findViewById(R.id.radio_btn_trang_chu);
        btnDoiSong = findViewById(R.id.radio_btn_doi_song);
        btnSoHoa = findViewById(R.id.radio_btn_so_hoa);
        btnTamSu = findViewById(R.id.radio_btn_tam_su);
        btnKhoaHoc = findViewById(R.id.radio_btn_khoa_hoc);
        btnSubmit = findViewById(R.id.btn_submit);
    }
}
