package com.example.application.views;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.awt.*;


@Route("")
@PermitAll
@UIScope
public class MainView extends AppLayout {

    private final SecurityService securityService;

    public MainView(SecurityService securityService) {
        this.securityService = securityService;

        // Maybe rewrite this to make it cleaner
        DrawerToggle toggle = new DrawerToggle();
        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav nav = getSideNav();

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        Button logoutButton = new Button("Logout", click -> securityService.logout());

        addToDrawer(scroller, logoutButton);
        addToNavbar(toggle, title);

        setContent(new H1("Bracket Racing App WIP"));
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Dashboard", "/dashboard",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("nil", "/orders", VaadinIcon.CART.create()),
                new SideNavItem("nil", "/customers",
                        VaadinIcon.USER_HEART.create()),
                new SideNavItem("nil", "/products",
                        VaadinIcon.PACKAGE.create()),
                new SideNavItem("nil", "/documents",
                        VaadinIcon.RECORDS.create()),
                new SideNavItem("nil", "/tasks", VaadinIcon.LIST.create()),
                new SideNavItem("nil", "/analytics",
                        VaadinIcon.CHART.create())
        );
        return sideNav;
    }

}

