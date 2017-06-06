# android动态修改app桌面icon

原理:

在Manifest文件中,使用<activity-alias>标签为我们的入口Activity准备多个，拥有<Activity-alias>标签的Activity是为了指向入口Activity,每个<activity-alias>标签都可以单独设置一个icon,在程序中我们动态设置<activity-alias>,然后kill掉launcher,等launcher重启后,icon就替换了
注意:kill掉launcher需要权限

<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>

代码: Manifest.xml
<?xml version="1.0" encoding="utf-8"?>

<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.jh.changeicon">
    
    <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        
//首先我们创建一个Activity,作为默认入口,并带有默认的图片

        <activity android:name=".MainActivity">
        
            <intent-filter>
            
                <action android:name="android.intent.action.MAIN" />
                
                <category android:name="android.intent.category.LAUNCHER" />
                
            </intent-filter>
            
        </activity>
        
//接着再创建一个新的activity-alias,指向默认的activty并带有新的icon图标 icon1

        <activity-alias
        
            android:name=".MainActivity1"
            
            android:targetActivity=".MainActivity"
            
            android:enabled="false"	//默认情况下我们应该先把这个activity-alias禁用掉,否则,在应用列表中会出现2个图标
            
            android:icon="@drawable/icon1"
            
            >
            <intent-filter>
            
                <action android:name="android.intent.action.MAIN" />
                
                <category android:name="android.intent.category.LAUNCHER" />
                
            </intent-filter>
            
        </activity-alias>
        
//再创建一个新的activity-alias,指向默认的Activity并带有新的icon图标 icon2……………………

        <activity-alias
        
            android:name=".MainActivity2"
            
            android:targetActivity=".MainActivity"
            
            android:enabled="false"   //默认情况下我们应该先把这个activity-alias禁用掉,否则,在应用列表中会出现2个图标
            
            android:icon="@drawable/icon2"
            
            >
            
            <intent-filter>
            
                <action android:name="android.intent.action.MAIN" />
                
                <category android:name="android.intent.category.LAUNCHER" />
                
            </intent-filter>
            
        </activity-alias>
        
    </application>
    
</manifest>

code:

PackageManager是一个大统领,可以管理所有的系统组件,当然,如果root了,

你还可以管理其他App的所有组件,一些系统优化工具就是通过这个方式来禁用一些后台Service的


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
    
//首先,我们先把所有的组件名称保存在数组中,然后遍历这个数组,

//如果,值相等的组件启用,其他的停用

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
    
// 根据PackageManager.COMPONENT_ENABLED_STATE_ENABLED

//和  PackageManager.COMPONENT_ ENABLED _STATE_DISABLED	

//还有对应的ComponentName ,就可以控制一个组件是否启用

    private void enabledComponent(PackageManager mp, ComponentName name) {
    
        mp.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP);
    }
    
    private void disableComponent(PackageManager mp,ComponentName name) {
    
        mp.setComponentEnabledSetting(name, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.DONT_KILL_APP);
    }
}
