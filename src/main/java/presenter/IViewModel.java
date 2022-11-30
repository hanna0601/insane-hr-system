package presenter;

import check_profile_validation.VisualLevel;
import view_model.Table;
import view_model.ViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.UUID;

public interface IViewModel extends PropertyChangeListener{



    void setFunction(UseCase[] useCases);
    /*
     * Add a new observer to observe the changes to this class.
     * @param observer
     */
    void addObserver(PropertyChangeListener observer);

    void updateFrameName(String newFrameName);

    void updateInfoTitle(String newInfoTitle);

    void updateIntro(String newIntro);

    void updateLeftTable(Table newLeftTable);

    void updateRightTable(Table newRightTable);

    void updateAll(ViewModel newDataModel);

    PropertyChangeSupport getObservable();

    String getFrameName();

    void setFrameName(String frameName);

    String getInfoTitle();

    void setInfoTitle(String infoTitle);

    String getIntro();

    void setIntro(String intro);

    Table getLeftTable();

    void setLeftTable(Table leftTable);

    Table getRightTable();

    void setRightTable(Table rightTable);

    void propertyChange(PropertyChangeEvent evt);

//    ScreenType getScreenType();
//
//    void setScreenType(ScreenType screenType);

    VisualLevel getVisualLevel();

    void setVisualLevel(VisualLevel visualLevel);
    Integer getRequesterID();
    void setRequesterID(Integer requesterID);

    UseCase[] getUseCases();

    void setFunctions(UseCase[] useCases);

    UUID getOid();

    void setOid(UUID oid);

    Integer getCurrUid();

    void setCurrUid(Integer uid);
}
