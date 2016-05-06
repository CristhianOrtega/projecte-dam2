package com.proyecto.dam2.librosvidal.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.proyecto.dam2.librosvidal.R;

/**
 * Created by david on 3/5/16.
 */
public class DatosNavigation extends AppCompatActivity {

    public static void cargaPreferenciasUser (SharedPreferences prefs, NavigationView navigationView, View headerView,Context context){

        System.out.println(prefs.getBoolean("login", false));
        if(prefs.getBoolean("login", false)) {
            System.out.println("Entra en login");
            navigationView.inflateMenu(R.menu.activity_all_drawer_loged);
            TextView nombreHeader = (TextView ) headerView.findViewById(R.id.nomHeader);
            TextView correoHeader = (TextView) headerView.findViewById(R.id.correoHeader);
            nombreHeader.setText(prefs.getString("NOM","Alumno"));
            correoHeader.setText(prefs.getString("EMAIL", "alumne@vidalibarraquer.net"));


            //CARGAR IMAGEN!!! ////

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fotoperfildefect);

            ImageView fotoPerfil = (ImageView) headerView.findViewById(R.id.imagePerfil);
            fotoPerfil.setImageResource(R.drawable.fotoperfildefect);

            if(!prefs.getString("STRINGIMAGE", "null").equals("null")){
                bitmap = Image.decodeString(prefs.getString("STRINGIMAGE", "null"));
                fotoPerfil.setImageBitmap(bitmap);
            }


            System.out.println("El bitmap es: " + bitmap);

            //ImageView fotoPerfil = (ImageView) headerView.findViewById(R.id.imagePerfil);
            //fotoPerfil.setImageBitmap(bitmap);

            Bitmap recortado = Image.cropBitmap(bitmap, 250, 250);
            Bitmap circleBitmap = Bitmap.createBitmap(recortado.getWidth(), recortado.getHeight(), Bitmap.Config.ARGB_8888);

            BitmapShader shader = new BitmapShader(recortado,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setShader(shader);

            Canvas c = new Canvas(circleBitmap);
            c.drawCircle(recortado.getWidth() / 2, recortado.getHeight() / 2, recortado.getWidth() / 2, paint);



            fotoPerfil.setImageBitmap(circleBitmap);

        } else {
            navigationView.inflateMenu(R.menu.activity_all_drawer);
        }
    }

}
