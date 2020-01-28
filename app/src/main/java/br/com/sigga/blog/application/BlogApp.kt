package br.com.sigga.blog.application

import android.app.Application
import br.com.sigga.blog.R
import br.com.sigga.data.di.dataModule
import br.com.sigga.service.di.serviceModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BlogApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(baseContext)
            androidLogger()
            androidFileProperties()
            modules(listOf(serviceModule, dataModule))
        }

        //Callygraphy
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                            .setFontAttrId(R.attr.fontPath)
                            .build())
                ).build())

    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}
