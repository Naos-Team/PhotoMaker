package com.xinlan.imageeditlibrary.editimage.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
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

import com.xinlan.imageeditlibrary.R;
import com.xinlan.imageeditlibrary.editimage.fragment.AddTextFragment;
import com.xinlan.imageeditlibrary.editimage.view.TextStickerView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FontPicker extends Dialog {

    public Activity c;
    public Context context;
    public Dialog d;

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.font);
    private ListView lv_fonts;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
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
        lv_fonts = findViewById(R.id.lv_fonts);
        arrayList = new ArrayList<String>();

        arrayList.add("Back To School");
        arrayList.add("California");
        arrayList.add("Family");
        arrayList.add("Default");
        arrayList.add("Gold Leaf Bold");
        arrayList.add("Halimun");
        arrayList.add("Mangabey");
        arrayList.add("Remachine Script");
        arrayList.add("Simple Mono Logue");
        arrayList.add("The Heart");
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        lv_fonts.setAdapter(adapter);

        lv_fonts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tf = (String) lv_fonts.getAdapter().getItem(position);
                if (tf ==  "Back To School"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.backtoschool);
                }
                if (tf ==  "California"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.california);
                }
                if (tf ==  "Family"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.family);
                }
                if (tf ==  "Default"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.font);
                }
                if (tf ==  "Gold Leaf Bold"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.goldleafbold);
                }
                if (tf ==  "Halimun"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.halimun);
                }
                if (tf ==  "Mangabey"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.mangabey);
                }
                if (tf ==  "Remachine Script"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.remachinescript);
                }
                if (tf ==  "Simple Mono Logue"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.simplemonologue);
                }
                if (tf ==  "The Heart"){
                    typeface = ResourcesCompat.getFont(getContext(),R.font.theheart);
                }
                //editText.setTypeface(typeface);
                listener.onChoice(typeface);
                dismiss();

            }
        });
    }

}
