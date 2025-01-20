package lo.radak.shape.app.booking.vaccination.manager

import android.app.Activity
import android.content.Context
import kotlin.random.Random

object DataBaseManager {

    fun save(activity: Activity, prefKey: String, key: String, value: Any) {
        val sharedPreferences = activity.getSharedPreferences(prefKey, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
            }
            apply()
        }
    }

    fun getCapacityByKey(activity: Activity, prefKey: String, key: String): Int {
        val capacity = Random.nextInt(0, 100)
        save(activity, prefKey, key, capacity)
        return capacity
    }
}