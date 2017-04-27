package moe.yukinoneko.diycode.module.login;

import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        String getUsername();

        String getPassword();

        void nameError();

        void passwordError();

        void LoginSuccessful();

        void showProgress(boolean show);
    }

    interface Presenter extends BasePresenter<View> {
        void login();
    }
}
