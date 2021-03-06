package com.cygnet.projecttemplate.views.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cygnet.projecttemplate.R;
import com.cygnet.projecttemplate.databinding.FragmentHomeBinding;
import com.cygnet.projecttemplate.mvvm.viewModels.HomeViewModel;
import com.cygnet.projecttemplate.mvvm.views.HomeView;
import com.cygnet.projecttemplate.views.adapters.PagerAdapter;


/**
 *Name : HomeFragment
 *<br> Created by 1730 on 11/7/2017
 *<br> Modified by 1730 on 11/7/2017
 *<br> Purpose : This fragment is used to display the home page of the application after login.
 * also contains the method to get album list.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private View mRootView;
    private FragmentHomeBinding mBinding;
    private HomeViewModel mHomeViewModel;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mRootView = mBinding.getRoot();
        mBinding.setEvent(this);

        setupViewPager();

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHomeViewModel = (HomeViewModel) getViewModel(HomeViewModel.class).inIt(mContext, this);
    }


    /**
     * Name : setupViewPager
     *<br> Created by 1730 on 11/13/2017
     *<br> Modified by 1730 on 11/13/2017
     *<br> Purpose : To set fragments in viewPager Adapter
     */
    private void setupViewPager() {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        adapter.addFragment(new AlbumFragment(), "Albums");
        adapter.addFragment(new UserFragment(), "Users");
        mBinding.viewPager.setAdapter(adapter);
        mBinding.resultTabs.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
