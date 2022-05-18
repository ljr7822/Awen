package com.example.awen

import android.app.Application
import android.content.Context

/**
 *
 * @Author: iwen
 * @CreateDate: 2022/5/18
 * @Package: com.example.awen
 */
class AwenApplication : Application() {

    companion object {
        var _context: Application? = null

        fun getContext(): Context {
            return _context!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }

}