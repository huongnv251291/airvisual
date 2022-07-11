package org.duystudio.myapplication

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.GsonUtils
import com.utility.DebugLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.duystudio.data.*
import org.duystudio.myapplication.databinding.FragmentFirstBinding
import org.json.JSONArray
import org.json.JSONObject

private val JSONObject.data: JSONArray?
    get() {
        return optJSONArray("data")
    }
private val JSONObject.status: String
    get() {
        return optString("status", "")
    }

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var countryError = ArrayList<Country>()
    private var stateError = ArrayList<State>()
    private var cityError = ArrayList<City>()
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    suspend fun getDataForCountry(country: Country): ArrayList<State> {
        val stringDataStates = RetrofitManager.instance?.apiInterface?.getStateSupportInCountries(RetrofitManager.genParamState(country))
        stringDataStates?.let {
            val dataStates = JSONObject(it)
            if (TextUtils.equals(dataStates.status, "success")) {
                val states = getDataFromJsonArray(dataStates.data, State::class.java)
                states.let {
                    states.forEach() { state ->
                        delay(1000)
                        try {
                            state.arrayList = getCityState(country, state)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            stateError.add(state)
                        }

                    }
                    context?.let { context ->
                        AppDatabase.getInstance(context)?.daoState?.inserts(it)
                    }
                    return it
                }
            }
        }
        return ArrayList()
    }

    private fun <T> getDataFromJsonArray(data: JSONArray?, java: Class<T>): ArrayList<T> {
        data?.let {
            val list = ArrayList<T>()
            for (i in 0 until data.length()) {
                list.add(GsonUtils.fromJson(data.getString(i), java))
            }
            return list
        }
        return ArrayList()
    }

    @Throws(Exception::class)
    suspend fun getCityState(country: Country, state: State): ArrayList<City> {
        val stringDataCity = RetrofitManager.instance?.apiInterface?.getCitySupportInState(RetrofitManager.genParamCity(country, state))
        stringDataCity?.let {
            val dataCity = JSONObject(stringDataCity)
            if (TextUtils.equals(dataCity.status, "success")) {
                val cities = getDataFromJsonArray(dataCity.data, City::class.java)
                cities.let {
                    it.forEach() { city ->
                        try {
                            city.list = genState(country, state, city)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            cityError.add(city)
                        }
                    }
                    context?.let { context ->
                        AppDatabase.getInstance(context)?.daoCity?.inserts(it)
                    }
                    return it
                }
            }
        }
        return ArrayList()
    }

    suspend fun genState(country: Country, state: State, it: City): ArrayList<Station> {
        return try {
            val list = ArrayList<Station>()
            val data = RetrofitManager.instance?.apiInterface?.getStationSupportInCity(RetrofitManager.genStation(country, state, it))
            if (!TextUtils.isEmpty(data) && data != null) {
                val dataStation = JSONObject(data)
                if (TextUtils.equals(dataStation.status, "success")) {
                    list.addAll(getDataFromJsonArray(dataStation.data, Station::class.java))
                }
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val data = RetrofitManager.instance?.apiInterface?.getCountries(RetrofitManager.genParamCountry())
            data?.let { it ->
                val jsonObject = JSONObject(it)
                if (TextUtils.equals(jsonObject.status, "success")) {
                    val country = getDataFromJsonArray(jsonObject.data, Country::class.java)
                    country.let {
                        it.forEach() { country ->
                            delay(1000)
                            try {
                                country.arrayList = getDataForCountry(country)
                            } catch (e: Exception) {
                                countryError.add(country)
                                e.printStackTrace()
                            }
                        }
                        context?.let { context ->
                            try {
                                AppDatabase.getInstance(context)?.daoCountry?.inserts(country)
                            }catch (e:Exception){
                                e.printStackTrace()
                            }

                        }
                        DebugLog.loge(countryError)
                        DebugLog.loge(stateError)
                        DebugLog.loge(cityError)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}