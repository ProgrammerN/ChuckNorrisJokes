package com.dvt.chucknorrisjokes.repositories

import com.dvt.chucknorrisjokes.repository.JokesRepository
import com.dvt.chucknorrisjokes.retrofit.JokesApiService
import com.dvt.chucknorrisjokes.room.JokesDatabase

class FakeJokesRepository(apiService: JokesApiService, db: JokesDatabase) : JokesRepository(apiService, db) {


}











