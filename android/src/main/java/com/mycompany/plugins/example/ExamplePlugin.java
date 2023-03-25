package com.mycompany.plugins.example;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.health.connect.client.HealthConnectClient;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Example")
public class ExamplePlugin extends Plugin {

    private Example implementation = new Example();

    @PluginMethod
    public boolean checkHC(PluginCall call) {
        final Context context = getContext();

        final int availabilityStatus = HealthConnectClient.sdkStatus(context);

        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE) {
          return false;// early return as there is no viable integration
        }
        if (availabilityStatus == HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED) {
          // Optionally redirect to package installer to find a provider, for example:
          final String uriString = "market://details?id=$providerPackageName&url=healthconnect%3A%2F%2Fonboarding";
          context.startActivity(
            new Intent(Intent.ACTION_VIEW)
                    .setPackage("com.android.vending")
                    .setData(Uri.parse(uriString))
                    .putExtra("overlay", true)
                    .putExtra("callerId", context.getPackageName())
          );
          return true;
        }

        return false;
    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod()
    public void openMap(PluginCall call) {
        Double latitude = call.getDouble("latitude");
        Double longitude = call.getDouble("longitude");

        // more logic

        call.resolve();
    }
}
