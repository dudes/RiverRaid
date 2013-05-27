package river.raid.game;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;

public class BarScene extends HUD {

	boolean done;
	BaseActivity activity;
	Text result, tpoints;
	
	TiledSprite lives;
	 Sprite pauseButton;
	private int HP, points;
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
		result.setText(hP+"");
	}

	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
		tpoints.setText(points+"");
		tpoints.setPosition(activity.mCamera.getWidth()-pauseButton.getWidth()-20-tpoints.getWidth(), lives.getHeight()/4);
	}
	public BarScene() {
		super();
		activity = BaseActivity.getSharedInstance();
		
//		 GameScene scene = (GameScene) activity.mCurrentScene;
		
		lives = new TiledSprite(0, 0, TailedTexRes.getInstance().regAdditions, activity.getVertexBufferObjectManager());
		lives.setCurrentTileIndex(2);
		lives.setPosition(0, lives.getHeight()/4);
		
		result = new Text(0, 0, FontRes.getInstance().jellyFont, ""+getHP(),5, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		result.setPosition(lives.getWidth()+5, lives.getHeight()/4);
		
		pauseButton = new Sprite(0, 0, TexRes.getInstance().regPauseButton, activity.getVertexBufferObjectManager());
		pauseButton.setPosition(activity.mCamera.getWidth()-pauseButton.getWidth()-10, lives.getHeight()/4);
		
		tpoints = new Text(0, 0, FontRes.getInstance().jellyFont, ""+getPoints(), 5, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		tpoints.setPosition(activity.mCamera.getWidth()-pauseButton.getWidth()-10, lives.getHeight()/4);
		
		
		// result.registerEntityModifier(mod);
	}

}
