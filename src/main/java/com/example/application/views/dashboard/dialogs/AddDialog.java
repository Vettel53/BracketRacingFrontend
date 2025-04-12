package com.example.application.views.dashboard.dialogs;

import com.example.application.models.AppUser;
import com.example.application.models.Run;
import com.example.application.services.DashboardService;
import com.example.application.views.dashboard.builder.ComponentBuilder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AddDialog {
    // Initialize member variables
    DatePicker datePicker;
    TimePicker timePicker;
    TextField car;
    TextField driver;
    ComboBox<String> trackSelection;
    Select<String> lane;
    BigDecimalField dial;
    BigDecimalField reaction ;
    BigDecimalField sixtyFoot;
    BigDecimalField halfTrack;
    BigDecimalField fullTrack;
    BigDecimalField speed;

    Run createdRun;

    private final DashboardService dashboardService;

    public AddDialog(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    public void showAddRunDialog(AppUser loggedInAppUser) {
        // Instantiate Dialog Vaadin Box
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Run");

        // Call helper method to generate vertical layout
        VerticalLayout dialogLayout = createRunEntryDialogLayout();
        // Add VerticalLayout to dialog box
        dialog.add(dialogLayout);

        // Add save and cancel buttons to dialog footer
        Button addRunButton = new Button("Save");
        Button cancelRunButton = new Button("Cancel");
        dialog.getFooter().add(cancelRunButton);
        dialog.getFooter().add(addRunButton);

        dialog.open();

        // Add event listener to save button
        addRunButton.addClickListener(event -> {
            // Create Run object from form data
            // USE THIS IN PRODUCTION FOR RUN TO BE CONSTRUCTED USING THE FORM FIELDS
            // TOOD: Error handling when any field is null
            createdRun = new Run(
                    loggedInAppUser,
                    datePicker.getValue(),
                    timePicker.getValue(),
                    car.getValue(),
                    driver.getValue(),
                    trackSelection.getValue(),
                    lane.getValue(),
                    dashboardService.truncateToValidDecimal(dial.getValue()),
                    dashboardService.truncateToValidDecimal(reaction.getValue()),
                    dashboardService.truncateToValidDecimal(sixtyFoot.getValue()),
                    dashboardService.truncateToValidDecimal(halfTrack.getValue()),
                    dashboardService.truncateToValidDecimal(fullTrack.getValue()),
                    dashboardService.truncateToValidDecimal(speed.getValue())
            );
            dashboardService.constructRunEntry(createdRun);

            // Save the Run to the database
            //createdRun = dashboardService.constructFakeRunEntry(loggedInAppUser);

            // Close the dialog box
            dialog.close();

            // Refresh grid
            dashboardService.callUpdateGrid(createdRun);

            // Show success notification
            Notification.show("Run saved successfully", 3000, Notification.Position.TOP_CENTER);
        });

        // Add event listener to cancel button
        cancelRunButton.addClickListener(event -> {
            dialog.close();
            Notification.show("Cancelled adding run", 3000, Notification.Position.TOP_CENTER);
        });
    }

    private VerticalLayout createRunEntryDialogLayout() {
        // Initialize instance variables
        datePicker = new DatePicker("Date");
        timePicker = new TimePicker("Time");
        car = new TextField("Car");
        driver = new TextField("Driver");
        trackSelection = createTrackSelectionBox();
        lane = new Select<>();
        lane.setLabel("Select Lane");
        lane.setItems("Left", "Right");
        dial = new BigDecimalField("Dial");
        reaction = new BigDecimalField("Reaction");
        sixtyFoot = new BigDecimalField("60' Foot");
        halfTrack = new BigDecimalField("330' Track");
        fullTrack = new BigDecimalField("660' Track");
        speed = new BigDecimalField("Speed MPH");

        // Initialize FormLayout with correct properties
        FormLayout formLayout = new FormLayout();
        formLayout.add(datePicker, timePicker, car, driver, trackSelection, lane, dial, reaction, sixtyFoot, halfTrack, fullTrack, speed);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1), new FormLayout.ResponsiveStep("600px", 2));

        // Initialize VerticalLayout with correct properties (FormLayout atm)
        VerticalLayout dialogLayout = new VerticalLayout(formLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "24rem").set("max-width", "100%");

        // Return VerticalLayout
        return dialogLayout;
    }

    // Maybe a better way to do this?
    ComboBox<String> createTrackSelectionBox() {
        // Initialize ComboBox with tracks
        ComboBox<String> trackSelectionBox = new ComboBox<>("Tracks");
        trackSelectionBox.setItems(
                "Alabama International Dragway",
                "Atmore Dragway",
                "Baileyton Dragstrip",
                "Cottonwood Dragway",
                "Holiday Raceway",
                "Huntsville Dragway",
                "I22 Motorsports Park",
                "Jakes Dragstrip",
                "Lassiter Mountain Dragway",
                "Mobile Dragway",
                "Montgomery Raceway Park",
                "Phenix Motorsports Park",
                "US-90 Dragway",
                "Alaska Raceway Park",
                "Dome Valley Raceway",
                "Firebird Motorsports Park",
                "Gila Bend Municipal Airport",
                "Tucson Dragway",
                "Centerville Dragway",
                "ECTA Arkansas Challenge",
                "George Rays Wildcat Dragstrip",
                "Prescott Raceway",
                "Auto Club Raceway at Pomona",
                "Avenal Sand Drags",
                "Banning Street Drags",
                "Barona Eighth Mile Drags",
                "Chuckwalla Valley Raceway",
                "Dumont Dunes",
                "El Mirage Dry Lake",
                "Famoso Raceway",
                "Fontana Drags",
                "Irwindale Speedway",
                "Kingdon Drags",
                "Lake Ming",
                "Qualcom Stadium",
                "Redding Dragstrip",
                "Sacramento Raceway",
                "Samoa Drag Strip",
                "Soboba Casino",
                "Sonoma Raceway",
                "Bandimere Speedway",
                "Front Range Airport",
                "Julesburg Dragstrip",
                "Pikes Peak International Raceway",
                "Pueblo Motorsports Park",
                "Western Colorado Dragway",
                "US 13 Dragway",
                "Bradenton Motorsports Park",
                "Emerald Coast Dragway",
                "Gainesville Raceway",
                "Immokalee Regional Raceway",
                "Orlando Speed World",
                "Palm Beach International Raceway",
                "Powerhouse Motorsports Park",
                "ShowTime Dragstrip",
                "ShowTime Speedway",
                "Atlanta Dragway",
                "Brainerd Motorsports Park",
                "Head Hunter Dragway",
                "LaGrange-Troup County Dragway",
                "Middle Georgia Motorsports Park",
                "Paradise Dragstrip",
                "RM Motorsports park",
                "Savannah River Dragway",
                "Screven Speedway",
                "Silver Dollar Raceway",
                "South Georgia Motorsports Park",
                "US 19 Dragway",
                "Hilo Dragstrip",
                "Kauai Raceway Park",
                "Maui Raceway Park",
                "Firebird Raceway",
                "Sage Raceway",
                "Accelaquarter Raceway",
                "Byron Dragway",
                "Central Illinois Dragway",
                "Coles County Dragway",
                "Cordova International Raceway",
                "I57 Dragstrip",
                "Route 66 Raceway",
                "World Wide Technology Raceway",
                "Brown County Dragway",
                "Bunker Hill Dragstrip",
                "Greater Evansville Dragway",
                "Lucas Oil Raceway",
                "Muncie Dragway",
                "Osceola Dragway",
                "US 41 Dragway",
                "Wabash Valley Dragway",
                "Wagler Motorsports Park",
                "Cedar Falls Motorsports Park",
                "Eddyville Raceway Park",
                "I29 Dragway",
                "North Iowa Dragway",
                "Onawa Dragway",
                "Tri-State Raceway",
                "Kansas International Dragway",
                "Mid America Dragway",
                "Midwest Raceway",
                "S. R. C. A. Dragstrip",
                "Beacon Dragway",
                "Beechbend Raceway Park",
                "Decker Boys Raceway",
                "I-64 Motorplex",
                "Kentucky Dragway",
                "London Dragway",
                "Ohio Valley Dragway",
                "Thornhill Dragstrip",
                "US 60 Dragway",
                "Windy Hollow Dragway",
                "No Problem Raceway Park",
                "Platinum Raceway",
                "State Capitol Raceway",
                "Twin City Raceway",
                "New Oxford Dragway",
                "Winterport Dragway",
                "75-80 Dragway",
                "Capitol Raceway",
                "Cecil County Dragway",
                "Maryland International Raceway",
                "Mason-Dixon Dragway",
                "Lapeer International Dragway",
                "Mid Michigan Motorplex",
                "Milan Dragway",
                "Northern Michigan Dragway",
                "Onondaga Dragway",
                "The Ubly Dragway",
                "US 131 Motorsports Park",
                "Brainerd International Raceway",
                "Grove Creek Raceway",
                "Top End Dragways",
                "Battlefield Dragstrip",
                "Byhalia Raceway",
                "Finishline Dragstrip",
                "Gulfport Dragway",
                "Holly Springs Motorsports",
                "Jackson Dragway Park",
                "Street Racing Haven",
                "Benton Speedway",
                "Bonne Terre Drag Strip",
                "Flying H Dragstrip",
                "Jeffers Motorsports Park",
                "Mo-Kan Dragway",
                "Ozark Raceway Park",
                "Thunder Valley Raceway",
                "Thunder Valley Sand Drags",
                "US 36 Raceway",
                "Lewistown Raceway",
                "Lost Creek Raceway",
                "Phillips County Motorsports",
                "Yellowstone Drag Strip",
                "Kearney Raceway Park",
                "Las Vegas Motor Speedway",
                "Northern Nevada Racing Association",
                "Top Gun Raceway",
                "New England Dragway",
                "Island Dragway",
                "Old Bridge Township Raceway Park",
                "Albuquerque Dragway",
                "Alien City Dragway",
                "Arroyo Seco Raceway",
                "Hobbs Motorsports Park",
                "Calverton Executive Airpark",
                "ESTA Safety Park Dragstrip",
                "Empire Dragway",
                "Lancaster Motorplex",
                "Lebanon Valley Speedway",
                "Skyview Drags",
                "710 Dragway",
                "Brewers Speedway",
                "Charlotte Motor Speedway",
                "Coastal Plains Dragway",
                "Farmington Dragway",
                "Fayetteville Dragway",
                "Galot Motorsports Park",
                "Harrells Raceway",
                "Kinston Dragstrip",
                "Mooresville Dragway",
                "Nahunta Dragway",
                "New Thunder Valley Raceway",
                "Northeast Dragway",
                "Outer Banks Speedway",
                "Piedmont Dragway",
                "Rico Dragstrip",
                "Rockingham Dragway",
                "Roxboro Motorsports Dragway",
                "Shadyside Dragway",
                "Wilkesboro Dragway",
                "zMax Dragway",
                "Magic City International Dragway",
                "Dragway 42",
                "Dragway of Magnolia",
                "Edgewater Sports Park",
                "Free Bird Dragway",
                "KD Dragway",
                "Kil-Kare Raceway",
                "Marion County Raceway",
                "National Trail Raceway",
                "Pacemakers Dragway Park",
                "Quaker City Motorsports Park",
                "Summit Motorsports Park",
                "Thompson Raceway Park",
                "Wilmington Airport",
                "Ardmore Dragway",
                "Grand Lake",
                "Hinton Street Races",
                "Lake El Reno",
                "Outlaw Dragway",
                "Sayre Street Races",
                "Thunder Valley Raceway Park",
                "Tulsa Raceway Park",
                "Coos Bay Speedway",
                "Madras Dragstrip",
                "Medford Dragstrip",
                "Portland Raceway",
                "Woodburn Dragstrip",
                "Beaver Springs Dragway",
                "Keystone Raceway Park",
                "Lucky Drag City",
                "Maple Grove Raceway",
                "Numidia Dragway",
                "South Mountain Raceway",
                "Salinas Speedway",
                "Bowman Dragway",
                "Carolina Dragway",
                "Darlington Dragway",
                "Greer Dragway",
                "Pageland Dragway",
                "South Carolina Motorplex",
                "Union County Dragway",
                "Ware Shoals Dragway",
                "Oahe Speedway",
                "Sturgis Dragway",
                "Thunder Valley Dragways",
                "Bristol Dragway",
                "Bristol Motor Speedway",
                "Cherokee Race Park",
                "Clarksville Speedway",
                "Crossville Dragway",
                "Jackson Dragway",
                "Knoxville Dragstrip",
                "Memphis International Raceway",
                "Middle Tennessee Dragway",
                "Music City Raceway",
                "US 43 Dragway",
                "Alamo City Motorplex",
                "Amarillo Dragway",
                "Ben Bruce Memorial Airpark",
                "Big Country Race Way",
                "Caprock Motorplex",
                "Cedar Creek Dragway",
                "Concho Valley Dragway",
                "Desert Thunder Raceway-Texas",
                "Edinburg Motorsports Park",
                "El Paso Motorplex",
                "Houston Motorsports Park",
                "I-30 Dragway",
                "Indian Valley Raceway",
                "Little River Dragway",
                "Lonestar Motorsports Park",
                "Lubbock Dragway",
                "North Star Dragway",
                "Paris Dragstrip",
                "Penwell Knights Raceway",
                "Pine Valley Raceway Park",
                "San Antonio Raceway",
                "Texas Motorplex",
                "The Texas Mile",
                "Wichita Raceway Park",
                "Xtreme Raceway Park",
                "Yellow Belly Dragstrip",
                "Bonneville Speedway",
                "Diamond Mountain Dragway",
                "Colonial Beach Dragway",
                "Eastside Speedway",
                "Elk Creek Dragway",
                "London Dragway",
                "Motor Mile Dragway",
                "Natural Bridge Dragstrip",
                "Richmond Dragway",
                "Sumerduck Dragway",
                "Virginia Motorsports Park",
                "Bremerton Raceway",
                "Pacific Raceways",
                "Renegade Raceway",
                "Spokane County Raceway",
                "Walla Walla Drag Strip",
                "Potomac Airpark",
                "Tri-River Dragway",
                "Great Lakes Dragaway",
                "Rock Falls Raceway",
                "Wisconsin International Raceway",
                "Central Wyoming Motorsports Park",
                "Hypoxia Dragway",
                "Dezzi Raceway",
                "Killarney International Raceway",
                "Midvaal Raceway",
                "Tarlton International Raceway",
                "The Rock Raceway",
                "Amisfield Dragstrip",
                "Bruce McLaren Motorsports Park",
                "Masterton Motorplex",
                "Meremere Dragway",
                "Motueka Airport",
                "Pegasus Bay Drag Racing Club",
                "Finnish Hot Rod Association",
                "Kauhava Airport",
                "Motopark Raceway",
                "Circuit de Clastres",
                "Hockenheimring",
                "Kiskunlachaza Drag",
                "Kvartmilubrautin",
                "Hal Far Raceway",
                "Gardermoen Raceway",
                "Hudik Raceway",
                "Malmo Raceway",
                "Mantorp Park",
                "Sundsvall Raceway",
                "Tierp Arena",
                "Melbourne Raceway",
                "Santa Pod Raceway",
                "Amazonas Dragway",
                "Arena Race Multieventos",
                "Autodromo Internacional de Curitiba",
                "Autodromo de Taruma",
                "Mega Space",
                "Race Park Maringa",
                "Race Valley Motorsports Park",
                "Sao Paulo International Dragway",
                "Speed Park Franca",
                "Toledo Dragway",
                "Velopark",
                "Autodromo Sunix",
                "Curacao International Raceway",
                "Palo Marga International Raceway",
                "Time Line Events",
                "Parkers Raceway",
                "Autodromo Motorsports Park",
                "Autodromo Guadalajara",
                "Autodromo Monclava",
                "Autodromo Monterrey",
                "Autodromo de Hermosillo",
                "Samalayuca Dragway",
                "Sonora Autodromo Cerro Colorado",
                "Autodromo Internacional de Turagua Pancho Pepe Croquer",
                "The Valley Dragway",
                "Area 53 Raceway",
                "Central Alberta Raceways",
                "Medicine Hat Dragway",
                "Rad Torque Raceway",
                "Mission Raceway Park",
                "Nitro Motorsports Park",
                "Northern Lights Raceway",
                "Gimli Motorsports Park",
                "Miramichi Dragway Park",
                "Eastbound Park",
                "Cape Breton Dragway",
                "Grand Bend Motorplex",
                "Shannonville Motorsports Park",
                "St Thomas Raceway Park",
                "Toronto Motorsports Park",
                "Autodrome Montmagny",
                "Drag BSL",
                "Luskville Dragway",
                "Napierville Dragway",
                "Saskatchewan Intl Raceway",
                "Swift Current Airport",
                "Dubbo City Car Club",
                "Sydney Dragway",
                "Alice Springs Inland Dragway",
                "Hidden Valley Dragway",
                "Benaraby Raceway",
                "Ironbark Raceway",
                "Palmyra Dragway",
                "Springmount Raceway",
                "Warwick Dragway",
                "Wide Bay Motor Complex",
                "Willowbank Raceway",
                "Adelaide International Raceway",
                "Steel City Drag Club",
                "The Bend Motorsports Park",
                "TAS Dragway",
                "Calder Park Raceway",
                "Heathcote Park Raceway",
                "South Coast Raceway",
                "Sunset Strip",
                "Swan Hill Dragway",
                "Collie Motorplex",
                "Perth Motorplex",
                "Kuwait Motor Town",
                "Qatar Racing Club",
                "Yas Marina Circuit"
        );

        return trackSelectionBox;
    }

}
