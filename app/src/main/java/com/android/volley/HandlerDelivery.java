package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import static com.android.volley.Request.DeliverState.*;

/**
 * Created by wuyexiong on 2/4/15.
 */
public class HandlerDelivery implements ResponseDelivery {

    private ResponderHandler mHandler;

    public HandlerDelivery() {
        mHandler = new ResponderHandler(this);
    }

    @Override
    public void postStart(Request<?> request) {
        request.addMarker("post-start");
        sendMessage(START, request, null, null);
    }

    @Override
    public void postResponse(Request<?> request, Response<?> response) {
        postResponse(request, response, null);
    }

    @Override
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        request.markDelivered();
        request.addMarker("post-response");
        sendMessage(SUCCESS, request, response, runnable);
    }

    private void sendMessage(int diliverState, Request<?> request, Response<?> response,
            Runnable runnable) {
        Message message = Message
                .obtain(mHandler, diliverState, new Object[]{request, response, runnable});
        mHandler.sendMessage(message);
    }

    @Override
    public void postError(Request<?> request, VolleyError error) {
        request.addMarker("post-error");
        Response<?> response = Response.error(error);
        sendMessage(ERROR, request, response, null);
    }

    @Override
    public void postFinish(Request<?> request) {
        sendMessage(FINISH, request, null, null);
    }

    private void handleMessage(Message msg) {


        Object[] data = (Object[]) msg.obj;
        Request request;
        Response response;
        Runnable runnable;

        request = (Request) data[0];
        response = (Response) data[1];
        runnable = (Runnable) data[2];

        // If this request has canceled, finish it and don't deliver.
        if (request.isCanceled()) {
            if (msg.what != FINISH) {
                request.finish("canceled-at-delivery");
            }
            return;
        }

        switch (msg.what) {
            case START:
                request.deliverStart();
                break;

            case SUCCESS:
                if (response.isSuccess()) {
                    request.deliverResponse(response.result, response.isResponseFromCache);
                } else {
                    request.deliverError(response.error);
                }

                if (response.intermediate) {
                    request.addMarker("intermediate-response");
                } else {
                    request.finish("done");
                }
                break;
            case ERROR:
                request.deliverError(response.error);
                if (response.intermediate) {
                    request.addMarker("intermediate-response");
                } else {
                    request.finish("done");
                }
                break;

            case FINISH:
                request.deliverFinish();
                break;
        }

        if (runnable != null) {
            mHandler.post(runnable);
        }
    }

    private static class ResponderHandler extends Handler {

        private final HandlerDelivery mDelivery;

        private ResponderHandler(HandlerDelivery delivery) {
            super(Looper.getMainLooper());
            mDelivery = delivery;
        }
        @Override
        public void handleMessage(Message msg) {
            mDelivery.handleMessage(msg);
        }

    }
}
