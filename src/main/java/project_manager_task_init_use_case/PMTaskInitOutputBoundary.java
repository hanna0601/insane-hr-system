package project_manager_task_init_use_case;

public interface PMTaskInitOutputBoundary {

    PMTaskInitResponseModel prepareSuccessView(PMTaskInitResponseModel responseModel);

    PMTaskInitResponseModel prepareFailView(String error);
}
