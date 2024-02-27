package com.expofp

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.expofp.common.GlobalLocationProvider
import com.expofp.common.LocationProvider
import com.expofp.crowdconnected.CrowdConnectedProvider
import com.expofp.crowdconnected.Mode
import com.expofp.fplan.FplanView
import com.expofp.fplan.Settings
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp


class ExpofpViewManager : SimpleViewManager<View>() {
  override fun getName() = "ExpofpView"
  companion object {
    lateinit  var appContext: Context
  }
  override fun createViewInstance(reactContext: ThemedReactContext): View {
    var settings: Settings = Settings();

    val lpSettings = com.expofp.crowdconnected.Settings(
      "bd931173",
      "5139171006c448219db05ca250afff45",
      "ENYt9213LF8339rlS8P7tH2341VEdK52",
      Mode.IPS_ONLY
    )
    val locationProvider: LocationProvider = CrowdConnectedProvider(
      ExpofpViewManager.appContext as Application
      , lpSettings)

    GlobalLocationProvider.init(locationProvider)
    GlobalLocationProvider.start()


    var view = FplanView(reactContext)
    view.init("https://demo.expofp.com", settings);
    return view;
  }
  @ReactProp(name = "url")
  fun setUrl(view: FplanView, string: String?) {
    view.init(string)
  }

  @ReactProp(name = "color")
  fun setColor(view: View, color: String) {
    view.setBackgroundColor(Color.parseColor(color))
  }
}
