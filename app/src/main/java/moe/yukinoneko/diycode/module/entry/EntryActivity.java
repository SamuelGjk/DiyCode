package moe.yukinoneko.diycode.module.entry;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import moe.yukinoneko.diycode.R;
import moe.yukinoneko.diycode.module.main.MainActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by SamuelGjk on 2017/4/28.
 */

public class EntryActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int RC_EXTERNAL_STORAGE = 10000;
    private static final String[] PERMISSIONS = { Manifest.permission.READ_EXTERNAL_STORAGE,
                                                  Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        askPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_EXTERNAL_STORAGE)
    private void askPermissions() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            toMain();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_external_storage),
                    RC_EXTERNAL_STORAGE,
                    PERMISSIONS
            );
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setRationale(R.string.rationale_ask_again)
                    .build()
                    .show();
        } else {
            finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
                toMain();
            } else {
                finish();
            }
        }
    }

    private void toMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
