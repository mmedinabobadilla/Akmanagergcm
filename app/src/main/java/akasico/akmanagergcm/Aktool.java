package akasico.akmanagergcm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class Aktool{
	static Context context;
	final static String tag="AKASICO";
    public static Delegate delegado=null;
    public interface Delegate{
        public void invoke(String e);
        public Map<String,String> getParams();
    }

    /*
    public static void registrar_string_json_get(final Context context,String uri){
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Cargando");
        pd.show();
        StringRequest sr=new StringRequest(Request.Method.GET,uri,new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.e(tag,response);
                pd.hide();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(tag,error.getMessage());
                pd.hide();
            }
        }
        );
        AppController.getInstance().addToRequestQueue(sr,tag);
    }
    ////////////////////////////////////////////
	public  static void registrar_string_json_post(final Context context,String url){
		final ProgressDialog pd=new ProgressDialog(context);
		pd.setMessage("Cargando");
		pd.show();
		StringRequest sr=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
                        Log.e(tag,response);
                        if(delegado!=null){
                            delegado.respuesta_string(response);
                        }
                        pd.hide();
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e(tag,error.getMessage());
                        pd.hide();
                    }
				}
		){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("correo","akastor2013@gmail.com");
                params.put("id","miid2222222222");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(sr,tag);
	}
	public static void registrar_object_json(final Context context,String uri) {
        final String tag_obj_json = "AKASICO";
        String url = "http://matiasmedina.cl/socialchat/index.php/appregistrar/akastor2013@gmail.com/1";
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Cargando");
        pd.show();
        JsonObjectRequest jor=new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject jobject){
                        Log.e(tag_obj_json,jobject.toString());
                        pd.hide();
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
						VolleyLog.e(tag_obj_json,error.getMessage());
                        Log.e(tag_obj_json,error.getMessage());
						pd.hide();
                    }
                }
        );
		AppController.getInstance().addToRequestQueue(jor,tag_obj_json);
	}*/
    //************************
    public static void post_json_string(Context context,String url, final Delegate del){
        final ProgressDialog p=new ProgressDialog(context);
        p.setMessage("Cargando");
        p.show();
        StringRequest sr=new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        if(delegado!=null) delegado.invoke(response);
                        p.hide();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(tag,error.getMessage());
                        p.hide();
                    }
                }
        ){
          protected Map<String,String> getParams(){
                return del.getParams();
          }
        };
        AppController.getInstance().addToRequestQueue(sr,tag);
    }
	public static void notificacion(Context context,String titulo,String mensaje){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setLargeIcon((((BitmapDrawable)context.getResources()
                                .getDrawable(R.mipmap.ic_launcher)).getBitmap()))
                        .setContentTitle(titulo)
                        .setContentText(mensaje)
                        .setContentInfo("")
                        .setTicker("Mensaje")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,0,intent,0);
        mBuilder.setContentIntent(pi);
        NotificationManager mn=(NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        mn.notify(1010,mBuilder.build());

	}
	public static void alert(Context context,String mensaje,String titulo){
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which){}
		});
		alertDialog.show();
	}
	public static Boolean networkState(Activity context){
		//permiso internet
		ConnectivityManager connMgr = (ConnectivityManager) 
			    context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
			   return true;
			} else {
			   return false;
			}
	}
	public static String networkType(Activity context){
		//permiso access_network_state
		ConnectivityManager connMgr = (ConnectivityManager) 
			    context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
			   String tipo="none";
				switch(networkInfo.getType()){
			   		case ConnectivityManager.TYPE_ETHERNET:
			   			tipo="ethernet";
			   		break;
			   		case ConnectivityManager.TYPE_WIFI:
			   			tipo="wifi";
			   		break;
			   		case ConnectivityManager.TYPE_BLUETOOTH:
			   			tipo="bluetooth";
			   		break;
			   		default:
			   			tipo="unknown";
			    }
				return tipo;
			} else {
			   return "none";
			}
	}
	public static void toast(Context context,String mensaje){
		Toast to=Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
		to.show();
	}
	public static void fullScreen(Activity act){
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);
		act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	public static String getImei(Context context)
	{
		//permiso READ_PHONE_STATE
	    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    return tm.getDeviceId();
	}
	//****************************************************************

}
