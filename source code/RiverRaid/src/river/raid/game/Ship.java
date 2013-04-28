package river.raid.game;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class Ship {
	public int hp;
	final int MAX_HEALTH = 1;

	
	public static Ship instance;
	Camera mCamera;
	boolean moveable;

	private BaseActivity activity;

	public BitmapTextureAtlas mBitmapTextureAtlas;
	public TextureRegion mTextureRegion;

	private Texture mRunnerTexture;


	public AnimatedSprite sprite;

	private static int SPR_COLUMN = 7;
	private static int SPR_ROWS = 2;
	private int STOP = 0;
	private int LEFT = 1;
	private int RIGHT = 2;
	private int movement = STOP;
	private int prevmovement = STOP;
	private float newXX = -1;

	// public Sprite sprite;
	public static Ship getSharedInstance() {
		if (instance == null)
			instance = new Ship();
		return instance;
	}

	private Ship() {
		hp = MAX_HEALTH;
		activity = BaseActivity.getSharedInstance();


		sprite = new AnimatedSprite(0, 0, activity.regBanana, activity.getVertexBufferObjectManager());
		
		mCamera = BaseActivity.getSharedInstance().mCamera;

		sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2, mCamera.getHeight() - sprite.getHeight() - 25);

		moveable = true;
	}

	public void animateShip(int delay) {
		sprite.animate(delay);
	}

	public void moveShip(float accelerometerSpeedX) {
		if (!moveable){

			sprite.stopAnimation();
			sprite.setCurrentTileIndex(9);
			return;
		}
		
		if (accelerometerSpeedX != 0) {

			int lL = 0;
			int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());

			float newX;

			if (sprite.getX() >= lL) {
				newX = sprite.getX() + accelerometerSpeedX;
			} else
				newX = lL;
			if (newX <= rL)
				newX = sprite.getX() + accelerometerSpeedX;
			else
				newX = rL;

			if (newX < lL)
				newX = lL;
			else if (newX > rL)
				newX = rL;
			// j
			if (newXX != -1) {
				prevmovement = movement;
				if (newXX < newX)
					movement = RIGHT;
				else if (newXX > newX)
					movement = LEFT;
				else if (accelerometerSpeedX > -0.26 && accelerometerSpeedX < 0.26)
					movement = STOP;

				if (prevmovement != movement)
					if (movement == RIGHT)
						sprite.animate(new long[]{200, 200, 200, 200,200,200}, 2, 7, true);
					else if (movement == LEFT)
						sprite.animate(new long[]{200, 200, 200, 200,200,200}, 10, 15, true);
					else if (movement == STOP)
						sprite.animate(new long[]{300, 900}, 1, 2, true);
			}
			newXX = newX;
			sprite.setPosition(newX, sprite.getY());

		}
	}

	public void shoot() {
		if (!moveable)
			return;
		GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;

		Bullet b = BulletPool.sharedBulletPool().obtainPoolItem();
		b.sprite.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY());
		MoveYModifier mod = new MoveYModifier(1.5f, b.sprite.getY(), b.sprite.getHeight());

		b.sprite.setVisible(true);
		b.sprite.detachSelf();
		scene.attachChild(b.sprite);
		scene.bulletList.add(b);
		b.sprite.registerEntityModifier(mod);

		scene.bulletCount++;
	}

	public void restart() {
		moveable = false;
		
		Camera mCamera = BaseActivity.getSharedInstance().mCamera;
		MoveXModifier mod = new MoveXModifier(0.2f, sprite.getX(), mCamera.getWidth() / 2 - sprite.getWidth() / 2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				moveable = true;

			}
		};
		sprite.registerEntityModifier(mod);

	}
	
	public boolean gotHit() {
		synchronized (this) {
			hp--;
			
			if (hp <= 0)
				return false;
			else
				return true;
		}
	}

}
