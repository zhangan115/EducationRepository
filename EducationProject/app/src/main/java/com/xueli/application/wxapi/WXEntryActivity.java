package com.xueli.application.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xueli.application.view.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	private static String TAG = "MicroMsg.WXEntryActivity";

    private IWXAPI api;
    private MyHandler handler;

	private static class MyHandler extends Handler {
		private final WeakReference<WXEntryActivity> wxEntryActivityWeakReference;

		MyHandler(WXEntryActivity wxEntryActivity){
			wxEntryActivityWeakReference = new WeakReference<WXEntryActivity>(wxEntryActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			int tag = msg.what;
			switch (tag) {
				case NetworkUtil.GET_TOKEN: {
					Bundle data = msg.getData();
					JSONObject json;
					try {
						json = new JSONObject(data.getString("result"));
						String openId, accessToken, refreshToken, scope;
						openId = json.getString("openid");
						accessToken = json.getString("access_token");
						refreshToken = json.getString("refresh_token");
						scope = json.getString("scope");
						Intent intent = new Intent(wxEntryActivityWeakReference.get(), LoginActivity.class);
						intent.putExtra("openId", openId);
						intent.putExtra("accessToken", accessToken);
						intent.putExtra("refreshToken", refreshToken);
						intent.putExtra("scope", scope);
						wxEntryActivityWeakReference.get().startActivity(intent);
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	api = WXAPIFactory.createWXAPI(this, "wx1c0c07722cf3fe96", false);
		handler = new MyHandler(this);

        try {
            Intent intent = getIntent();
        	api.handleIntent(intent, this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			SendAuth.Resp authResp = (SendAuth.Resp)resp;
			final String code = authResp.code;
			NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
							"appid=%s&secret=%s&code=%s&grant_type=authorization_code", "wx1c0c07722cf3fe96",
					"753caff5bf55e20b2a0767a81b1cbefb", code), NetworkUtil.GET_TOKEN);
		}
        finish();
	}
}