package vcarry.mask;


import com.kessi.photovideomaker.R;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D.EFFECT;

public enum KessiTheme {

    Shine("Shine") {
        public ArrayList<EFFECT> getTheme() {
            ArrayList<EFFECT> mEffects = new ArrayList();
            mEffects.add(EFFECT.CROSS_IN);
            mEffects.add(EFFECT.CROSS_OUT);
            mEffects.add(EFFECT.LOVE_DOOR);
            mEffects.add(EFFECT.WIND_MILL);
            mEffects.add(EFFECT.LEAF);
            mEffects.add(EFFECT.ECLIPSE_IN);
            mEffects.add(EFFECT.HORIZONTAL_RECT);
            return mEffects;
        }

        public ArrayList<EFFECT> getTheme(ArrayList<EFFECT> arrayList) {
            return null;
        }

        public int getThemeDrawable() {
            return R.drawable.circlebomb;
        }

        public int getThemeMusic() {
            return R.raw.song_2;
        }
    },
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
            return R.drawable.tourando;
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
            return R.drawable.lcwiper;
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
            return R.drawable.skew;
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
            return R.drawable.circle_out;
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
            return R.drawable.squareout;
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
            return R.drawable.opendoor;
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
            return R.drawable.rectrandom;
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
            return R.drawable.bubble;
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
            return R.drawable.windmill;
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
            return R.drawable.circlebomb;
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
            return R.drawable.fourtriangle;
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
            return R.drawable.lcwiper;
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
            return R.drawable.rcwiper;
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
            return R.drawable.tourando;
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
            return R.drawable.revtorando;
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
            return R.drawable.rlrub;
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
            return R.drawable.lrrub;
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
            return R.drawable.circle_in;
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
            return R.drawable.squareout;
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
            return R.drawable.squarein;
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
            return R.drawable.opendoor;
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
            return R.drawable.rldoor;
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
            return R.drawable.btdoor;
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
            return R.drawable.tbdoor;
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
            return R.drawable.lrdoor;
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
            return R.drawable.vrect;
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
            return R.drawable.hrect;
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
            return R.drawable.zip;
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
            return R.drawable.diamond;
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
            return R.drawable.boom;
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
