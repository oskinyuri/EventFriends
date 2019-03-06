package io.eventfriends.presentation.main;

public class MainPresentor {

    private MainView mView;



    public void onAttach(MainView view){
        mView = view;
    }

    public void onDetach(){
        mView = null;
    }

}
