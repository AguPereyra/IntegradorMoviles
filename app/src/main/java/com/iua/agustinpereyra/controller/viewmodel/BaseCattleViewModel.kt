package com.iua.agustinpereyra.controller.viewmodel

import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.entities.Cattle

/**
 * BaseCattleViewModel is an interface that declares the minimum that a ViewModel
 * needs in order to be used as a ViewModel for showing lists of cattle data, as
 * required by the abstract class BaseCattleListFragment.
 */
interface BaseCattleViewModel {
    val cattleList: LiveData<List<Cattle>>
}