package com.jh.changeicon;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button icon1;
    Button icon2;
    Button defaule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        icon1=(Button)findViewById(R.id.setIcon1);
        icon2=(Button)findViewById(R.id.setIcon2);
        defaule=(Button)findViewById(R.id.defaule_Icon);

        icon1.setOnClickListener(this);
        icon2.setOnClickListener(this);
        defaule.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setIcon1:
                setIconwith("com.jh.changeicon.MainActivity1");
                break;
            case R.id.setIcon2:
                setIconwith("com.jh.changeicon.MainActivity2");
                break;
            case R.id.defaule_Icon:
                setIconwith("com.jh.changeicon.MainActivity");
                break;
        }
    }



    String[] alias_list={
            "com.jh.changeicon.MainActivity",
            "com.jh.changeicon.MainActivity1",
            "com.jh.changeicon.MainActivity2",
    };

    private void setIconwith(String alias){
        PackageManager mp=getApplicationContext().getPackageManager();
        for (String s : alias_list){
            ComponentName cn=new ComponentName(getBaseContext(),s);
            if(alias.equals(s)){
                enabledComponent(mp,cn);
            }else {
                disableComponent(mp,cn);
            }
        }

    }

    private void enabledComponent(PackageManager mp, ComponentName name) {
        mp.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
    private void disableComponent(PackageManager mp,ComponentName name) {
        mp.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }



}
