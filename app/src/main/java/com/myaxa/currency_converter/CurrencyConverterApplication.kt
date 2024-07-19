package com.myaxa.currency_converter

import android.app.Application
import com.myaxa.currency_converter.di.DaggerApplicationComponent

class CurrencyConverterApplication : Application() {
    private val component by lazy { DaggerApplicationComponent.factory().create() }


}
