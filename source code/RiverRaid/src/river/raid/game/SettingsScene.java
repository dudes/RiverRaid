package river.raid.game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import pl2.lines.shit.happnes.R;


public class SettingsScene extends MenuScene implements IOnSceneTouchListener {

	boolean done;
	boolean shown = false;
	BaseActivity activity;
	private Sprite window, soundon, soundoff;
	private float height;
	public SettingsScene(Camera mCamera) {
		super(mCamera);
		activity = BaseActivity.getSharedInstance();
		setBackgroundEnabled(false);

		// ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		// background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0,
		// 0, activity.regPopup, activity.getVertexBufferObjectManager())));
		// // setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		//
		// setBackground(background);
		// GameScene scene = (GameScene) activity.mCurrentScene;
		
		window = new Sprite(0, 0, TexRes.getInstance().regPopup, activity.getVertexBufferObjectManager());
		height = mCamera.getHeight();
		final int x = (int) (mCamera.getWidth() / 2 - window.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - window.getHeight() / 2);
	
		soundon = new Sprite(0, 0, TexRes.getInstance().regSoundOn,activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				//soundon.setVisible(false);
				soundon.setZIndex(0);
				soundoff.setZIndex(1);
				Log.d("sound","0n");
				return true;
			}
		};
		soundoff = new Sprite(0, 0, TexRes.getInstance().regSoundOff,activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				soundon.setVisible(true);
				soundoff.setVisible(false);
				Log.d("sound","0ff");
				return true;
			}
		};
		
		soundoff.setVisible(false);
		Sprite nobutton = new Sprite(0, 0, TexRes.getInstance().regNoButton, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				closePopup(window,height);
				return true;
			}
		};
		Text result = new Text(0, 0, FontRes.getInstance().popupFont, activity.getString(R.string.soundon), BaseActivity.getSharedInstance().getVertexBufferObjectManager());


		done = false;
		result.setPosition(window.getWidth() / 2 - result.getWidth() / 2, window.getHeight() / 2 - result.getHeight());
		soundon.setPosition(window.getWidth() / 2 - soundon.getWidth() / 2, window.getHeight() - result.getHeight()*4);
		soundoff.setPosition(window.getWidth() / 2 - soundoff.getWidth() / 2, window.getHeight() - result.getHeight()*4);
		nobutton.setPosition(window.getWidth() - nobutton.getWidth(), -nobutton.getHeight() / 3);
		
		window.attachChild(result);
		window.attachChild(soundon);
		window.attachChild(soundoff);
		window.attachChild(nobutton);
		window.setPosition(x, mCamera.getHeight() + window.getHeight());
		MoveYModifier mod = new MoveYModifier(0.5f, window.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
				shown = true;
			}
		};
		attachChild(window);
		window.registerEntityModifier(mod);
	//	setOnSceneTouchListener(this);
		registerTouchArea(soundon);
		registerTouchArea(soundoff);
		registerTouchArea(nobutton);
		setTouchAreaBindingOnActionDownEnabled(true);

	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!done)
			return true;
		// ((GameScene) activity.mCurrentScene).resetValues();
		return false;
	}

	private void closePopup(Sprite popup, float y){
	//	closeMenuScene();
		MoveYModifier mod = new MoveYModifier(0.5f, popup.getY(),y+popup.getY()) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				shown = false;
				closeMenuScene();
			}
		};
		popup.registerEntityModifier(mod);
	}
}
