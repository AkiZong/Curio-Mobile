package example.com.curio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Question_list extends AsyncTask<String, String, JSONArray> {

    public JSONArray tmp = new JSONArray();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        HttpURLConnection client = null;
        try {
            URL url = new URL("http://test.crowdcurio.com/api/curio/");
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(client.getInputStream());

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            try {
                JSONArray jsonArray = new JSONArray(responseStrBuilder.toString());
                tmp = jsonArray;
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
    public void onPostExecute(JSONArray result) {
        super.onPostExecute(result);
        tmp = result;
    }

}
