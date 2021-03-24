
# Description - SpeedRoommating
Project is an Android Developer Task I was assigned to. I'm sharing it on github after completing it, of course it needs improvement so it would be a great opportunity to learn and get new ideas about best practices. Any ideas and suggestions are welcome.

The task here is to build an app in which people can see the upcoming events by SpareRoom. The user can scroll and view all the upcoming events and by tapping one of the event cards, it triggers a phone call to the phone number relevant to that event.

**Required functionality:**

1. Listing upcoming events from a secure REST Api;

2. Handling errors
- Api or JSON error.
- Empty (no upcoming events).
- Offline (no internet connectivity)
- Loading states.

3. Should work on Android Lollipop (API level 21) and later;

4. When the user taps on one of the event cards, it should trigger a phone call to the phone number relevant to that event.;

5. Designing of all UI components;


# Implementation 

App has no any complex functionality so I used a simple architecture pattern followed by some Clean Architecture concepts.
The application has one MainActivity (Base) and four packages: EventModel, Fragments, Network, Utils.

- **EventModel:** Contains Event pojo class & EventAdapter.
- **Fragments:** Have all the three fragment class (Upcoming, Archived, Options). 
- **Network:** Contains an ApiService class (fetch JSON data with authorization) & a NetworkClient class (Retrofit Client with base URL of api).
- **Utils:** FragmentAdapter class (for loading fragments) & InternetCheck class are in Utils package.

### Libraries Used

1. **RxJava**
2. **RxAndroid**
3. **Retrofit + OkHttp**
4. **Karumi Dexter (Permissions)**
5. **Glide**
6. **Gson**

### Notable problems and how I solved them
- **Fetching events from Api:** Used retrofit lib for networking along with RxJava (Observables and Observers).
- **Error:** used throwable (RxJava) to notify about error.
- **Empty Event List:** checked size of fetched event list if empty then show empty message.
- **Empty Event Objects in Event List:** ran a loop for retrieved events list if it contained empty event object removed them.
- **Offline (No Connectivity):** create a class (InternetCheck) that returns static boolean as true if internet is available or false.
- **Loading States:** proper loading messages and indicators (ProgressBar, Fake data cards for partially loaded JSON data).
- **High Resolution/Size Images e.g 26Mb:** used glide image loading lib with placeholder (thumbnail) images until image loads properly.
- **Extracting Date Info:** api provided date as a string, converted it into a date object and extracted use full info e.g month, day, starting time and end time in proper formate.
- **Empty Phone number:** Checked if any event object did not have a number value, if not then notify the user.
- **Phone Call Permission:** Used Kerumi Dexter permission lib for better permission handling on all android version.
- **Testing & Validating:** Tested functionality on many android versions and API levels & tested multiple cases with fake errors app passed almost every case and validation.


### Final Message
App contains neat and clean code along with well documented and comments about everything, any developer can easily understand the coding. And almost everything can be optimized even this project, so will be waiting for your email about feedback on this project.
