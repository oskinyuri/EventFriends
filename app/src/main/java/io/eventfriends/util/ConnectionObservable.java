package io.eventfriends.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class ConnectionObservable extends Observable<ConnectionModel> {

    private Context context;
    private Observer<? super ConnectionModel> mObserver;
    private ConnectionObservable mConnectionObservable;

    public ConnectionObservable(Context context) {
        this.context = context;
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras()!=null) {
                NetworkInfo activeNetwork = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    switch (activeNetwork.getType()){
                        case ConnectivityManager.TYPE_WIFI:
                            mObserver.onNext(new ConnectionModel(ConnectionModel.WIFI_DATA,true));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            mObserver.onNext(new ConnectionModel(ConnectionModel.MOBILE_DATA,true));
                            break;
                    }
                } else {
                    mObserver.onNext(new ConnectionModel(0,false));
                }
            }
        }
    };

    @Override
    protected void subscribeActual(Observer<? super ConnectionModel> observer) {
        mObserver = observer;
    }

    @Override
    protected void finalize() throws Throwable {
        context.unregisterReceiver(networkReceiver);
        super.finalize();
    }


}