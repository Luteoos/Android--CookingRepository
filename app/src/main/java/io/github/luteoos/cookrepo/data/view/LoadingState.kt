package io.github.luteoos.cookrepo.data.view

sealed class LoadingState {
    object StartLoading : LoadingState()
    object StopLoading : LoadingState()
}
