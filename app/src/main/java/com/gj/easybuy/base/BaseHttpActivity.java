package com.gj.easybuy.base;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.Nullable;

import com.gj.easybuy.R;
import com.gj.easybuy.constant.ServerUrl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.gy.gylibrary.http.HttpConfig;
import com.gy.gylibrary.http.HttpManager;
import com.gy.gylibrary.http.builder.RequestParams;
import com.gy.gylibrary.http.callback.CallBack;
import com.gy.gylibrary.http.constant.ErrorType;
import com.gy.gylibrary.http.constant.entity.ErrorMeg;
import com.gy.gylibrary.http.okhttp.callback.StringCallback;
import com.gy.gylibrary.okhttplib.HttpInfo;
import com.gy.gylibrary.okhttplib.OkHttpUtil;
import com.gy.gylibrary.okhttplib.annotation.RequestType;
import com.gy.gylibrary.okhttplib.callback.Callback;
import com.gy.gylibrary.okhttplib.handler.RequestDisplayHandler;
import com.gy.gylibrary.utils.LogUtils;
import com.gy.gylibrary.utils.NetUtils;
import com.gy.gylibrary.view.dialog.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Activity基类：支持网络请求、加载提示框
 *
 * @author zhousf
 */
public abstract class BaseHttpActivity extends BaseActivity implements RequestDisplayHandler.CallBack {

    private RequestDisplayHandler mainHandler;

    private LoadingDialog loadingDialog;//加载提示框
    private String loadingTipsSucceed; // 提示语言
    private String loadingTipsError; // 提示语言

    public void setLoadingTips(String loadingTipsSucceed, String loadingTipsError) {
        this.loadingTipsSucceed = loadingTipsSucceed;
        this.loadingTipsError = loadingTipsError;
    }

    private final int SHOW_DIALOG = 0x001;
    private final int DISMISS_DIALOG = 0x002;
    private final int LOAD_SUCCEED = 0x003;
    private final int LOAD_FAILED = 0x004;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 同步请求
     *
     * @param info 请求信息体
     * @return HttpInfo
     */
    protected HttpInfo doHttpSync(HttpInfo info) {
        //显示加载框
        getMainHandler().sendEmptyMessage(SHOW_DIALOG);
        info = OkHttpUtil.getDefault(this).doSync(info);
        if (info.isSuccessful()) {
            //显示加载成功
            getMainHandler().sendEmptyMessage(LOAD_SUCCEED);
        } else {
            //显示加载失败
            getMainHandler().sendEmptyMessage(LOAD_FAILED);
        }
        //隐藏加载框
        getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
        return info;
    }


    /**
     * 异步请求 GET
     *
     * @param url      地址
     * @param map      参数
     * @param callback 结果回调接口
     */
    protected void getAsync(String url, Map<String, String> map, final CallBack callback) {
        httpAsync(url, RequestType.GET, null, map, callback);
    }

    /**
     * 异步请求 POST
     *
     * @param url      地址
     * @param map      参数
     * @param callback 结果回调接口
     */
    protected void postAsync(String url, Map<String, String> map, final CallBack callback) {
        httpAsync(url, RequestType.POST, null, map, callback);
    }

