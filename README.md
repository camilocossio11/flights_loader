# Flights Loader Application

## Overview
The Flights Loader Application is a Scala-based program that processes data on commercial
flights in the United States for the year 2023. The dataset is sourced from the official
Bureau of Transportation Statistics (BTS) webpage. The application reads flight data,
generates reports, and categorizes flights based on origin and delay status.

## Features
- Processes a dataset of commercial flights from the United States during 2023.
- Generates reports summarizing flight information.
- Filters and categorizes flights by their origin and delay status.
- Supports flexibility in specifying the dataset file:
  - Uses a default dataset packaged with the application.
  - Allows users to provide their own dataset at runtime.

## Dataset
The dataset contains detailed information about commercial flights, including:

- Flight Origin and Destination Codes.
- Departure and Arrival Times.
- Delay Status (e.g., delayed or on-time).
- Carrier Information.

## Requirements
- *Java:* Version 8 or later.
- *Scala:* Version 2.13.x or later.
- *SBT:* For building and running the project.