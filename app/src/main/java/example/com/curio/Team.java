package example.com.curio;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Team extends AsyncTask<String, String, JSONObject> {

    public JSONObject tmp = new JSONObject();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection client = null;
        try {
            URL url = new URL("http://test.crowdcurio.com/api/user/profile/");
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(client.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            try {
                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                tmp = jsonObject;
            }catch (JSONException e){

            }finally {
            }

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } finally {
            client.disconnect();
        }
        return (tmp);
    }

    @Override
    public void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
        tmp = result;
    }
}

