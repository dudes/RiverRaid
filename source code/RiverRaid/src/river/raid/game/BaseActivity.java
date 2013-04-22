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

	public Font mFont, popupFont;
	public Font jellyFont;
	public Font krashFont;
	public Font splashFont;
	public Camera mCamera;
	private BitmapTextureAtlas texBanana, texPlanes, texAdditions, texPackage, tex2lines, texPlayButton, texButtons, texSmoke, texExit, texYesButton, texNoButton, texSettings, texScores,
			texPauseButton, texMenuBackground, texMenuText, texPopup, texGamePopup, texMenuButton, texPlayAgainButton, texSubmitButton, texSoundOn, texSoundOff;
	public TiledTextureRegion regBanana, regPlanes, regAdditions, regPackage, regButtons;
	public TextureRegion reg2lines, regPlayButton, regSmoke, regExit, regMenuBackground, regSettings, regScores, regMenuText, regPopup, regYesButton, regNoButton, regPauseButton, regGamePopup,
			regMenuButton, regPlayAgainButton, regSubmitButton, regSoundOn, regSoundOff;
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
		mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		mFont.load();

		popupFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 22, Color.WHITE);
		popupFont.load();

		texBanana = new BitmapTextureAtlas(this.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regBanana = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texBanana, this.getAssets(), "gfx/bob.png", 0, 0, SPR_COLUMN, SPR_ROWS);
		texBanana.load();

		texPlanes = new BitmapTextureAtlas(this.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regPlanes = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texPlanes, this.getAssets(), "gfx/hero.png", 0, 0, SPR_COLUMN_PLANES, SPR_ROWS_PLANES);
		texPlanes.load();

		texAdditions = new BitmapTextureAtlas(this.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regAdditions = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texAdditions, this.getAssets(), "gfx/additions.png", 0, 0, SPR_COLUMN_ADDITIONS, SPR_ROWS_ADDITIONS);
		texAdditions.load();

		texPackage = new BitmapTextureAtlas(this.getTextureManager(), 512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regPackage = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texPackage, this.getAssets(), "gfx/package.png", 0, 0, SPR_COLUMN_PACKAGE, SPR_ROWS_PACKAGE);
		texPackage.load();

		tex2lines = new BitmapTextureAtlas(this.getTextureManager(), 64, 128, TextureOptions.BILINEAR);
		reg2lines = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tex2lines, this.getAssets(), "gfx/2lines.png", 0, 0);
		tex2lines.load();

		texPlayButton = new BitmapTextureAtlas(this.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		regPlayButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPlayButton, this.getAssets(), "gfx/playbutton.png", 0, 0);
		texPlayButton.load();

		texSmoke = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		regSmoke = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSmoke, this.getAssets(), "gfx/particle_fire.png", 0, 0);
		texSmoke.load();

		texExit = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regExit = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texExit, this.getAssets(), "gfx/exitbutton.png", 0, 0);
		texExit.load();

		texSettings = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSettings = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSettings, this.getAssets(), "gfx/settingsbutton.png", 0, 0);
		texSettings.load();

		texScores = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regScores = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texScores, this.getAssets(), "gfx/scoresbutton.png", 0, 0);
		texScores.load();

		texYesButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regYesButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texYesButton, this.getAssets(), "gfx/yesbutton.png", 0, 0);
		texYesButton.load();

		texNoButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regNoButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texNoButton, this.getAssets(), "gfx/nobutton.png", 0, 0);
		texNoButton.load();

		texPauseButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regPauseButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPauseButton, this.getAssets(), "gfx/pausebutton.png", 0, 0);
		texPauseButton.load();

		texMenuBackground = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		regMenuBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuBackground, this.getAssets(), "gfx/menubackground.png", 0, 0);
		texMenuBackground.load();

		texMenuText = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		regMenuText = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuText, this.getAssets(), "gfx/maintext.png", 0, 0);
		texMenuText.load();

		texMenuButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regMenuButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texMenuButton, this.getAssets(), "gfx/menubutton.png", 0, 0);
		texMenuButton.load();

		texPlayAgainButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regPlayAgainButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPlayAgainButton, this.getAssets(), "gfx/againbutton.png", 0, 0);
		texPlayAgainButton.load();

		texSubmitButton = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSubmitButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSubmitButton, this.getAssets(), "gfx/submitbutton.png", 0, 0);
		texSubmitButton.load();
		
		texSoundOff = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSoundOff = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSoundOff, this.getAssets(), "gfx/soundoff.png", 0, 0);
		texSoundOff.load();
		
		texSoundOn = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		regSoundOn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texSoundOn, this.getAssets(), "gfx/soundon.png", 0, 0);
		texSoundOn.load();

		final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);

		final ITexture fontTexture2 = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);

		jellyFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "fonts/JELLYBELLY.TTF", 48, true, Color.parseColor("#ffcc33"));
		jellyFont.load();

		krashFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture2, this.getAssets(), "fonts/Kraash.ttf", 32, true, Color.BLACK);
		krashFont.load();

		splashFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture2, this.getAssets(), "fonts/Artbrush.ttf", 48, true, Color.BLACK);
		splashFont.load();

		texPopup = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		regPopup = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texPopup, this.getAssets(), "gfx/popup.png", 0, 0);
		texPopup.load();

		texGamePopup = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		regGamePopup = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texGamePopup, this.getAssets(), "gfx/gamepopup.png", 0, 0);
		texGamePopup.load();
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