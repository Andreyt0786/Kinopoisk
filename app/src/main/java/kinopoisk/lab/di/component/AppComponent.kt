package kinopoisk.lab.di.component

import dagger.Component
import kinopoisk.lab.di.module.DbModule
import kinopoisk.lab.di.module.NetworkModule
import kinopoisk.lab.ui.details.DetailsViewModel
import kinopoisk.lab.ui.list.ListViewModel
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DbModule::class])
@Singleton
interface AppComponent {

    val listViewModelFactory: ListViewModel.Factory
    val detailsViewModelFactory: DetailsViewModel.Factory
}