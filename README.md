![JitPack](https://img.shields.io/jitpack/version/com.github.leonard-palm/compose-state-events?color=%23%233cdb83&style=for-the-badge)
![GitHub](https://img.shields.io/github/license/leonard-palm/compose-state-events?color=%234185f3&style=for-the-badge)
![GitHub top language](https://img.shields.io/github/languages/top/leonard-palm/compose-state-events?color=%237f52ff&style=for-the-badge)

<br>
<p align="center"> 
   <img height="150" src="https://user-images.githubusercontent.com/20493984/194604428-89476453-8455-4bc5-803d-7ab604c41b9b.png"/> 
</p>

<h1 align="center"> 
   Compose-State-Events
</h1>

A new way to implement One-Time-UI-Events (former SingleLiveEvent) in a Compose world.

This library will help you to avoid implementing any antipatterns regarding One-Time-UI-Events as despribed by Manuel Vivo's [article](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95).

See the samples below on how to effectively use `StateEvent` in your view's state and `EventEffect` in your composables.

# How to use

> Imagine a simple usecase where you need to fetch some list data from an API and display the result on the screen.



### View State Object
```kotlin
data class FlowerViewState(
    val flowers: List<Flower> = emptyList(),
    val isLoadingFlowers: Boolean = false,
    val downloadSucceededEvent: StateEvent = consumed,
    val downloadFailedEvent: StateEventWithContent<Int> = consumed()
)
```
> Imagine we would like to show a green success snackbar or a red failure snackbar after the loading has finished. 
These two events would be represented with the two new fields `downloadSucceededEvent` and `downloadFailedEvent` in our view state object.

Use the `StateEventWithContent<T>` when you need to pass some data to the consumer (the composable). 
In the example above a StringRes with a fitting error description is passed.

### ViewModel
```kotlin
private val _stateStream = MutableStateFlow(FlowerViewState())
val stateStream = _stateStream.asStateFlow()

private var state: FlowerViewState
    get() = _stateStream.value
    set(newState) {
        _stateStream.update { newState }
    }
    
fun loadFlowers(){
  viewModelScope.launch {
    state = state.copy(isLoading = true)
    state = when (val apiResult = loadAllFlowersFromApiUseCase.call()) {
        is Success -> state.copy(flowers: apiResult, downloadSucceededEvent = triggered)
        is Failure -> state.copy(downloadFailedEvent = triggered(R.string.error_load_flowers))
    }
    state = state.copy(isLoading = false)
  }
}

fun onConsumedDownloadSucceededEvent(){
  state = state.copy(downloadSucceededEvent = consumed)
}

fun onConsumedDownloadFailedEvent(){
  state = state.copy(downloadFailedEvent = consumed())
}
```
To trigger an event without any data just use the `triggered` value, otherwise use the `triggered(content: T)` function.
To consume an event without any data just use the `consumed` value, otherwise use the `consumed()` function.

### Composable

```kotlin
val viewModel: MainViewModel = viewModel()
val viewState: MainViewState by viewModel.stateStream.collectAsStateLifecycleAware()

EventEffect(
    event = viewState.downloadSucceededEvent, 
    onConsumed = viewModel::onConsumedDownloadSucceededEvent
) {
    scaffoldState.snackbarHostState.showSnackbar("Download succeeded.")
}

EventEffect(
    event = viewState.downloadFailedEvent, 
    onConsumed = viewModel::onConsumedDownloadFailedEvent
) { stringRes ->
    scaffoldState.snackbarHostState.showSnackbar(context.resources.getString(stringRes))
}
```
The `EventEffect` is a `LaunchedEffect` that will be executed, when the event is in its triggered state. 
When the event action was executed the effect calls the passed `onConsumed` callback to force you to set the view state field to be consumed.

# Installation

```gradle
allprojects {
   repositories {
       ...
       maven { url "https://jitpack.io" }
   }
}
dependencies {
   implementation 'com.github.leonard-palm:compose-state-events:1.0.5'
}
``` 
