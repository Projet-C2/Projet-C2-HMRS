package com.example.mariobousamra.projet_c2_hmrs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddProduct extends AppCompatActivity implements View.OnClickListener{

    Button button;
    TextView textView;
    TextView x;
    TextView y;
    String coorx;
    String coory;

    ImageView imageView;

    Button button_image;

    Button button_save;
    Button button_cancel;

    EditText txt_name;
    EditText txt_price;
    EditText txt_description;
    Spinner spinner;
    Spinner spinner2;

    String product_name;
    Float product_price;
    String product_description;
    String product_category;
    String product_status;

    String realPath="";
    Uri imagepath;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_add_product);

       //location button
        button = (Button) findViewById(R.id.buttonlocation);
        textView = (TextView) findViewById(R.id.textViewlocation);
        x = (TextView) findViewById(R.id.xnumber);
        y = (TextView) findViewById(R.id.ynumber);

        ActivityCompat.requestPermissions(AddProduct.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPStracker g = new GPStracker(getApplicationContext());
                Location l =g.getLocation();
                Globals.Coordinates = l;
                if(l != null){
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    LatLng myCoordinates = new LatLng(lat,lon);
                    //Toast.makeText(getApplicationContext(),"Latitude:"+lat+" \n Longitude: "+lon, Toast.LENGTH_LONG).show();
                    String cityName = getCityName(myCoordinates);
                    textView.setText(cityName);
                    x.setText(Double.toString(lat));
                    y.setText(Double.toString(lon));

                }

            }
        });

        //find by id.
        button_image = findViewById(R.id.button_image);
        button_save = findViewById(R.id.button_Save);
        button_cancel = findViewById(R.id.button_cancel);

        txt_name = findViewById(R.id.txt_name);
        txt_price = findViewById(R.id.txt_price);
        txt_description = findViewById(R.id.txt_description);

        firebaseAuth = FirebaseAuth.getInstance();

        // add click listener to buttons
        button_image.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

        //allocate array to spinner(drop down list) - [categories - product status]

        spinner = findViewById(R.id.spinner_categories);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner2 = findViewById(R.id.spinner_status);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.status_product, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

    }

    private String getCityName(LatLng myCoordinates) {
        String myCity ="";
        Geocoder geocoder =new Geocoder(AddProduct.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getLocality();

        }catch (IOException e){
            e.printStackTrace();
        }
        return myCity;
    }

    @Override
    public void onClick(View view) {

        //button_image
        if (view.equals(button_image)) {

            // ask for permission to read image.
            askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 4);

            // 1. on Upload click call ACTION_GET_CONTENT intent

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            // 2. pick image only
            intent.setType("image/*");
            // 3. start activity
            startActivityForResult(intent, 0);
            // define onActivityResult to do something with picked image
        }

        //button save
        if (view.equals(button_save)) {

            product_name = txt_name.getText().toString();

            if (txt_price.getText().toString().isEmpty() ){
                product_price = Float.parseFloat("0.00");

            }else{
                product_price = Float.parseFloat(txt_price.getText().toString());

            }

            product_description = txt_description.getText().toString();
            product_category = spinner.getSelectedItem().toString();
            product_status = spinner2.getSelectedItem().toString();
            coorx = x.getText().toString();
            coory = y.getText().toString();

            if(!(product_name.isEmpty() || product_price.isNaN()|| coorx.isEmpty() || coory.isEmpty() || product_description.isEmpty() || product_category.isEmpty() || product_status.isEmpty() || (realPath == "") ) ){

               //get uid from auth.
               FirebaseDatabase firebasedatabse = FirebaseDatabase.getInstance();
               //DatabaseReference myref = firebasedatabse.getReference(firebaseAuth.getUid());

               DatabaseReference myref = firebasedatabse.getReference(firebaseAuth.getUid()).child(product_name) ;

               //new object of the class Product to the object to firebase database.
               Product product = new Product (product_name,product_category,product_price,product_status,product_description,coorx,coory);

               //send data to firebase database.
               myref.setValue(product);

               //save image.
               FirebaseStorage firebasestorage = FirebaseStorage.getInstance();
               StorageReference storagereference =   firebasestorage.getReference();

               StorageReference myref1 = storagereference.child(firebaseAuth.getUid()).child(product_name);
               UploadTask uploadtask = myref1.putFile(imagepath);

               uploadtask.addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(AddProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
               }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       Toast.makeText(AddProduct.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(AddProduct.this, VendorPage.class);
                       startActivity(intent);
                   }
               });

           }else{
               Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
           }

        }



        if (view.equals(button_cancel)) {
            Intent intent = new Intent(AddProduct.this, VendorPage.class);
            startActivity(intent);
        }

        }


    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {

                //Read External Storage
                case 4:
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, 11);
                    break;

            }

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if(resCode == Activity.RESULT_OK && data != null){

            imagepath = data.getData();

            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(),realPath);
        }
    }

    private void setTextViews(int sdk, String uriPath,String realPath){
        //image
        imageView = findViewById(R.id.imageView);
        Uri uriFromPath = Uri.fromFile(new File(realPath));

        // you have two ways to display selected image
        // ( 1 ) imageView.setImageURI(uriFromPath);
        //( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);

        //Log.d("HMKCODE", "Build.VERSION.SDK_INT:"+sdk);
        //Log.d("HMKCODE", "URI Path:"+uriPath);
        //Log.d("HMKCODE", "Real Path: "+realPath);
    }



    //Hide keyboard when you click anywhere on the screen
    //Note: I added this line 'android:onClick="myMethod"' in the respective .xml file.
    public void myMethod(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}