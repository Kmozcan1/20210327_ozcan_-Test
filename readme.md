# ***** Test Application

This application was developed for ***** as a part of their hiring process. 

## Structure of the app

The application is developed following the Clean Architecture principles, and has three layers: Domain, Data and Presentation. Due to the size of the project, the layers are not separated into different modules, they are however defined by packages named after them. Below sections will briefly mention the purpose of each layer.

### Domain Layer

The innermost layer of the app, domain layer contains interactors (use cases), domain models, repository interface, the mapper class interface, enumerations and manager classes that handle domain layer related logic. Since the application's scope isn't too big, the each of the latter two only has one example in the current implementation (ProductSortType as enum and InternetManager that handles domain related logic).

### Data Layer

This is the outer layer that makes it possible for the application to access external data (i.e. reading/writing room database data and making rest api calls). Data layer contains repository implementations, retrofit rest api endpoint interfaces, data <-> domain mapper implementations, room database object & DAOs and data layer related models.

### Presentation Layer

Presentation layer is the outer layer that presents the application to the user. It includes ViewModel classes, Activity and Fragments and their ViewStates, presentation <-> data mapper implementations, presentation layer models, adapter classes and presentation layer related models.

## Key components of the app

This section will list the key components of the application (as well as some important libraries that I used), explain their roles in the project and my reason to use them.

### Single-Activity Architecture

Although some complain about the overly complicated Fragment lifecycles, Google encouraged us to build single activity applications back in 2018. In my opinion, Android Jetpack's Navigation component makes it worth using this architecture. The only activity the app has is MainActivity, which contains a toolbar and a FragmentContainerView. FragmentContainerView is responsible for inflating the fragments and handling the navigation logic using the navigation graph. Using this architecture makes it easier for the fragments to access activity methods and variables that are common to all of them, such as checking internet connectivity state and the manipulating the toolbar.

### Data Binding

Data binding is mainly used for assigning buttonClick methods to components and providing better readibility (like accessing a view component with binding.componentName). Although it is possible to declare a variable in the .xml file and assign ViewStates or other values to them, I find that this practive usually causes negative impact for code readibility and organization.

### ViewModels and ViewStates

ViewModel classes handle the presentation layer's communication with the data layer and cause views to stay "dumb". They are injected to the view classes thanks to Dagger Hilt's HiltViewModel annotation. All ViewModel classes implement the abstract BaseViewModel class, which declares a viewState LiveData object based on the type paremeter given to it. This object is observed from the views to act based on the current state of the screen. ViewState classes themselves are data classes, and each of them has an Enum class named State for organization purposes.

### BaseFragment

All fragments in the current implementation are implementations of the abstract BaseFragment class. Inflating the fragments using data binding and providing them their ViewModel classes in the base class helps reduce amount the unnecessary code. BaseFragment provides the abstract onViewBound() and observeLiveData() which the fragments can override. BaseFragment class also provides methods to access MainActivity variables and methods.

### Dependency Injection (and Inversion) with Dagger Hilt

The app heavily utilizes the Dagger Hilt library for dependency injection, including the injection of retrofit, room database and dao instances. Injecting the repository implementations to usecase classes prevent the violation of dependency inversion principle. ViewModels are also injected to their views thanks to Dagger.

### ReactiveX

ReactiveX library (RxKotlin) allows ViewModels to observe emissions from the domain layer, which allows data to be transmitted without domain layer using any presentation layer methods. Both Room and Retrofit provides RxKotlin integration which allows data retrival process to be observed by use cases. RxKotlin also provides methods to help unit testing. Views themselves still use LiveData instead of Rx, since unlike Rx LiveData is lifecycle aware.

### Repositories

Repositories are the innermost part of the data layer and allow its communication with the domain layer. To implement Repository Pattern, repository interfaces are located in the domain layer and can have multiple implementations in the data layer (although currently each repository only has one).

### Retrofit

Retrofit endpoint interfaces are located in the data layer and allow the application to make REST api calls. They can be accessed from the repository classes.

### Room

Room is used to store browsing history in a local database. Tables and their entries are represented with Entity data classes and methods to insert/retrieve/delete data are implemented in data access objects. These methods can be accessed from the repository classes.

### Models and Mappers

For the purpose of separating the layers, each layer has their own version of models such as: Product for domain layer, ProductUiModel for presentation layer and ProductDataModel for data layer. These models are mapped to data data <-> domain and domain <-> presentation with mappers. The mappers are located in data and presentation layers. Domain layer can't access these as it would violate dependency inversion principle.

### Interactors (use cases)

UseCase classes are the domain layer's outer part that communicates with the presentation and data layers. When a view wants something from the domain layer, it calls a ViewModel function that observes an Rx object by calling a UseCase function. UseCase methods are implementation of different Rx observable variations; currently the project has base clases for Completable, Observable and Single Rx objects. Current implementation allows complex scenarios where one UseCase class can use others and combine their results (like using two UseCase classes that use repository to retrieve REST API data, and then using another UseCase class to do some domain layer logic, then returning the result to the presentation layer).

## Final Notes

* The product list has very slight lag when it is scrolled very fast the first time. The images are loaded with Glide asyncronously and the list itself is RecyclerView, so I don't think the number of products are the issue. Using fixed image sizes didn't help either. Using LinearLayout instead of GridLayout for the RecyclerView solves the problem, but since displaying two items per row is more practical and the lag is not very noticable (and only happens the first time), I decided to leave it as it is. In real life scenario, I would also probably fetch fewer items per api call and apply pagination, depending on the performance implications.
* I tried adding id, cod10, code10 etc. parameters to the /item call. They didn't work so I assume as the project description says, regardless of which product is selected, same information will be displayed. Nevertheless, product detail page should work with different api responses too, including color-available size combinations.
* The UI was designed for portrait orientation, but to show that fragment states are handled on configuration change, I allowed it to work with landscape too.
* An anti-plagiarism software may show similarities to a couple of projects that belong to a github user name 'Kmozcan1'. That user is me, I did similar projects before and my base classes remain mostly the same.
