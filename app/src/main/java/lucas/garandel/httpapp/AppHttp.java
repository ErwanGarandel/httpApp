package lucas.garandel.httpapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AppHttp extends AppCompatActivity {

    Button b;
    WebView web;
    URL url;
    URLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_http);

        b = (Button)this.findViewById(R.id.button);
        web = (WebView)this.findViewById(R.id.webView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    url = new URL("https://9gag.com/");
                    String res = new Loader().execute(url).get();

                    web.loadData(res,"text/html; charset=UTF-8", null);


                }catch(Exception e){

                }

            }
        });
    }

    private class Loader extends AsyncTask<URL, Integer, String> {

        protected void onPostExecute(String res) {

        }

        @Override
        protected String doInBackground(URL... urls){

            try{

                conn = urls[0].openConnection();

                //Settings
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(1000);

                //Connexion
                conn.connect();
                String res = convertStreamToString(conn.getInputStream());
                return res;
            }catch(Exception error){
                return "An error occured while connecting to the server...\n" + error;

            }
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

    }

}


