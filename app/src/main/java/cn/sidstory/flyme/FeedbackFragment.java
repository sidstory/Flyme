package cn.sidstory.flyme;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import androidx.fragment.app.Fragment;
import cn.sidstory.flyme.util.AliPayDonate;
import cn.sidstory.flyme.util.WeiXinDonate;


public class FeedbackFragment extends Fragment implements View.OnClickListener {
    Context context=null;
    View view=null;
    ImageView alipay=null;
    ImageView weixinpay=null;
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_feedback,container,false);
        context=container.getContext();
        TextView textView=view.findViewById(R.id.feedback_content);
        alipay=view.findViewById(R.id.img_alipay);
        weixinpay=view.findViewById(R.id.img_weixinpay);
        alipay.setOnClickListener(this);
        weixinpay.setOnClickListener(this);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setLongClickable(true);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboardManager=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText("smice_h@qq.com");
                Snackbar.make(view,"已复制到剪切板",Snackbar.LENGTH_SHORT).show();
                return false;
            }
        });


        getActivity().setTitle("反馈建议");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_alipay:
            {
                donateAlipay("FKX03609KSMX76OWIKHT77");

                break;
            }
            case R.id.img_weixinpay:
            {
                if (!XXPermissions.isHasPermission(context, Manifest.permission_group.STORAGE))
                {
                    XXPermissions.with(getActivity()).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).permission(Manifest.permission.READ_EXTERNAL_STORAGE).request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            donateWechat();
                            Toast.makeText(context, "微信二维码已保存到本地，请选择从相册扫码", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {
                            Toast.makeText(context, "微信支付需要把二维码保存在本地，请授予权限", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }
    }
    private void donateAlipay(String str) {
        if (AliPayDonate.hasInstalledAlipayClient(context)) {
            AliPayDonate.startAlipayClient(getActivity(), str);
        }

    }
    private void donateWechat() {
        InputStream openRawResource = getResources().openRawResource(R.raw.wechat_binarrycode);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuilder.append(File.separator);
        stringBuilder.append("WeChat");
        stringBuilder.append(File.separator);
        stringBuilder.append("donateImg.png");
        String stringBuilder2 = stringBuilder.toString();
        WeiXinDonate.saveDonateQrImage2SDCard(stringBuilder2, BitmapFactory.decodeStream(openRawResource));
        WeiXinDonate.donateViaWeiXin(getActivity(), stringBuilder2);
    }



}
