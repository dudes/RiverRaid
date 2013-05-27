package river.raid.game;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import pl2.lines.shit.happnes.R;

public class ResultScene extends CameraScene implements IOnSceneTouchListener {

	boolean done;
	BaseActivity activity;

	public ResultScene(Camera mCamera) {
		super(mCamera);
		activity = BaseActivity.getSharedInstance();
		setBackgroundEnabled(false);
		//GameScene scene = (GameScene) activity.mCurrentScene;

		Text result = new Text(0, 0, FontRes.getInstance().krashFont, activity.getString(R.string.gameover), BaseActivity.getSharedInstance().getVertexBufferObjectManager());

		final int x = (int) (mCamera.getWidth() / 2 - result.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - result.getHeight() / 2);

		done = false;
		result.setPosition(x, mCamera.getHeight() + result.getHeight());
		MoveYModifier mod = new MoveYModifier(1, result.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
			}
		};
		attachChild(result);
		result.registerEntityModifier(mod);
		setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!done)
			return true;
		((GameScene) activity.mCurrentScene).resetValues();
		return false;
	}

}
