/*
 * Copyright (c) 2017 SamuelGjk <samuel.alva@outlook.com>
 *
 * This file is part of DiyCode
 *
 * DiyCode is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DiyCode is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DiyCode. If not, see <http://www.gnu.org/licenses/>.
 */

package moe.yukinoneko.diycode.module.login;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.event.LoginEvent;
import moe.yukinoneko.diycode.mvp.MVPBaseActivity;

import static android.text.TextUtils.isEmpty;
import static moe.yukinoneko.diycode.tool.StringHelper.trim;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.username_hint) TextInputLayout usernameHint;
    @BindView(R.id.edit_username) TextInputEditText editUsername;
    @BindView(R.id.password_hint) TextInputLayout passwordHint;
    @BindView(R.id.edit_password) TextInputEditText editPassword;

    private ProgressDialog progressDialog;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("请稍后...");

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isEmpty(s)) {
                    usernameHint.setErrorEnabled(false);
                }
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isEmpty(s)) {
                    passwordHint.setErrorEnabled(false);
                }
            }
        });
    }

    @OnClick({ R.id.button_close, R.id.button_sign_in })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_close:
                finish();
                break;
            case R.id.button_sign_in:
                presenter.login();
                break;
        }
    }

    @Override
    public String getUsername() {
        return trim(editUsername.getText());
    }

    @Override
    public String getPassword() {
        return trim(editPassword.getText());
    }

    @Override
    public void nameError() {
        usernameHint.setError(getString(R.string.username_non_null));
    }

    @Override
    public void passwordError() {
        passwordHint.setError(getString(R.string.password_non_null));
    }

    @Override
    public void LoginSuccessful() {
        EventBus.getDefault().post(new LoginEvent());
        finish();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }
}
