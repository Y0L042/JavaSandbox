import java.util.function.Function;
import java.util.function.Consumer;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.rCore;

public class EasyRaylibJ {
    static EasyRaylibJ app = null;

    public static void main(String[] args) {
        app = new EasyRaylibJ();
        app.run();
    }



    final int SCREEN_WIDTH = 800;
    final int SCREEN_HEIGHT = 450;
    final int TARGET_FPS = 60;
    final String TITLE = "Raylib-J";
    Color BACKGROUND_COLOR = Color.DARKGRAY;

    Raylib rlj;

    private Runnable ready = null;
    private Consumer<Float> update = null;
    private Consumer<Float> draw = null;

    public static void setReady(Runnable ready) {
        getSingleton().ready = ready;
    }

    public static void setUpdate(Consumer<Float> update) {
        getSingleton().update = update;
    }

    public static void setDraw(Consumer<Float> draw) {
        getSingleton().draw = draw;
    }

    public static void run() {
        getSingleton().execute();
    }


    private void execute() {
        rlj = new Raylib();
        rlj.core.InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, TITLE);
        rlj.core.SetTargetFPS(TARGET_FPS);

        if (ready != null) {
            ready.run();
        }

        while(!rlj.core.WindowShouldClose()) {
            float delta = rCore.GetFrameTime();
            rlj.core.BeginDrawing();
            rlj.core.ClearBackground(BACKGROUND_COLOR);

            if (update != null) {
                update.accept(delta);
            }
            if (draw != null) {
                draw.accept(delta);
            }

            rlj.core.EndDrawing();
        }
    }

    public static EasyRaylibJ getSingleton() {
        if (app == null) {
            app = new EasyRaylibJ();
        }

        return app;
    }
}
