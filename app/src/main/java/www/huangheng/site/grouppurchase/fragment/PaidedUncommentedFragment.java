package www.huangheng.site.grouppurchase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import www.huangheng.site.grouppurchase.R;

/**
 * 待评价
 */

public class PaidedUncommentedFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paided_uncommented, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
