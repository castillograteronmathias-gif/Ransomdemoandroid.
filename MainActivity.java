package com.example.ransomdemo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.os.Handler;
import android.os.Looper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextView glitch, clock, status;
    private int frame = 0;
    private boolean running = false;
    private final String[] glitches = {"R4NS0M D3M0", "R@NSOM D3MO", "R4N$0M D3M0", "RANSOM DEMO"};

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        showHome();
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value); t.setTextSize(size); t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        if (bold) t.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        t.setPadding(16, 10, 16, 10);
        return t;
    }

    private Button button(String label) {
        Button b = new Button(this); b.setText(label); b.setAllCaps(false); return b;
    }

    private void showHome() {
        running = false; handler.removeCallbacksAndMessages(null);
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER); root.setPadding(22, 28, 22, 28); root.setBackgroundColor(Color.rgb(14, 8, 12));
        TextView title = text("RANSOM DEMO", 30, Color.rgb(255, 55, 70), true);
        TextView label = text("SIMULACIÓN VISUAL SEGURA", 16, Color.WHITE, true);
        TextView info = text("Esta aplicación solo muestra efectos ficticios. No cifra, borra ni modifica archivos y no bloquea el teléfono.", 16, Color.LTGRAY, false);
        Button start = button("Iniciar simulación");
        Button exit = button("Salir");
        root.addView(title); root.addView(label); root.addView(info);
        root.addView(start); root.addView(exit);
        start.setOnClickListener(v -> showSimulation());
        exit.setOnClickListener(v -> finish());
        setContentView(root);
    }

    private void showSimulation() {
        running = true;
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER); root.setPadding(18, 18, 18, 18); root.setBackgroundColor(Color.BLACK);
        TextView demo = text("DEMO · NO ES UN ATAQUE REAL", 14, Color.GREEN, true);
        glitch = text(glitches[0], 30, Color.RED, true);
        TextView warning = text("¡AVISO FICTICIO!\n\nTus archivos están a salvo.\nEsto es una demostración visual para aprender a reconocer pantallas engañosas.", 19, Color.WHITE, true);
        clock = text("00:30", 35, Color.rgb(255, 70, 70), true);
        status = text("Efectos visuales: activos", 14, Color.LTGRAY, false);
        Button stop = button("DETENER Y VOLVER");
        root.addView(demo); root.addView(glitch); root.addView(warning); root.addView(clock); root.addView(status); root.addView(stop);
        stop.setOnClickListener(v -> showHome());
        setContentView(root);
        animate(30);
    }

    private void animate(final int seconds) {
        if (!running) return;
        if (seconds <= 0) {
            status.setText("Simulación terminada. Ningún archivo fue tocado.");
            clock.setText("FIN"); return;
        }
        glitch.setText(glitches[(frame++) % glitches.length]);
        glitch.setTextColor(frame % 2 == 0 ? Color.RED : Color.MAGENTA);
        clock.setText(String.format(Locale.US, "00:%02d", seconds));
        status.setText(frame % 2 == 0 ? "EFECTO GLITCH · DEMOSTRACIÓN" : "MODO SEGURO · SIN ACCESO A ARCHIVOS");
        handler.postDelayed(() -> animate(seconds - 1), 1000);
    }

    @Override public void onBackPressed() {
        showHome();
    }
    @Override protected void onDestroy() { running = false; handler.removeCallbacksAndMessages(null); super.onDestroy(); }
}
