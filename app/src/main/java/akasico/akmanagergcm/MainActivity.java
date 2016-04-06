package akasico.akmanagergcm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    Activity actual;
    EditText txt;
    Button btn;
    View.OnClickListener eventBtn=new View.OnClickListener(){
        public void onClick(View v){
            GCMClientManager gcm=new GCMClientManager(actual,"151654249048");
            gcm.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler(){
                public void onSuccess(final String regid,boolean isNewRegistration){
                    Aktool.delegado=new Aktool.Delegate() {
                        @Override
                        public void invoke(String e) {
                            Aktool.toast(actual,e);
                        }
                        @Override
                        public Map<String, String> getParams() {
                            Map<String,String> param=new HashMap<String,String>();
                            param.put("correo",txt.getText().toString());
                            param.put("id",regid);
                            return param;
                        }
                    };
                    Aktool.post_json_string(actual,"http://matiasmedina.cl/socialchat/index.php/appregistrar",Aktool.delegado);
                }
                public void onFailure(String ex){
                    super.onFailure(ex);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Aktool.fullScreen(this);
        setContentView(R.layout.activity_main);
        //***************
        this.actual=this;
        this.txt=(EditText)findViewById(R.id.txt);
        this.btn=(Button)findViewById(R.id.btn);
        //***************
        this.btn.setOnClickListener(eventBtn);
    }
}
