package moe.yukinoneko.diycode.module.user;

import moe.yukinoneko.diycode.bean.User;
import moe.yukinoneko.diycode.mvp.BasePresenter;
import moe.yukinoneko.diycode.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UserContract {
    interface View extends BaseView {
        void updateUser(User user);

        void error();
    }

    interface  Presenter extends BasePresenter<View> {
        void fetchUser(String userLogin);
    }
}
