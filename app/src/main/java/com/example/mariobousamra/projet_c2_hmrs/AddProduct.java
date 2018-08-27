package com.example.mariobousamra.projet_c2_hmrs;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;
import java.io.File;
import java.io.FileNotFoundException;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;


public class AddProduct extends AppCompatActivity implements View.OnClickListener{

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

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_add_product);

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
           product_price = Float.parseFloat(txt_price.getText().toString());
           product_description = txt_description.getText().toString();
           product_category = spinner.getSelectedItem().toString();
           product_status = spinner2.getSelectedItem().toString();

          //get uid from auth.
          FirebaseDatabase firebasedatabse = FirebaseDatabase.getInstance();
         //DatabaseReference myref = firebasedatabse.getReference(firebaseAuth.getUid());

         DatabaseReference myref = firebasedatabse.getReference("uid1" + product_name);

         //new object of the class Product to the object to firebase database.
         Product product = new Product (product_name,product_category,product_price,product_status,product_description);

         //send data to firebase database.
         myref.setValue(product);

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
            String realPath;
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

}