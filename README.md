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
Displays list of beers loaded from API.
User can see the list of all beer, add beer to the Favorite screen, click on the beer and see its details.

#### FavBeerFragment
Displays list of favorite beer saved on the local database.
User can click on beer and see its details, delete beer from favorite.

#### BeerTinderFragment
This is random beer section. Displays cards with beer (like a tinder, badoo and another date apps). User can like or dislike it by click the button with correct icon or just swipe the card horizontally (right or left).

#### BeerDetails
Displays the beer according to the API.

***
## UI architicture
MVVM
