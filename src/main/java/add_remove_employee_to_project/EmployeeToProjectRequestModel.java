package add_remove_employee_to_project;

import entity.Project;

public class EmployeeToProjectRequestModel {

    Project project;
    Integer toDo;
    boolean toAdd;

    public Project getProject() {
        return project;
    }
    public boolean getToAdd() {
        return toAdd;
    }
    public Integer getToDo() {
        return toDo;
    }
}