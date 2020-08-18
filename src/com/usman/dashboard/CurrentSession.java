package com.usman.dashboard;

import com.usman.users.User;

public class CurrentSession {
    private static User currentUser;
    private static DashboardController dashboardController;

    public static DashboardController getDashboardController() {
        return dashboardController;
    }

    public static void setDashboardController(DashboardController dc) {
        dashboardController = dc;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentSession.currentUser = currentUser;
    }
}
