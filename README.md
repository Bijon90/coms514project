# Pulse Tracker
COM S 414/514, Fall &#39;16
Team 5
## **Overview:**
Pulse Tracker is an android based mobile application to continuously monitor the heart rate in real time from wearable devices and send an automatic alert to a list of user-defined contacts in case of irregularity.
GitHub Link: [https://github.com/Bijon90/coms514project](https://github.com/Bijon90/coms514project) .

## **Introduction:**
Health-care has the top most priority for everyone and it is a domain where still active research is going on to provide better care and better solutions utilizing medical knowledge and available advanced technologies. Health issues can be sudden, specially health issues increase with increase in age. And it is of utmost interest to provide better solutions and better health monitoring systems. Now-a-days we have plenty of wearable devices that can track a wide range of health readings.

Our project aims to utilize these devices and the tracked data in a more useful and effective way. In this project, we have developed an android based application which takes pulse rate readings as input, collected from wearable devices via Bluetooth or Wi-Fi, and provides a better monitoring system. If there is any irregularity or abnormality in pulse rate, the device automatically sends a notification to a previously user-defined list of contacts (Doctor/ nurse/ Care-Giver) to alert them about the alarming situation.

This application can make significant improvement in care giving to elderly people and prove to be very useful in critical health conditions.

## **Usage:**

The application is specifically useful in cases where the elderly people stay at home and there is nobody to watch over them all the time, especially during the night. It is even harder for care giver to monitor them all the time. There can be even cases when a person is not able to use a lifeline button or call someone for help when they suddenly feel unwell or suffer from serious health breakdown. In such scenarios, this application will send a notification to the alert list and call for immediate attention. We have also added functionality to register the user with details about the person on which pulse rate can vary.

## **Execution Environments:**
Android 6.x Marshmallow
## **Steps to set up the application:**
### **System Requirements:**
Operating System: Windows/Mac/Linux
Memory: 3GB minimum, 8GB recommended, plus 1 GB for the Android Emulator
Disk Space: 2 GB minimum, 4GB recommended
Screen Resolution: 1280 x 800 or more
IDE: Android Studio 2.2.2
JDK: Java Development Kit (JDK) 7 or higher
JVM: OpenJDK 64-Bit Server VM

### **Steps to import the project:**

1. Download the project from Github: [https://github.com/Bijon90/coms514project](https://github.com/Bijon90/coms514project) or do a git pull and save it in a local repository.
2. From the Android Studio menu click File &gt; New &gt; Import Project
3. Select the repository/project folder with the AndroidManifest.xml file and click Ok
4. Select the import options **and click **** Finish ****.**
5. The import process prompts to migrate any library and project dependencies to Android Studio, and add the dependency declarations to the build.gradle files.
6. Android Studio imports the project and displays the project import summary. Review the summary for details about the project restructuring and the import process.

### **Steps to set up the application in emulator:**
1. Android studio using your own device
    a. Connect your phone with the USB device to the machine in which you are running the application from android studio
    b. While selecting emulator, select the device connected through USB
    c. Application will run in your device (no need to install the apk in your device)
2. Android studio using virtual device
    a. Run the application in Android studio from your local machine
    b. Select deployment target as virtual device e.g. Nexus 5 API 23 with android version 6.0
    c. Run the application on selected android device
3. apk in android device
    a. Copy the apk file from /MyApplication2/app/build/outputs/apk/
    b. Save it in your android device
    c. Install the application and run it.

## **Database Set-Up Instructions:**

## **How to use the application:**

### **Welcome to PulseTracker:**
![Welcome Screen](https://s27.postimg.org/5qw094trn/Welcome.jpg)
On opening the app, the following screen is shown:

### **Registration:**

1. If user is not already registered on the app, the user can click on the &#39;Register&#39; button on login page.
![Register](https://s27.postimg.org/itrmsejzn/Register.jpg)
2. The application takes the user to the sign-up page.
![SignUp](https://s29.postimg.org/wq7j52fiv/Sign_In.jpg)
3. The user has to fill email and chosen password to sign up his account on the app.
4. The next step in the registration process is to fill all the personal details of the user.
![Create Alert List](https://s29.postimg.org/8h8etd0d3/Register_User_Details.jpg)
5. Click on &#39;Create Alert List&#39;
6. For alert list: At least one of the alert contact details should be filled to complete the registration process.
7. After filling all the details, click on &#39;Submit&#39; to register the alert list for the user.
![Submit Alert List](https://s29.postimg.org/exlyx6yaf/Set_Alert_List.jpg)
8. Click on &#39;Back&#39;
9. Fill up the fields - Full Name, Address, Gender, Age, Weight, Height, Heart rate and any medical history of the user.
![Save User Details](https://s29.postimg.org/ow6xjo7pz/Set_Profile_Details.jpg)
10. Click &#39;Save&#39;.
11. This completes the registration process and on successful completion the app redirects the user to the initial Login page.

### **Sign In:**
1.  After the user has already registered, they can login to the application by providing the registered email address and password combination and clicking &#39;Sign In&#39; button.
![Sign In](https://s29.postimg.org/wq7j52fiv/Sign_In.jpg)
### **Homepage:**
1. After logging in to the app, it will redirect to the Home Page.
![Home Page](https://s29.postimg.org/9sg61tvyv/Home_Page.jpg)
2. The homepage displays four information:
  a. Continuously monitored pulse rate
  b. Maximum Pulse rate tracked in current session
  c. Minimum Pulse rate tracked in current session.
  d. A graph presenting pulse rates monitored during current session
### **Homepage Menu:**
1. The homepage also has a menu section of the application:
![Home Page Menu](https://s29.postimg.org/zcig89hcn/Home_Page_Menu.jpg)
a. **User Profile details:** in which user can view/update his/her profile anytime.
![User Profile Details](https://s29.postimg.org/ke4mrkro7/User_Details.jpg)
   **Modify** : The modify button enables the user to save all the modified user details.
   **Back To Home Page** : This back button redirects the user back to home page.

    b. **Alert list:** This menu option displays the alert list of the user and provides the option to modify it.
![Alert List Details](https://s29.postimg.org/tyjo0prmf/Alert_List_Details.jpg)
   **Modify** : The modify button enables the user to save all the modified alert list.
   **Back** : This back button redirects the user back to home page.

    c. **Send Report:** This option enables the user to send reports on demand and select contacts from alert list to which the report should be sent to. This will send an email and SMS to the people in alert list.
![Send Report](https://s29.postimg.org/wz53uzsbb/Send_Report.jpg)
d. **Log Out:** Enables user to logout form the application.

### **Contact Us:**
1. Bijon Bose - email: bkbose@iastate.edu
2. Vijayadeepak - email: bvdeepak@iastate.edu
3. Preeti Bhardwaj - email: preetibh@iastate.edu
4. Zhennan Chen - email: [zhennan@iastate.edu](mailto:zhennan@iastate.edu)

## Demo:
[![Pulse Tracker Demo](http://img.youtube.com/vi/BmazcvSKrXw/0.jpg)](https://youtu.be/BmazcvSKrXw)

External Resources:
Android Studio:
[https://developer.android.com/studio/index.html](https://developer.android.com/studio/index.html)
Android Studio Tutorial:
[https://developer.android.com/training/basics/firstapp/index.html](https://developer.android.com/training/basics/firstapp/index.html)
Firebase:
[https://firebase.google.com/](https://firebase.google.com/)
Firebase Tutorial:
[https://cloud.google.com/solutions/mobile/firebase-app-engine-android-studio](https://cloud.google.com/solutions/mobile/firebase-app-engine-android-studio)
[https://firebase.google.com/docs/](https://firebase.google.com/docs/)
