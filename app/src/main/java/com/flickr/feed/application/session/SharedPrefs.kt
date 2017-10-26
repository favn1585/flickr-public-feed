package com.flickr.feed.application.session

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by andrew on 26/10/2017.
 *
 * Helps with manipulating around-session responsibilities like
 * storing the state of first time user experience hints
 */
class SharedPrefs
@Inject constructor(val context: Context) {

    private val PREFERENCES_KEY = "flickr_public_feed"

    private val GRID_TIP = "grid_tip"

    //region First Time User Experience
    /**
     * Get [GRID_TIP] shared preferences value
     */
    fun isGridTipShowed(): Boolean = getSharedPreferences().getBoolean(GRID_TIP, false)

    /**
     * Store [GRID_TIP] shared preferences value
     */
    fun storeIsGridTipShowed(value: Boolean) {
        getEditor().putBoolean(GRID_TIP, value).apply()
    }
    //endregion

    //region private methods
    /**
     * Get app shared preferences editor
     *
     * @return `Editor` for session related shared preferences
     */
    fun getEditor(): SharedPreferences.Editor = getSharedPreferences().edit()

    /**
     * Get app shared preferences
     *
     * @return session related shared preferences using [PREFERENCES_KEY]
     */
    private fun getSharedPreferences(): SharedPreferences =
            context.applicationContext.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
    //endregion
}