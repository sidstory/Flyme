package cn.sidstory.flyme;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.navigation.NavigationView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager manager = getSupportFragmentManager();
    private Long firstpress = new Long(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        manager.beginTransaction().replace(R.id.setting_fragment, new SettingFragment()).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        DrawerArrowDrawable drawable = toggle.getDrawerArrowDrawable();
        drawable.setColor(Color.BLACK);
        toggle.setDrawerArrowDrawable(drawable);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (!XXPermissions.isHasPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.ACCESS_NETWORK_STATE})) {
            XXPermissions.with(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.ACCESS_NETWORK_STATE).request(new OnPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {

                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                    SmartToast.error("您未授权权限，软件无法正常运行");
                }
            });
        }
        ;
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (this.getTitle().equals("魅工具")) {

            Long presentpress = System.currentTimeMillis();
            if ((presentpress - firstpress) < 2000) {
                finish();
            } else {
                SmartToast.show("再次按下退出");
                firstpress = presentpress;
            }

        } else
            manager.beginTransaction().replace(R.id.setting_fragment, new SettingFragment()).commit();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_setting) {
            manager.beginTransaction().replace(R.id.setting_fragment, new AppSettingFragment()).addToBackStack("Main").commit();
            // Handle the camera action
        } else if (id == R.id.menu_host) {
            manager.beginTransaction().replace(R.id.setting_fragment, new HostFragment()).addToBackStack("Host").commit();

        } else if (id == R.id.menu_about) {
            manager.beginTransaction().replace(R.id.setting_fragment, new AboutFragment()).addToBackStack("Host").commit();

        } else if (id == R.id.menu_donate) {

            manager.beginTransaction().replace(R.id.setting_fragment, new FeedbackFragment()).addToBackStack("Host").commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}