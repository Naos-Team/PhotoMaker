package com.xinlan.imageeditlibrary.editimage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.adapter.FontAdapter;
import com.xinlan.imageeditlibrary.editimage.fragment.AddTextFragment;
import com.xinlan.imageeditlibrary.editimage.model.FontItem;
import com.xinlan.imageeditlibrary.editimage.view.TextStickerView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FontPicker extends Dialog {

    public Activity c;
    public Context context;
    public Dialog d;

    public Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.font);
    private RecyclerView rv_fonts;
    private FontAdapter fontAdapter;
    private ArrayList<FontItem> arrayList;
    private TextStickerView textSticker;
    private EditText editText;
    private pickTypeListener listener;

    public FontPicker(@NonNull Context context,  pickTypeListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fonts_view);
        rv_fonts = findViewById(R.id.rv_fonts);
        arrayList = new ArrayList<FontItem>();

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        arrayList.add(new FontItem("Default", ResourcesCompat.getFont(getContext(),R.font.font)));
        arrayList.add(new FontItem("Back To School", ResourcesCompat.getFont(getContext(),R.font.backtoschool)));
        arrayList.add(new FontItem("California", ResourcesCompat.getFont(getContext(),R.font.california)));
        arrayList.add(new FontItem("Family", ResourcesCompat.getFont(getContext(),R.font.family)));
        arrayList.add(new FontItem("Gold Leaf Bold", ResourcesCompat.getFont(getContext(),R.font.goldleafbold)));
        arrayList.add(new FontItem("Halimun", ResourcesCompat.getFont(getContext(),R.font.halimun)));
        arrayList.add(new FontItem("Mangabey", ResourcesCompat.getFont(getContext(),R.font.mangabey)));
        arrayList.add(new FontItem("Remachine Script", ResourcesCompat.getFont(getContext(),R.font.remachinescript)));
        arrayList.add(new FontItem("Simple Mono Logue", ResourcesCompat.getFont(getContext(),R.font.simplemonologue)));
        arrayList.add(new FontItem("Aloevera", ResourcesCompat.getFont(getContext(),R.font.aloevera)));
        arrayList.add(new FontItem("Amsterdam", ResourcesCompat.getFont(getContext(),R.font.amsterdam)));
        arrayList.add(new FontItem("Bite Chocolate", ResourcesCompat.getFont(getContext(),R.font.bitechocolate)));
        arrayList.add(new FontItem("Brussels", ResourcesCompat.getFont(getContext(),R.font.brussels)));
        arrayList.add(new FontItem("Cassandra", ResourcesCompat.getFont(getContext(),R.font.cassandra)));
        arrayList.add(new FontItem("Candy Cane", ResourcesCompat.getFont(getContext(),R.font.candycane)));
        arrayList.add(new FontItem("Dance Today", ResourcesCompat.getFont(getContext(),R.font.dancetoday)));
        arrayList.add(new FontItem("First Snow", ResourcesCompat.getFont(getContext(),R.font.firstsnow)));
        arrayList.add(new FontItem("Girl As Free", ResourcesCompat.getFont(getContext(),R.font.girlasfree)));
        arrayList.add(new FontItem("Melanie", ResourcesCompat.getFont(getContext(),R.font.melanie)));
        arrayList.add(new FontItem("Romance", ResourcesCompat.getFont(getContext(),R.font.romance)));
        arrayList.add(new FontItem("Street", ResourcesCompat.getFont(getContext(),R.font.street)));
        arrayList.add(new FontItem("The Heart", ResourcesCompat.getFont(getContext(),R.font.theheart)));

        fontAdapter = new FontAdapter(arrayList, listener);
        rv_fonts.setAdapter(fontAdapter);
        rv_fonts.setLayoutManager(layoutManager);


    }

}
