package Controller;

import Entity.User;
import Login.LoginInteractor;
import Login.LoginRequestModel;
import Presenter.LoginResponsePresenter;

public class LoginController{

    public static void Login(String username, String password) {
        LoginRequestModel logReqMod = new LoginRequestModel(username, password);
        LoginInteractor.getUser(logReqMod);
    }
}