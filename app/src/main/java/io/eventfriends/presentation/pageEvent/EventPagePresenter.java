package io.eventfriends.presentation.pageEvent;

import io.eventfriends.domain.interactors.LoadEventsInteractor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class EventPagePresenter {
    private EventPageView mView;
    private CompositeDisposable mCompositeDisposable;
    private LoadEventsInteractor mLoadEventsInteractor;

    public EventPagePresenter(CompositeDisposable compositeDisposable,
                              LoadEventsInteractor loadEventsInteractor) {
        mCompositeDisposable = compositeDisposable;
        mLoadEventsInteractor = loadEventsInteractor;
    }

    public void onAttach(EventPageView view) {
        mView = view;
    }

    public void onDetach() {
        mView = null;
        mCompositeDisposable.clear();
    }

    public void getEvent(String key) {

        mCompositeDisposable.add(mLoadEventsInteractor.getCurrentEventLoadState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadState -> {
            switch (loadState.getStatus()) {
                case SUCCESS:
                    mView.hideProgress();
                    break;
                case RUNNING:
                    mView.showProgress();
                    break;
                case FAILED:
                    mView.hideProgress();
                    mView.showToast(loadState.getMsg());
                    break;
            }
        }));

        mCompositeDisposable.add(mLoadEventsInteractor.getEvent(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( event -> {
                    if (event != null && mView != null)
                        mView.setEvent(event);
                }));
    }
}
