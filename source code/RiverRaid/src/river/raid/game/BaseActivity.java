package river.raid.game;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

public class BaseActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;


	public Camera mCamera;
	
	public Music menuMusic;
	private static int SPR_COLUMN = 8;
	private static int SPR_ROWS = 2;

	private static int SPR_COLUMN_PLANES = 3;
	private static int SPR_ROWS_PLANES = 4;

	private static int SPR_COLUMN_ADDITIONS = 4;
	private static int SPR_ROWS_ADDITIONS = 2;

	private static int SPR_COLUMN_PACKAGE = 4;
	private static int SPR_ROWS_PACKAGE = 1;

	private static int SPR_COLUMN_BUTTONS = 4;
	private static int SPR_ROWS_BUTTONS = 2;
	// A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;

	public static BaseActivity getSharedInstance() {
		return instance;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions engOpt = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engOpt.getAudioOptions().setNeedsMusic(true);
		engOpt.getAudioOptions().setNeedsSound(true);
		return engOpt;
	}

	@Override
	protected void onCreateResources() {
		
		FontRes.getInstance().loadRes(this);
		TailedTexRes.getInstance().loadRes(this);
		TexRes.getInstance().loadRes(this);

		
		try {
			this.menuMusic = MusicFactory.createMusicFromAsset(this.getMusicManager(), this, "music/menu.mp3");
			this.menuMusic.setLooping(true);
		} catch (final IOException e) {
			Log.d("Errorsssssssssssssssssssssssssssssssssssssss", e + "");
		}

	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		return mCurrentScene;
	}

	// to change the current main scene
	public void setCurrentScene(Scene scene) {
		mCurrentScene = null;
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);

	}

	@Override
	public void onBackPressed() {
		Log.v("Jimvaders", "BaseActivity BackPressed " + mCurrentScene.toString());
		if (mCurrentScene instanceof GameScene)
			// ((GameScene) mCurrentScene).detach();
			if (this.mEngine.isRunning()) {
				// mCurrentScene.setChildScene(new PauseGameScene(mCamera));
				((GameScene) mCurrentScene).setPopup(mEngine);
				// this.mEngine.stop();

			} else {
				mCurrentScene.clearChildScene();
				this.mEngine.start();
			}
		else if (mCurrentScene instanceof MainMenuScene) {
			((MainMenuScene) mCurrentScene).setPopup();
			Log.d("dddddddddddddddddddddddddd", "plok");
		}

		// mCurrentScene = null;
		// SensorListener.instance = null;
		// super.onBackPressed();
	}
	@Override
	public void onPause() {
		if (mEngine.isRunning()) {
			this.mEngine.stop();
		}
		super.onPause();
	}
	// public boolean onTouchEvent(MotionEvent event) {
	// int myEventAction = event.getAction();
	//
	// float X = event.getX();
	// float Y = event.getY();
	//
	// // switch (myEventAction) {
	// // case MotionEvent.ACTION_DOWN:
	// //mEngine.start();
	// Log.d("ddd", "dddddddddddddddddddddddddddddddf");
	// // break;
	// //}
	// return true;
	// }
}