    /**
     * 异步请求 GET POST DELETE PUT
     *
     * @param url         地址
     * @param requestType 请求方式
     * @param heads       heads数据
     * @param map         参数
     * @param callback    结果回调接口
     */
    protected void httpAsync(String url, @RequestType int requestType, Map<String, String> heads, Map<String, String> map, final CallBack callback) {
        getMainHandler().sendEmptyMessage(SHOW_DIALOG);
        HttpInfo httpInfo = HttpInfo.Builder()
                .setUrl(ServerUrl.getApiUrl(url))
                .setRequestType(requestType)//设置请求方式
                .addParams(map)//添加接口参数
                //.setDelayExec(1, TimeUnit.SECONDS)//延迟1秒执行
                .addHeads(heads)  //添加RequestHeader
                .build();

        OkHttpUtil.getDefault(mActivity).doAsync(httpInfo, new Callback() {
            @Override
            public void onSuccess(HttpInfo info) {
                //显示加载成功
                getMainHandler().sendEmptyMessage(LOAD_SUCCEED);
                //隐藏加载框
                getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
                getDataForEntityParse(info.getRetDetail(), callback);
            }

            @Override
            public void onFailure(HttpInfo info) {
                //显示加载失败
                getMainHandler().sendEmptyMessage(LOAD_FAILED);
                //隐藏加载框
                getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
                ErrorMeg errorMeg = new ErrorMeg();
                errorMeg.setMsg(info.getRetDetail());
                errorMeg.setStatus(ErrorType.ERROR_DATA);
                callback.onFailure(null, errorMeg);
            }
        });
    }


    /**
     *  异步请求 jsonPost
     * @param url
     * @param object
     * @param callback
     */
    public void jsonPostAsync(String url, Object object, final CallBack callback) {
        jsonAsync(url, RequestType.POST,null,object,callback);
    }

