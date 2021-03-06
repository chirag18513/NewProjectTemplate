package com.cygnet.projecttemplate.views.activities;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.cygnet.framework.utils.AppLog;
import com.cygnet.framework.views.activities.BaseActivity;
import com.cygnet.model.entities.NavItemModel;
import com.cygnet.projecttemplate.R;
import com.cygnet.projecttemplate.databinding.ActivityBaseBinding;


/**
 * Name : AppBaseActivity
 *<br> Created by 1618 on 10/30/2017
 *<br> Modified by 1618 on 10/30/2017
 *<br> Purpose :
 * This class will contains the base activity which is
 * extended by all other activities.
 *
 * It contains toolbar, methods for setting fragments, Back Handling mechanism Etc.
 */
public class AppBaseActivity extends BaseActivity {

    private String TAG = AppBaseActivity.class.getSimpleName();
    private String prevTag;

    ActivityBaseBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_base);
        setSupportActionBar(mBinding.toolbar);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public <T extends ViewModel> T getViewModel(Class<T> viewModelClass){
        return ViewModelProviders.of(this).get(viewModelClass);
    }

    /**
     * Name : AppBaseActivity setFragment
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :
     * This method will be used to set the fragment to the container.
     * and also manage the backStackEntryCount of the fragment.
     *
     * @param sNavItemModel : Object of {@link NavItemModel} which contains the info related to fragments
     *                      which we want to set into container.
     */
    public void setFragment(NavItemModel sNavItemModel) {
        AppLog.d(TAG, "setSelectedNavItem():" + sNavItemModel.getFragment().getSimpleName());
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        loadFragment(sNavItemModel, mFragmentTransaction);
        mFragmentTransaction.commit();

        AppLog.i("Count", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        // SETUP THE BACK BUTTON.
        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }



    /**
     * Name : AppBaseActivity loadFragment
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :
     * This method will be executed by setFragment method. and this method will
     * first detach the existing fragment from container and then set new fragment to the container.
     * and also add fragment to backstack.
     *
     * @param aNavItemModel : Object of {@link NavItemModel} which we want to set in container.
     * @param aFragmentTransaction  : Object of {@link FragmentTransaction} to manage the transaction
     *                              between switching of fragments.
     */
    public void loadFragment(NavItemModel aNavItemModel, FragmentTransaction aFragmentTransaction) {
        Fragment fragment;

        if (prevTag != null && !prevTag.equals(aNavItemModel.getTag())) {
            fragment = getSupportFragmentManager().findFragmentByTag(prevTag);
            if (fragment != null) {
                aFragmentTransaction.detach(fragment);
            }
        }

        fragment = getSupportFragmentManager().findFragmentByTag(aNavItemModel.getTag());

        if (fragment == null) {
            fragment = Fragment.instantiate(this, aNavItemModel.getFragment().getName(), aNavItemModel.getBundle());
            aFragmentTransaction.replace(R.id.flContainer, fragment, aNavItemModel.getTag());
        } else if (fragment.isDetached()) {
            fragment = Fragment.instantiate(this, aNavItemModel.getFragment().getName(), aNavItemModel.getBundle());
            aFragmentTransaction.replace(R.id.flContainer, fragment, aNavItemModel.getTag());
        } else {
            fragment = Fragment.instantiate(this, aNavItemModel.getFragment().getName(), aNavItemModel.getBundle());
            aFragmentTransaction.replace(R.id.flContainer, fragment, aNavItemModel.getTag());
        }

        aFragmentTransaction.addToBackStack(null);
        prevTag = aNavItemModel.getTag();
        AppLog.e("DATA","prevtag "+prevTag);



    }



    /**
     * Name : AppBaseActivity detachFragment
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :
     *  This method is used to detach the specific fragment. from the backstack of the fragment
     * transaction.
     *
     * @param aNavItemModel : Object of {@link NavItemModel} which we want to set in container.
     * @param aFragmentTransaction  : Object of {@link FragmentTransaction} to manage the transaction
     *                              between switching of fragments.

     */
    private void detachFragment(NavItemModel aNavItemModel, FragmentTransaction aFragmentTransaction) {
        AppLog.d(TAG, "detachFragment() aNavItemModel:" + aNavItemModel.getTag());
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(aNavItemModel.getTag());
        if (fragment != null && !fragment.isDetached()) {
            aFragmentTransaction.detach(fragment);
        }
    }

    /**
     * Name : AppBaseActivity hideSoftKeybord
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :This method is used to hide the soft keyboard forcefully.
     */
    public void hideSoftKeybord() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }



    /**
     * Name : AppBaseActivity clearAllTopFragment
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :This method is used to clear all the fragments of the backstack.
     */
    public void clearAllTopFragment() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

    }

    /**
     * Name : AppBaseActivity onBackPressed
     *<br> Created by 1618 on 10/30/2017
     *<br> Modified by 1618 on 10/30/2017
     *<br> Purpose :This method will handle the back button pressed event.
     */
    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            } else {
                getSupportFragmentManager().popBackStackImmediate();
            }
        } else {
            super.onBackPressed();
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
