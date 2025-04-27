package com.example.application.views.accountcreation;

import com.example.application.views.accountcreation.builder.AccountCreationBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AccountCreationDialog {

    private final AccountCreationBuilder accountCreationBuilder;

    public AccountCreationDialog(AccountCreationBuilder accountCreationBuilder) {
        this.accountCreationBuilder = accountCreationBuilder;
    }

    public void showAccountCreateDialog() {
        // Instantiate dialog box
        Dialog createDialog = accountCreationBuilder.createAccountCreateDialog();

        createDialog.open();
    }


}
