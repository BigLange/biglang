package com.lang.big.biglang.fragment;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lang.big.biglang.R;

public class HgFragment extends Fragment implements View.OnClickListener{

    private Button btn_tuijian;
    private Button btn_fujin;

    private View hg_tj_or_fj;

    private TuiJianFragment tj_fragment;
    private FuJinFragment fj_fragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Fragment currentFragment;

    public HgFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hg, container, false);
        intoView(v);
        intoEvent();
        intoFrgament();
        return v;
    }

    private void intoFrgament() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        tj_fragment = new TuiJianFragment();
        fragmentTransaction.add(R.id.hg_tj_or_fj, tj_fragment);
        currentFragment = tj_fragment;
        fragmentTransaction.commit();
    }

    private void intoEvent() {
        btn_tuijian.setOnClickListener(this);
        btn_fujin.setOnClickListener(this);
    }

    private void intoView(View v) {
        btn_tuijian = (Button)v.findViewById(R.id.btn_tuijian);
        btn_fujin = (Button)v.findViewById(R.id.btn_fujin);
        hg_tj_or_fj = v.findViewById(R.id.hg_tj_or_fj);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tuijian:
                if(currentFragment!=tj_fragment) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    btn_tuijian.setBackgroundResource(R.drawable.fragment_hg_top_btn_style);
                    btn_fujin.setBackgroundColor(0x00000000);
                    setFragmentShowAndHide(tj_fragment);
                }
                break;
            case R.id.btn_fujin:
                if(currentFragment!=fj_fragment) {
//                    Toast.makeText(getContext(), "界面显示了", Toast.LENGTH_SHORT).show();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    btn_fujin.setBackgroundResource(R.drawable.fragment_hg_top_btn_style);
                    btn_tuijian.setBackgroundColor(0x00000000);
                    if (fj_fragment == null) {
                        fj_fragment = new FuJinFragment();
                        fragmentTransaction.add(R.id.hg_tj_or_fj, fj_fragment);
                    }
                    setFragmentShowAndHide(fj_fragment);
                }
                break;
            default:
                break;
        }
    }

    private void setFragmentShowAndHide(Fragment fragment) {
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        currentFragment = fragment;
    }


}
