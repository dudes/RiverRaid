package river.raid.game;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import pl2.lines.shit.happnes.R;

/**
 * @author Nicolas Gramlich
 * @since 16:36:51 - 03.08.2010
 */
public class PauseGameScene extends MenuScene implements IOnSceneTouchListener {

	boolean done;
	boolean shown = false;
	BaseActivity activity;
	private Sprite window;
	private float height;
	private Engine mEngine;
	public PauseGameScene(Camera mCamera, Engine engine) {
		super(mCamera);
		mEngine = engine;
		activity = BaseActivity.getSharedInstance();
		setBackgroundEnabled(false);

		// ParallaxBackground background = new ParallaxBackground(0, 0, 0);
		// background.attachParallaxEntity(new ParallaxEntity(0, new Sprite(0,
		// 0, activity.regPopup, activity.getVertexBufferObjectManager())));
		// // setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		//
		// setBackground(background);
		// GameScene scene = (GameScene) activity.mCurrentScene;
		
		window = new Sprite(0, 0, activity.regPopup, activity.getVertexBufferObjectManager());
		height = mCamera.getHeight();
		final int x = (int) (mCamera.getWidth() / 2 - window.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - window.getHeight() / 2);
		Sprite yesbutton = new Sprite(0, 0, activity.regYesButton, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				Log.d("fffff", "ddddddddddddddddddddddddd");
				

	            switch(pSceneTouchEvent.getAction()) {
	            case TouchEvent.ACTION_DOWN:
	 
	            	activity.finish();	 
	                break;
	            }
				return true;
			}
		};
//		 registerTouchArea(yesbutton);
//		    getLastChild().attachChild(yesbutton);
		Sprite nobutton = new Sprite(0, 0, activity.regNoButton, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				Log.d("ddddd", "ddddddddddddddddddddddddd");
				closePopup(window,height);
				return true;
			}
		};
		Text result = new Text(0, 0, activity.popupFont, activity.getString(R.string.exit), BaseActivity.getSharedInstance().getVertexBufferObjectManager());


		done = false;
		yesbutton.setPosition(window.getWidth() / 2 - yesbutton.getWidth() / 2, window.getHeight() - yesbutton.getHeight() / 2);
		nobutton.setPosition(window.getWidth() - nobutton.getWidth(), -nobutton.getHeight() / 3);
		result.setPosition(window.getWidth() / 2 - result.getWidth() / 2, window.getHeight() / 2 - result.getHeight() / 2);
		window.attachChild(result);
		window.attachChild(yesbutton);
		window.attachChild(nobutton);
		window.setPosition(x, mCamera.getHeight() + window.getHeight());
		MoveYModifier mod = new MoveYModifier(0.01f, window.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
				shown = true;
				activity.getEngine().stop();
				Log.d("stoooooooop", "sssssssssssssssss");
				
			}
		};
		attachChild(window);
		window.registerEntityModifier(mod);
	//	setOnSceneTouchListener(this);
		registerTouchArea(yesbutton);
		registerTouchArea(nobutton);
		setTouchAreaBindingOnActionDownEnabled(true);

	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
//		if (!done)
//			return true;
		Log.d("dddddddddda","dasdasdafqwrqwff");
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
