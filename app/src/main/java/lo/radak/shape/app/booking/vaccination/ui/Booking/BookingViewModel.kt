package lo.radak.shape.app.booking.vaccination.ui.Booking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lo.radak.shape.app.booking.vaccination.models.BookingInfo
import lo.radak.shape.app.booking.vaccination.models.PersonalInfo
import lo.radak.shape.app.booking.vaccination.models.VaccineInfo
import lo.radak.shape.app.booking.vaccination.ui.Booking.enum.BookingStep
import java.util.*

class BookingViewModel : ViewModel() {
    val currentStep = MutableLiveData<BookingStep>()
    var _personalInfo = MutableLiveData<PersonalInfo>()
    var selectedVaccine = MutableLiveData<Pair<VaccineInfo, Boolean>>()
    val bookingInfoLiveData = MutableLiveData<BookingInfo>()
    val showDatePickerEvent = MutableLiveData<Boolean>()

    companion object {
        val TAG = BookingViewModel::class.java.simpleName
    }

    init {
        Log.d(TAG, "init!")
    }

    fun updatePersonalInfo(personalInfo: PersonalInfo) {
        _personalInfo.value = personalInfo
        bookingInfoLiveData.value = BookingInfo(personalInfo = personalInfo)
    }

    fun updateCurrentStep(bookingStep: BookingStep) {
        currentStep.value = bookingStep
    }

    fun updateSelectedVaccine(vaccineInfo: Pair<VaccineInfo, Boolean>) {
        selectedVaccine.value = vaccineInfo
    }

    fun updateBookingInfo(bookingInfo: BookingInfo) {
        bookingInfoLiveData.value = bookingInfoLiveData.value?.copy(
                venue = bookingInfo.venue,
                timeSlot = bookingInfo.timeSlot,
                date = bookingInfo.date,
                vaccineSelected = bookingInfo.vaccineSelected
            )
    }

    fun showDatePicker() {
        showDatePickerEvent.value = true
    }
}