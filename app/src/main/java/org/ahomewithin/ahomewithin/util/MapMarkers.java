package org.ahomewithin.ahomewithin.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.activities.ChatActivity;
import org.ahomewithin.ahomewithin.activities.UserActivity;
import org.ahomewithin.ahomewithin.models.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by barbara on 3/12/16.
 */
public class MapMarkers {

    private Context mContext;
    private ViewHolder mMapPopupViewHolder;
    private ArrayList<User> users;

    private ArrayList<Marker> markers;

    private HashMap<String, User> markerMap;
    public static final int REQUEST_CODE = 21;

    public static User curUserOnMap;
    public static class ViewHolder {
        public RelativeLayout rlMapPopup;
        public TextView tvTitle;
        public TextView tvSubtitle;
        public TextView tvDescription;
        public ImageView btnChat;
        public ImageView btnTelephone;
        public Marker previousMarker;
        public User previousUser;
        public ViewHolder(View view) {
            rlMapPopup = (RelativeLayout) view.findViewById(R.id.rlMapPopup);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvSubtitle = (TextView) view.findViewById(R.id.tvSubtitle);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            btnChat = (ImageView) view.findViewById(R.id.btnChat);
            btnTelephone = (ImageView) view.findViewById(R.id.btnTelephone);
        }

        public void setAllVisible(int visibility) {
            tvTitle.setVisibility(visibility);
            tvSubtitle.setVisibility(visibility);
            tvDescription.setVisibility(visibility);
            btnChat.setVisibility(visibility);
            btnTelephone.setVisibility(visibility);
        }

    }
    public MapMarkers(Context context) {
        mContext = context;
//        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
//        mMapPopupViewHolder = new ViewHolder(rootView);


        users = new ArrayList<User>();
        markers = new ArrayList<>();
        loadUsers(context);
    }


    public void addMarkersToMap(GoogleMap map) {
        View rootView = ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content);
        mMapPopupViewHolder = new ViewHolder(rootView);

        markerMap = new HashMap<String, User>();
        if (users != null) {
            for (User user : users) {
                createMarkerForUser(map, user);
            }
        }
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                updateMapPopupView(null, null);
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                updateMapPopupView(marker, markerMap.get(marker.getId()));
                return false;
            }
        });
    }

    public ArrayList<Marker> getMarkers() {
        return markers;
    }

    private void updateMapPopupView(Marker marker, final User user) {
        restoreMarker(mMapPopupViewHolder.previousMarker, mMapPopupViewHolder.previousUser);
        if (user != null) {
            highlightMarker(marker, user);
            mMapPopupViewHolder.setAllVisible(View.VISIBLE);
            mMapPopupViewHolder.tvTitle.setText(user.getFullName(true));
            switch (user.type) {
                case SERVICE_PROVIDER:
                    mMapPopupViewHolder.tvSubtitle.setText(R.string.service_provider);
                    break;
                case COMMUNITY:
                    mMapPopupViewHolder.tvSubtitle.setText(R.string.community);
                    break;
            }
            mMapPopupViewHolder.tvDescription.setText(user.description);
            mMapPopupViewHolder.btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ParseClient client = ParseClient.newInstance(mContext);
                    if (!client.isUserLoggedIn()) {
                        curUserOnMap = user;
                        Intent intent = new Intent(mContext, UserActivity.class);
                        Activity origActivity = (Activity)mContext;
                        origActivity.startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        Intent intent = new Intent(mContext, ChatActivity.class);
                        intent.putExtra("otherEmail", user.email);
                        mContext.startActivity(intent);
                    }
                }
            });

            mMapPopupViewHolder.btnTelephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + user.phone));
                    mContext.startActivity(intent);
                }
            });
        } else {
            mMapPopupViewHolder.setAllVisible(View.INVISIBLE);
        }
        mMapPopupViewHolder.previousMarker = marker;
        mMapPopupViewHolder.previousUser = user;
    }

    private void loadUsers(Context context) {
        try {
            // Stubbed out way
            JSONObject response = AHomeWithinClient.getUsers(context);
            users = User.fromJSONArray(response.getJSONArray("users"));
//            ParseClient client = ParseClient.newInstance(context);
//            client.getAllUsers(new ParseClientAsyncHandler() {
//                @Override
//                public void onSuccess(Object obj) {
//                    users = (ArrayList<User>) obj;
//                    Log.d(LOG_TAG, "All users recovered from db");
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    Log.d(LOG_TAG, "Error recovering users from db: " + error);
//
//
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMarkerForUser(GoogleMap map, User user) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(user.lat, user.lng));
        Marker marker = map.addMarker(markerOptions);
        markers.add(marker);
        restoreMarker(marker, user);
        dropPinEffect(marker);
        markerMap.put(marker.getId(), user);
    }

    private void highlightMarker(Marker marker, User user) {
        if (marker != null) {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        }
    }

    private void restoreMarker(Marker marker, User user) {
        if (marker != null) {
//        switch(user.type) {
//            case SERVICE_PROVIDER:
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                break;
//            default: // COMMUNITY:
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//        }
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
    }

    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
            new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                    1 - interpolator.getInterpolation((float) elapsed
                        / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }


}
