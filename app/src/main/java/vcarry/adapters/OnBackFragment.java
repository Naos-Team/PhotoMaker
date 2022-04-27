package vcarry.adapters;

import java.util.ArrayList;

import vcarry.mask.KessiMaskBitmap3D;

public interface OnBackFragment {
    public void onEndChoose();
    public void onBackFragment(ArrayList<KessiMaskBitmap3D.EFFECT> arrayList);
}
