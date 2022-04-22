package vcarry.mask;


import com.kessi.photovideomaker.R;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D.EFFECT;

public enum KessiTheme {

    Test("Test"){
        public ArrayList<EFFECT> getTheme() {
            KessiMaskBitmap3D.Random_num();
            ArrayList<EFFECT> mEffects = new ArrayList();
            //mEffects.add(EFFECT.TEST);
            mEffects.add(EFFECT.RANDOM_CIRCLE_IN);
            mEffects.add(EFFECT.RANDOM_CIRCLE_OUT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.facebook;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    Ramdom1("Ramdom1"){
        public ArrayList<EFFECT> getTheme() {
            return KessiMaskBitmap3D.random(1);
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.random1   ;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    Ramdom2("Ramdom2"){
        public ArrayList<EFFECT> getTheme() {
            return KessiMaskBitmap3D.random(2);
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.random_zoom;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    Ramdom3("Ramdom3"){
        public ArrayList<EFFECT> getTheme() {
            return KessiMaskBitmap3D.random(3);
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.random_all;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    None("None"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.NONE);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_no;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    RANDOM_ROUND("RANDOM_ROUND"){
        public ArrayList<EFFECT> getTheme() {
            KessiMaskBitmap3D.Random_num();
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.RANDOM_CIRCLE_IN);
            mEffects.add(EFFECT.RANDOM_CIRCLE_OUT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.beauty;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    EYE("EYE"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.LEAF);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.ic_launcher_app;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    FAN("FAN"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.FAN_TOP_RIGHT);
            mEffects.add(EFFECT.FAN_TOP_LEFT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.ic_launcher_app;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    CROSS("CROSS"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CROSS_OUT);
            mEffects.add(EFFECT.CROSS_IN);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.bg_no_button_for_dialog;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    ECLIPSE("ECLIPSE"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.ECLIPSE_IN);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.f_9;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    HORIZONTAL_RECT("HORIZONTAL_RECT"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.HORIZONTAL_RECT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.small_play;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    LOVE("LOVE"){
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.LOVE_DOOR);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_love;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
//    Shine("Shine") {
//        public ArrayList<EFFECT> getTheme() {
//            ArrayList<EFFECT> mEffects = new ArrayList();
//            mEffects.add(EFFECT.CROSS_IN);
//            mEffects.add(EFFECT.CROSS_OUT);
//            mEffects.add(EFFECT.LOVE_DOOR);
//            mEffects.add(EFFECT.WIND_MILL);
//            mEffects.add(EFFECT.LEAF);
//            mEffects.add(EFFECT.ECLIPSE_IN);
//            mEffects.add(EFFECT.HORIZONTAL_RECT);
//            return mEffects;
//        }
//
//        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
//            return null;
//        }
//
//        public int getThemeDrawable() {
//            return R.drawable.circlebomb;
//        }
//
//        public int getThemeMusic() {
//            return R.raw.song_2;
//        }
//    },
    MixTOURNADO("MixTOURNADO") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.TOURNADO);
            list.add(EFFECT.TOURNADO_REV);
            list.add(EFFECT.RUB_RL);
            list.add(EFFECT.RUB_LR);

            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_31;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }

    },
    MixWIPER("MixWIPER") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.WIPER_LEFT_CORNER);
            list.add(EFFECT.WIPER_RIGHT_CORNER);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_30;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }

    },
    SKEW("SKEW") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SKEW_LEFT_MEARGE);
            list.add(EFFECT.SKEW_RIGHT_SPLIT);
            list.add(EFFECT.SKEW_LEFT_SPLIT);
            list.add(EFFECT.SKEW_RIGHT_MEARGE);

            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_29;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    NewShine("NewShine") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CIRCLE_OUT);
            mEffects.add(EFFECT.CIRCLE_IN);
            mEffects.add(EFFECT.CIRCLE_LEFT_TOP);
            mEffects.add(EFFECT.CIRCLE_RIGHT_TOP);
            mEffects.add(EFFECT.CIRCLE_LEFT_BOTTOM);
            mEffects.add(EFFECT.CIRCLE_RIGHT_BOTTOM);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.t_28;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    MixSquare("MixSquare") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();

            mEffects.add(EFFECT.SQUARE_OUT);
            mEffects.add(EFFECT.SQUARE_IN);
            mEffects.add(EFFECT.SQUARE_R_OUT);

            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.t_27;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    MixDOOR("MixDOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.OPEN_DOOR);
            list.add(EFFECT.LR_DOOR);
            list.add(EFFECT.RL_DOOR);
            list.add(EFFECT.TB_DOOR);
            list.add(EFFECT.BT_DOOR);

            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_20;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }

    },
    RECT_RANDOM("RECT_RANDOM") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.RECT_RANDOM);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_25;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    BUBBLE("BUBBLE") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.BUBBLE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_24;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    PIN_WHEEL("PIN_WHEEL") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.PIN_WHEEL);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_26;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    CIRCLE_BOMB("CIRCLE_BOMB") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.CIRCLE_BOMB);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_08;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    BAND("BAND") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.BAND);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.band;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    FOUR_TRIANGLE("Four triangle") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.FOUR_TRIANGLE);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_11;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }

    },
    WIPER_LEFT_CORNER("WIPER_LEFT_CORNER") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.WIPER_LEFT_CORNER);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_14;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    WIPER_RIGHT_CORNER("WIPER_RIGHT_CORNER") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.WIPER_RIGHT_CORNER);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_15;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    TOURNADO("TOURNADO") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.TOURNADO);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_18;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    TOURNADO_REV("TOURNADO_REV") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.TOURNADO_REV);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_19;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    RUB_RL("RUB_RL") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.RUB_RL);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_17;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    RUB_LR("RUB_LR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.RUB_LR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_16;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    CIRCLE_IN("Circle in") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.CIRCLE_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_06;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    SQUARE_OUT("Square Out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SQUARE_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_22;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    SQUARE_IN("Square In") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SQUARE_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_23;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }

    },
    OPEN_DOOR("OPEN_DOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.OPEN_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_20;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }

    },
    RL_DOOR("RL_DOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.RL_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_05;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    BT_DOOR("BT_DOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.BT_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_02;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    TB_DOOR("TB_DOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.TB_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_03;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    LR_DOOR("LR_DOOR") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.LR_DOOR);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_04;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    VERTICAL_RECT("VERTICAL_RECT") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.VERTICAL_RECT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_12;
        }

        public int getThemeMusic() {
            return R.raw.song_3;
        }
    },
    HORI_RECT("HORI_RECT") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.HORI_RECT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_13;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    ZIP("ZIP") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.ZIP);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_21;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
    DIAMOND_OUT("Diamond out") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.DIAMOND_OUT);
            list.add(EFFECT.DIAMOND_IN);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_10;
        }

        public int getThemeMusic() {
            return R.raw.song_1;
        }
    },
    SQUARE_R_OUT("SQUARE_R_OUT") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> list = new ArrayList();
            list.add(EFFECT.SQUARE_R_OUT);
            return list;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return new ArrayList();
        }

        public int getThemeDrawable() {
            return R.drawable.t_01;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    };


    String name;

    public abstract ArrayList<EFFECT> getTheme();

    public abstract ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList);

    public abstract int getThemeDrawable();

    public abstract int getThemeMusic();

    private KessiTheme(String str) {
        this.name = "";
        this.name = str;
    }
}
