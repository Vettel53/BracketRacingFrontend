# Bracket Racing Timeslip Manager

A comprehensive web application for tracking and "analyzing" 1/8th mile bracket racing runs. Built with Vaadin for the frontend and Spring Boot for the backend, with H2 database storage.

## Overview

This application helps racers record, track, and hopefully in the future, analyze their drag racing performance data. The application features a responsive, user-friendly interface that adapts to different screen sizes, making it useful at the track or in the garage.

## Features

### Current Functionality

- **Run Tracking**: Record and store detailed information about your 1/8th mile bracket racing runs
- **Track Management**: Select from all IHRA/NHRA drag racing tracks
- **Automatic Weather Integration**: When selecting a track, the application automatically populates weather conditions from a custom microservice API
- **Responsive UI**: User-friendly interface that adapts to desktop, tablet, and mobile screens
- **Basic Authentication**: Initial Spring Security implementation (partially complete)

### Planned Features

- **Vehicle Management**: Create and manage multiple cars/setups
- **Run Prediction**: Algorithms to predict run times based on historical data and current conditions
- **Advanced Analytics**: Visual representation of performance trends
- **Complete Security Implementation**: Finalize Spring Security for user accounts and data protection

## Technology Stack

- **Frontend**: Vaadin (Java-based web framework)
- **Backend**: Spring Boot
- **Database**: H2 in memory -> Future: MySQL
- **Authentication**: Spring Security (Partially Implemented)
- **Weather Data**: Custom microservice API [My API](https://github.com/Vettel53/TrackWeatherAPI)

## Usage (Not Available to Public Yet)

1. **Login**: Use the provided login screen (Note: Authentication is partially implemented)
2. **Add a Run**: Fill in details about your racing run
3. **Select a Track**: Choose a track from the dropdown to automatically populate weather data
4. **View History**: Browse and analyze your previous racing runs

## Architecture

The application follows a standard Spring Boot architecture:

- **Vaadin Views**: Frontend components for user interaction
- **Service Layer**: Business logic and data processing
- **Repository Layer**: Data access and persistence
- **Model Layer**: Entity definitions and data structures
- **Weather API**: Custom microservice [TrackWeatherAPI](https://github.com/Vettel53/TrackWeatherAPI) on my profile, for automatic weather data retrieval : Shoutout [Air Density Online](http://airdensityonline.com/)

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Weather data provided by [Air Density Online](http://airdensityonline.com/)
