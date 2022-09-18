# BeerTinder (app in progress)
This is just a study project using the Punk API (https://punkapi.com/)

# --- Specification ---

### Technologies:
1. Kotlin (Coroutines, Coil, Flows)
2. Retrofit + Kotlin Serialization
3. Dagger2
4. RecyclerView
5. ViewPager2
6. Bottom Navigation
7. ViewModel
8. View Bindings
9. Room
10. LiveData

***

### Screens: 
1. Splash screen
2. Host activity
3. List of beers fragment
4. Favorites beer fragment
5. Fragment with ViewPager2
6. Fragment with beer details

***
## Screens logic:

#### Splash screen

#### Host activity
Just holds all fragments

#### BeerListFragment
![image](https://user-images.githubusercontent.com/83790466/190903885-2fe41aeb-d0af-4e07-9428-1472bd4047bb.png)

Displays list of beers loaded from API.
User can see the list of all beer, add beer to the Favorite screen, click on the beer and see its details.

#### FavBeerFragment
![image](https://user-images.githubusercontent.com/83790466/190903917-f91610fd-e8bf-48df-a8b4-4a23a8d9293c.png)

Displays list of favorite beer saved on the local database.
User can click on beer and see its details, delete beer from favorite.

#### BeerTinderFragment
![image](https://user-images.githubusercontent.com/83790466/190903923-a3a145b9-f2d2-4795-ad90-d44b2f114577.png)

This is random beer section. Displays cards with beer (like a tinder, badoo and another date apps). User can like or dislike it by click the button with correct icon or just swipe the card horizontally (right or left).

#### BeerDetails
![image](https://user-images.githubusercontent.com/83790466/190903929-676cc505-3e79-424b-9f60-ebc57973211b.png)

Displays the beer according to the API.

***
## UI architicture
MVVM
