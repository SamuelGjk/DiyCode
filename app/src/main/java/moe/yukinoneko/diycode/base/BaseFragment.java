package moe.yukinoneko.diycode.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moe.yukinoneko.diycode.R;

/**
 * Created by SamuelGjk on 2017/4/29.
 */

public abstract class BaseFragment extends RxFragment {

    protected Unbinder unbinder;

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindColor(R.color.navigation)
    int navigationColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(provideViewLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, root);
        if (toolbar != null) {
            DrawerArrowDrawable navigationIcon = new DrawerArrowDrawable(getContext());
            navigationIcon.setColor(navigationColor);
            toolbar.setNavigationIcon(navigationIcon);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void setToolBarTitle(@StringRes int resId) {
        if (toolbar != null) {
            toolbar.setTitle(resId);
        }
    }

    protected void onNavigationClick() {}

    protected abstract int provideViewLayoutId();
}
