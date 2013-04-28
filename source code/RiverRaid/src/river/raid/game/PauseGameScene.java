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


/*
 Scena gry - wstrzymanie aplikacji
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

	
		
		window = new Sprite(0, 0, activity.regPopup, activity.getVertexBufferObjectManager());
		height = mCamera.getHeight();
		final int x = (int) (mCamera.getWidth() / 2 - window.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - window.getHeight() / 2);
		Sprite yesbutton = new Sprite(0, 0, activity.regYesButton, activity.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				

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
