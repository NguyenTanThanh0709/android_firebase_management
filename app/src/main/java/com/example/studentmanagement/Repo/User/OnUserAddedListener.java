package com.example.studentmanagement.Repo.User;

public interface OnUserAddedListener {
    void onUserAdded();
    void onError(String errorMessage);
}
