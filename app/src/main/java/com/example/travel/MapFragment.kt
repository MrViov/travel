package com.example.travel

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travel.databinding.FragmentMapBinding
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.lang.Exception

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    val MY_USER_AGENT = "MyOwnUserAgent/1.0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try{
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Configuration.getInstance().userAgentValue = MY_USER_AGENT
            binding = FragmentMapBinding.inflate(inflater)
            binding.map.minZoomLevel = 4.0
            binding.map.maxZoomLevel = 20.0
            binding.map.setMultiTouchControls(true)
            var pointsArray = ArrayList<GeoPoint>()
            for (i in 0 until PlacesArray().places.size){
                pointsArray.add(GeoPoint(PlacesArray().places[i].latitude, PlacesArray().places[i].longitude))
            }
            var roadManager = OSRMRoadManager(requireContext(), MY_USER_AGENT)
            var road = roadManager.getRoad(pointsArray)
            binding.map.controller.setZoom(15.00)
            binding.map.controller.setCenter(pointsArray[0])
            var markers = ArrayList<Marker>()
            for (i in 0 until pointsArray.size){
                var marker = Marker(binding.map)
                marker.position = pointsArray[i]
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.setOnMarkerClickListener(Marker.OnMarkerClickListener
                { _, _ ->
                    var dialog = AlertDialog.Builder(requireContext())
                        .setTitle(PlacesArray().places[i].title)
                        .setMessage(PlacesArray().places[i].describe)
                        .setPositiveButton("Просмотр")
                        { p0, p1 ->
                            var prefs = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE)
                            prefs.edit().putInt("ID", PlacesArray().places[i].id).apply()
                            findNavController().navigate(R.id.action_mapFragment_to_infoFragment)
                        }
                        .setNegativeButton("Назад")
                        { p0, p1 ->

                        }
                        .create()
                        .show()
                    return@OnMarkerClickListener true
                })
                markers.add(marker)
            }
            for (i in 0 until markers.size){
                markers[i].icon = writeOnBitmap(R.drawable.location)
            }
            for (i in markers){
                binding.map.overlays.add(i)
            }
            binding.map.overlays.add(RoadManager.buildRoadOverlay(road))
        }
        catch (ex: Exception){
            Log.e("Exception in MapFragment.kt: ", "$ex")
        }
        return binding.root
    }

    fun writeOnBitmap(drawableId : Int) : BitmapDrawable {
        var bm = BitmapFactory.decodeResource(resources, drawableId).copy(Bitmap.Config.ARGB_8888, true)
        var paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        paint.textSize = 50f
        return BitmapDrawable(resources, bm)
    }
}