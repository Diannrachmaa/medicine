package com.example.medicine;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.medicine.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ActivityMapsBinding binding;
    Boolean oke = false;
    TextView lat, lon, alamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        lat = findViewById(R.id.latitudemap);
        lon = findViewById(R.id.longitudemap);
        alamat = findViewById(R.id.alamatmap);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
            List<Address> addressList = null;
            @Override
            public void onLocationChanged(@NonNull Location location) {
               try {
                   addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                   if (addressList != null){
                       Address returnAdd = addressList.get(0);
                       StringBuilder stringBuilder = new StringBuilder("");
                       for (int i=0; i<returnAdd.getMaxAddressLineIndex(); i++){
                           stringBuilder.append(returnAdd.getAddressLine(i)).append("\n");
                       }
                       Log.w("My Location Address", stringBuilder.toString());
                   }else {
                       Log.w("My Location Address", "no address");
                   }
               } catch (IOException e){
                   e.printStackTrace();
               }
                if (oke) {
                    String addressLines = addressList.get(0).getAddressLine(0);

                    LatLng lokasisekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasisekarang).title(addressLines));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasisekarang));
                    lat.setText("Latitude: "+String.valueOf(location.getLatitude()));
                    lon.setText("Longitude: "+String.valueOf(location.getLongitude()));
                    alamat.setText("Address: "+(addressLines));
                }
            }
            @Override
            public  void onProviderEnabled(@NonNull String provider) {

            }
            @Override
            public  void onProviderDisabled(@NonNull String provider) {

            }
            @Override
            public  void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        oke = true;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }
}