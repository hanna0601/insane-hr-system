package controller;

import check_profile_validation.*;
import presenter.CheckProfilePresenter;
import view_model.Table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

public class CheckProfileController implements PropertyChangeListener {
    private final CheckProfileInputBoundary interactor;

    public CheckProfileController(CheckProfileGateway gateway, CheckProfileOutputBoundary presenter) {
        this.interactor = new CheckProfileInteractor(gateway, presenter);
    }

    public void create(Integer requester, Integer target){
        CheckUserFileRequestModel requestModel = new CheckUserFileRequestModel(requester, target);
        interactor.checkUserProfile(requestModel);
    }

    public void create(Integer requester, UUID target){

        CheckOrgFileRequestModel requestModel = new CheckOrgFileRequestModel(requester, target);
        interactor.checkOrgProfile(requestModel);
    }
    /**
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
