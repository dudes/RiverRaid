package river.raid.game;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

public class Enemy {
//	public Rectangle sprite;
	public int hp;
	// the max health for each enemy
	protected final int MAX_HEALTH = 2;

	
	private BaseActivity activity;

	public BitmapTextureAtlas mBitmapTextureAtlas;
	public TextureRegion mTextureRegion;
//	private static int SPR_COLUMN = 3;
//	private static int SPR_ROWS = 4;
//	private BitmapTextureAtlas texBanana;
//	private TiledTextureRegion regBanana;
	public AnimatedSprite sprite;
	public int direction;
	
	
	public Enemy() {

		activity = BaseActivity .getSharedInstance();
		Log.d("kierunek", direction+"");
		sprite = new AnimatedSprite(0, 0, TailedTexRes.getInstance().regPlanes, activity.getVertexBufferObjectManager());
		if(direction == 0)
			sprite.animate(new long[]{100,100,100},9,11,true);
		if(direction == 1)
			sprite.animate(new long[]{100,100,100},6,8,true);
		init();
	}


	public void init() {
		hp = MAX_HEALTH;

	}

	public void clean() {
		sprite.clearEntityModifiers();
		sprite.clearUpdateHandlers();
	}

	// method for applying hit and checking if enemy died or not
	// returns false if enemy died
	public boolean gotHit() {
		synchronized (this) {
			hp--;
			if (hp <= 0)
				return false;
			else
				return true;
		}
	}
	public void setAnimation(){}
}
