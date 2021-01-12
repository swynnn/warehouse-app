package com.example.warehouseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.warehouseapp.provider.ItemViewModel;
import com.example.warehouseapp.provider.WarehouseItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;





public class MainActivity extends AppCompatActivity {
    private EditText itemName, quantity, cost, description;

    private String itemStr, qtStr, costStr, descStr;
    private ToggleButton frozen;
    public static final String TAG = "LIFE_CYCLE_TRACE";
    DrawerLayout drawer;

    private ItemViewModel mItemViewModel;
    MyRecyclerViewAdapter adapter;

    //ArrayList<WarehouseItem> itemData = new ArrayList<>();  //store data

    View myConstraintLayout;
    int x_down;
    int y_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        itemName = findViewById(R.id.itemText);
        quantity = findViewById(R.id.quantityText);
        cost = findViewById(R.id.costText);
        description = findViewById(R.id.descriptionText);
        frozen = findViewById(R.id.frozenButton);
        Log.i(TAG,"onCreate");

        IntentFilter intentFilter = new IntentFilter("mySMS"); //specifies the type of intents that the component would like to receive
        registerReceiver(myReceiver, intentFilter);

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();                 //Synchronize the state of the drawer indicator/affordance with the linked DrawerLayout.

        //menu item in drawer
        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myItem = itemName.getText().toString().trim();
                if (!("".equals(myItem))) {
                    Toast.makeText(getApplicationContext(), "New item (" + myItem + ") has been added", Toast.LENGTH_LONG).show();
                    Snackbar.make(v, "Item Saved", Snackbar.LENGTH_LONG).show();
                    addData();
                }
            }
        });

        adapter = new MyRecyclerViewAdapter();
        mItemViewModel=new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {   //ask the model class to set the data to newData, list with all the data and pass to adapter
            adapter.setItemData(newData); //newData a list of item
            adapter.notifyDataSetChanged();
        });


        myConstraintLayout = findViewById(R.id.constraint_id);

        myConstraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int action = motionEvent.getActionMasked(); //current action of mouse

                switch(action){
                    case MotionEvent.ACTION_DOWN: //0
                        x_down = (int)motionEvent.getX();
                        y_down = (int)motionEvent.getY();
//                        Toast.makeText(getApplicationContext(),"x:" + motionEvent.getRawX() + "y: " + (int)motionEvent.getRawY(),Toast.LENGTH_SHORT).show();
                        return true;

                    case MotionEvent.ACTION_UP: //1
                        if (Math.abs(y_down - motionEvent.getY())<40)
                        {
                            if (x_down - motionEvent.getX()<0)              //swipe left to right
                            {
                                addData();
                            }
                            else                                            //swipe right to left
                            {
                                clearItemDetails();
                            }
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });



    }

    public void deleteAll(){
        mItemViewModel.deleteAll();
    }

    public void deleteFunc(View v){
        deleteAll();
    }

    public void addData(){
        String named = itemName.getText().toString();
        String qtyd = quantity.getText().toString();
        String costd = cost.getText().toString();
        String descd = description.getText().toString();
        boolean frozed = frozen.isChecked();
        WarehouseItem item = new WarehouseItem(named, qtyd ,costd ,descd ,Boolean.toString(frozed));
        //itemData.add(item);
        if (!(named.isEmpty()))
        {
            mItemViewModel.insert(item);
            addItemToast();
        }
    }

    public void addItem(View v){addData();}

    public void addItemToast()
    {
        String myItem = itemName.getText().toString().trim();
        if (!("".equals(myItem))) {
            Toast myToastMsg = Toast.makeText(this, "New item (" + myItem + ") has been added.", Toast.LENGTH_SHORT);
            myToastMsg.show();
        }
    }

    public void clearFields(View v){
        clearItemDetails();
    }

    public void clearItemDetails()
    {
        itemName.getText().clear();
        quantity.getText().clear();
        cost.getText().clear();
        description.getText().clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
        SharedPreferences myData = getSharedPreferences("f1",0);

        itemStr = myData.getString("item", "");
        qtStr = myData.getString("quantity", "0");
        costStr = myData.getString("cost", "0.0");
        descStr = myData.getString("description", "");

        itemName.setText(itemStr);
        quantity.setText(qtStr);
        cost.setText(costStr);
        description.setText(descStr);
        boolean frozenButt = myData.getBoolean("frozenbutton", true);
        if (frozenButt == true){
            frozen.setChecked(true);
        }
        else
        {
            frozen.setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");

        itemStr = itemName.getText().toString();
        qtStr = quantity.getText().toString();
        costStr = cost.getText().toString();
        descStr = description.getText().toString();

        SharedPreferences myData = getSharedPreferences("f1",0);
        SharedPreferences.Editor myEditor = myData.edit(); //bind editor with data, create new editor for preference which can modify the data

        myEditor.putString("item", itemStr); //use editor to store data (dic, key/val pair)
        myEditor.putString("quantity", qtStr);
        myEditor.putString("cost", costStr);
        myEditor.putString("description", descStr);

        if (frozen.isChecked()){
            myEditor.putBoolean("frozenbutton", true);
        }
        else
        {
            myEditor.putBoolean("frozenbutton", false);
        }

        myEditor.commit(); //data is not saved into file if didnt commit

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        Log.i(TAG,"onRestoreInstanceState");
    }


    final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieve the message from the intent
            String msg = intent.getStringExtra("SMSKEY");

            // String Tokenizer is used to parse the incoming message
            // The protocol is to have the warehouse item details separate by a semicolon
            StringTokenizer sT = new StringTokenizer(msg, ";");
            String item = sT.nextToken();
            String iQuantity = sT.nextToken();
            String iCost = sT.nextToken();
            float dec = Float.parseFloat(iCost);
            iCost = Float.toString(dec);
            String iDesc = sT.nextToken();
            String iFrozen = sT.nextToken();

            //update UI with the SMS message
            itemName.setText(item.trim());
            quantity.setText(iQuantity);
            cost.setText(iCost);
            description.setText(iDesc.trim());
            frozen.setChecked(Boolean.parseBoolean(iFrozen));
        }
    };

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();
            if (id == R.id.add) {
                addData();
            } else if (id == R.id.delete){
                deleteAll();
            } else if (id == R.id.clear) {
                clearItemDetails();
            } else if (id == R.id.listItem){
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                //i.putExtra("KEY_LIST", itemData);
                startActivity(i);
            }

            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);    //creating options menu based on XML file to your toolbar
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.add_opt) {
            addData();

        } else if (id == R.id.clear_opt) {
            clearItemDetails();
        }
        return super.onOptionsItemSelected(item);
    }

}