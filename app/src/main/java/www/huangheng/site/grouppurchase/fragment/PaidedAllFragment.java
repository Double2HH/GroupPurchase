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
 * 全部
 */

public class PaidedAllFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paided_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
