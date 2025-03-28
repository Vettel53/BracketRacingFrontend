package com.example.application.views.dashboard;

import com.example.application.models.Run;
import org.springframework.stereotype.Component;

public interface DashboardViewListener {
    void onEditButtonClick(Run runToEdit);
    void onDeleteButtonClick(Run runToDelete);
    void onAddRunButtonClick();
}