    /**
     * json 提交数据
     *
     * @param url         地址
     * @param requestType 请求类型
     * @param heads       heads数据
     * @param object      提交对象
     * @param callback    回调
     */
    public void jsonAsync(String url, @RequestType int requestType, Map<String, String> heads, Object object, final CallBack callback) {
        getMainHandler().sendEmptyMessage(SHOW_DIALOG);
        HttpInfo httpInfo = HttpInfo.Builder()
                .setUrl(ServerUrl.getApiUrl(url))
                .setRequestType(requestType)//设置请求方式
                //.addParams(map)//添加接口参数
                //.setDelayExec(1, TimeUnit.SECONDS)//延迟1秒执行
                .addParamJson(mApplication.getGson().toJson(object))
                .addHeads(heads)  //添加RequestHeader
                .build();
        OkHttpUtil.getDefault(mActivity).doAsync(httpInfo, new Callback() {
            @Override
            public void onSuccess(HttpInfo info) {
                //显示加载成功
                getMainHandler().sendEmptyMessage(LOAD_SUCCEED);
                //隐藏加载框
                getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
                getDataForEntityParse(info.getRetDetail(), callback);
            }

            @Override
            public void onFailure(HttpInfo info) {
                //显示加载失败
                getMainHandler().sendEmptyMessage(LOAD_FAILED);
                //隐藏加载框
                getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);
                ErrorMeg errorMeg = new ErrorMeg();
                errorMeg.setMsg(info.getRetDetail());
                errorMeg.setStatus(ErrorType.ERROR_DATA);
                callback.onFailure(null, errorMeg);
            }
        });
    }

    protected LoadingDialog getLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(mActivity);
            //点击空白处Dialog不消失
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        return loadingDialog;
    }

    /**
     * 获取通用句柄，自动释放
     */
    protected RequestDisplayHandler getMainHandler() {
        if (null == mainHandler) {
            mainHandler = new RequestDisplayHandler(this, Looper.getMainLooper());
        }
        return mainHandler;
    }


    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                getLoadingDialog().showDialog();
                break;
            case LOAD_SUCCEED:
                getLoadingDialog().succeed(loadingTipsSucceed);
                break;
            case LOAD_FAILED:
                getLoadingDialog().failed(loadingTipsError);
                break;
            case DISMISS_DIALOG:
                if (null != mActivity && !mActivity.isFinishing()) {
                    getLoadingDialog().dismiss();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        if (null != mainHandler)
            mainHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != getLoadingDialog() && getLoadingDialog().isShowing()) {
            getLoadingDialog().dismiss();
        }
    }


    /**
     * get请求
     *
     * @param url
     * @param map
     * @param callback
     */
    @Deprecated
    protected void get(final String url, final Map<String, String> map, final CallBack callback) {
        LogUtils.e(HttpConfig.OK_HTTP_TAG, "get 请求URL:  " + url + "\n提交参数:  " + mApplication.getGson().toJson(map));
        if (NetUtils.isConnected(mActivity)) {// 判断联网
            RequestParams params = new RequestParams();
            if (map != null) {
                params.addQueryStringParameter(map);
            }
            HttpManager.getInstance().requestGet(url, params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if (null != callback) {
                        ErrorMeg errorMeg = new ErrorMeg();
                        errorMeg.setMsg(e.getMessage() + " -->" + id);
                        errorMeg.setStatus(ErrorType.ERROR_DATA);
                        callback.onFailure(e, errorMeg);
                    }
                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "服务器返回异常信息：" + e.getMessage());
                    showShortToast("网络异常");
                }

                @Override
                public void onResponse(String response, int id) {
                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "get 网络返回数据:  " + response);
                    getDataForEntityParse(response, callback);
                }
            });
        } else {
            showShortToast("请检查网络连接是否正常");
            if (null != callback) {
                ErrorMeg errorMeg = new ErrorMeg();
                errorMeg.setMsg(getString(R.string.internet_error));
                errorMeg.setStatus(ErrorType.NO_NETWORK);
                callback.onFailure(new Exception(getString(R.string.internet_error)), errorMeg);
            }
        }
    }

    /**
     * post 表单提交
     *
     * @param url
     * @param map
     * @param callback
     */
    @Deprecated
    protected void post(String url, Map<String, String> map, final CallBack callback) {
        LogUtils.e(HttpConfig.OK_HTTP_TAG, "post 请求URL:" + url + "\n提交参数:    " + mApplication.getGson().toJson(map));

        RequestParams params = new RequestParams();
        if (map != null) {
            params.addBodyParameter(map);
        }
        requestPost(url, params, callback);
    }

    /**
     * postJson 请求
     *
     * @param url
     * @param base
     * @param callback
     */
    @Deprecated
    protected void postJson(String url, Object base, final CallBack callback) {
        LogUtils.e(HttpConfig.OK_HTTP_TAG, "postJson 请求URL:" + url + "\n提交参数:    " + mApplication.getGson().toJson(base));
        if (NetUtils.isConnected(mActivity)) {// 判断联网
            HttpManager.getInstance().postJsonObject(url, base, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if (null != callback) {
                        ErrorMeg errorMeg = new ErrorMeg();
                        errorMeg.setMsg(e.getMessage() + " -->" + id);
                        errorMeg.setStatus(ErrorType.ERROR_DATA);
                        callback.onFailure(e, errorMeg);
                    }
                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "服务器返回异常信息：" + e.getMessage());
                    showShortToast("网络异常");
                }

                @Override
                public void onResponse(String response, int id) {
                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "网络返回数据:  " + response);
                    getDataForEntityParse(response, callback);

                }
            });
        } else {
            showShortToast("请检查网络连接是否正常");
            if (null != callback) {
                ErrorMeg errorMeg = new ErrorMeg();
                errorMeg.setMsg(getString(R.string.internet_error));
                errorMeg.setStatus(ErrorType.NO_NETWORK);
                callback.onFailure(new Exception(getString(R.string.internet_error)), errorMeg);
            }
        }

    }


    /**
     * post 提交
     *
     * @param url
     * @param params
     * @param callback
     */
    @Deprecated
    protected void requestPost(String url, RequestParams params, final CallBack callback) {
        getMainHandler().sendEmptyMessage(SHOW_DIALOG);
        if (NetUtils.isConnected(mActivity)) {// 判断联网
            HttpManager.getInstance().requestPost(ServerUrl.getApiUrl(url), params, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    //显示加载失败
                    getMainHandler().sendEmptyMessage(LOAD_FAILED);
                    //隐藏加载框
                    getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);


                    if (null != callback) {
                        ErrorMeg errorMeg = new ErrorMeg();
                        errorMeg.setMsg(e.getMessage() + " -->" + id);
                        errorMeg.setStatus(ErrorType.ERROR_DATA);
                        callback.onFailure(e, errorMeg);
                    }
                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "服务器返回异常信息：" + e.getMessage());
                    showShortToast("网络异常");
                }

                @Override
                public void onResponse(String response, int id) {
                    //显示加载成功
                    getMainHandler().sendEmptyMessage(LOAD_SUCCEED);
                    //隐藏加载框
                    getMainHandler().sendEmptyMessageDelayed(DISMISS_DIALOG, 1000);

                    LogUtils.e(HttpConfig.OK_HTTP_TAG, "网络返回数据:  " + response);
                    getDataForEntityParse(response, callback);

                }
            });
        } else {
            showShortToast("请检查网络连接是否正常");
            if (null != callback) {
                ErrorMeg errorMeg = new ErrorMeg();
                errorMeg.setMsg(getString(R.string.internet_error));
                errorMeg.setStatus(ErrorType.NO_NETWORK);
                callback.onFailure(new Exception(getString(R.string.internet_error)), errorMeg);
            }
        }

    }


    /**
     * 数据处理
     *
     * @param response
     * @param callback
     */
    private void getDataForEntityParse(String response, final CallBack callback) {
        ErrorMeg errorMeg = parseData(response);
        if (errorMeg.getStatus() == ErrorType.SUCCESS_DATA) {
            if (callback != null) {
                Object obj = mApplication.getGson().fromJson(response, callback.getDataClass());
                if (null != obj) {
                    callback.onSuccess(obj);
                } else {
                    errorMeg.setMsg(getString(R.string.internet_service_error));
                    errorMeg.setStatus(ErrorType.ERROR_DATA);

                    callback.onFailure(new Exception(), errorMeg);
                }
            }
        } else {
            if (callback != null) {
                if (errorMeg.getCode() == ErrorType.ERROR_DATA) {//解析异常捕获的处理
                    errorMeg.setStatus(ErrorType.ERROR_DATA);
                    callback.onFailure(new JsonParseException("数据错误!"), errorMeg);
                } else if (errorMeg.getCode() == ErrorType.ERROR_PARSE) {
                    errorMeg.setStatus(ErrorType.ERROR_PARSE);
                    callback.onFailure(new JsonParseException("数据解析错误!"), errorMeg);
                } else {
                    callback.onFailure(new JsonParseException("未知错误!"), errorMeg);
                }
            }
        }
    }

    /**
     * 解析网络数据
     *
     * @param response
     */
    private ErrorMeg parseData(String response) {
        JsonParser parser = new JsonParser();
        ErrorMeg errorMeg = new ErrorMeg();
        try {
            JsonObject root = parser.parse(response).getAsJsonObject();

            JsonElement codeElement = root.get("Code");
            if (null != codeElement && !codeElement.isJsonNull()) {
                if (200 == codeElement.getAsInt()) {
                    errorMeg.setStatus(ErrorType.SUCCESS_DATA);
                }
                errorMeg.setCode(codeElement.getAsInt());

            }

            JsonElement jElement = root.get("Reason");
            if (null != jElement && !jElement.isJsonNull()) {
                errorMeg.setMsg(jElement.getAsString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorMeg.setStatus(ErrorType.ERROR_PARSE);
            errorMeg.setMsg(getString(R.string.str_data_analysis_error));
        }
        return errorMeg;
    }


    /**
     * 设置程序RequestHeader
     *
     * @param url
     * @return Map<String, String>
     */
    /*protected Map<String, String> getHeads(final String url) {
        final String authorization = null != mApplication.getCurrentLoginUser() ? mApplication.getCurrentLoginUser().getReason() : "";
        final long timestamp = DateUtils.getCurrentTimeMillis();
        final String signature = MD5Utils.getMD5(timestamp + authorization + url);
        return new HashMap<String, String>() {
            {
                put("Authorization", "BasicAuth " + authorization);
                put("timestamp", timestamp + "");
                put("signature", signature);
            }
        };
    }*/


}
