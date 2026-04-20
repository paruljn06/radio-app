package com.example.radio;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgElement, rgSize;
    private MaterialCardView petCard;
    private ImageView petImage;
    private TextView petStatus;
    private MaterialButton btnSummon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        rgElement = findViewById(R.id.rgElement);
        rgSize = findViewById(R.id.rgSize);
        petCard = findViewById(R.id.petCard);
        petImage = findViewById(R.id.petImage);
        petStatus = findViewById(R.id.petStatus);
        btnSummon = findViewById(R.id.btnSummon);

        btnSummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                summonPet();
            }
        });
    }

    private void summonPet() {
        int elementId = rgElement.getCheckedRadioButtonId();
        int sizeId = rgSize.getCheckedRadioButtonId();

        if (elementId == -1) {
            Toast.makeText(this, "Choose an element first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine Color based on Element
        int color;
        String elementName;
        if (elementId == R.id.rbFire) {
            color = ContextCompat.getColor(this, R.color.fire_red);
            elementName = "Fire";
        } else if (elementId == R.id.rbWater) {
            color = ContextCompat.getColor(this, R.color.water_blue);
            elementName = "Water";
        } else {
            color = ContextCompat.getColor(this, R.color.grass_green);
            elementName = "Grass";
        }

        // Determine Scale based on Size
        float scale;
        String sizeName;
        if (sizeId == R.id.rbTiny) {
            scale = 0.5f;
            sizeName = "Tiny";
        } else if (sizeId == R.id.rbGiant) {
            scale = 1.5f;
            sizeName = "Giant";
        } else {
            scale = 1.0f;
            sizeName = "Normal";
        }

        // Update Card Background and Status
        petCard.setCardBackgroundColor(color);
        petStatus.setText("A " + sizeName + " " + elementName + " Spirit has appeared!");

        // Animate Image
        petImage.animate()
                .scaleX(scale)
                .scaleY(scale)
                .rotationBy(360)
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
        
        Toast.makeText(this, "Summoning successful!", Toast.LENGTH_SHORT).show();
    }
