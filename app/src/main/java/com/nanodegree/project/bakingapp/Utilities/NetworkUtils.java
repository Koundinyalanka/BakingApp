package com.nanodegree.project.bakingapp.Utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by venka on 3/12/2018.
 */

public final class NetworkUtils {
    public static String BASE_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static URL details=null;
    public static URL getUrl()
    {
        try {
            details=new URL(BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return details;
    }
    public static String getTheResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
