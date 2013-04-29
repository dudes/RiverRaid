package river.raid.game;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;

/*
 Klasa przeciwnika
*/

public class Enemy {
	public int hp;
	protected final int MAX_HEALTH = 2;
	private BaseActivity activity;
	public BitmapTextureAtlas mBitmapTextureAtlas;
	public TextureRegion mTextureRegion;
	public AnimatedSprite sprite;
	public int direction;
	
	
	public Enemy() {

		activity = BaseActivity .getSharedInstance();
		Log.d("kierunek", direction+"");
		sprite = new AnimatedSprite(0, 0, activity.regPlanes, activity.getVertexBufferObjectManager());
		if(direction == 0)
			sprite.animate(new long[]{100,100,100},9,11,true);
		if(direction == 1)
			sprite.animate(new long[]{100,100,100},6,8,true);
		init();
	}


	/*
	 Ustawianie zycia przeciwnikowi podczas jego tworzenia
	*/
	public void init() {
		hp = MAX_HEALTH;

	}

	/*
		Czyszczenie ekranu z widocznych przeciwnikow
	*/
	public void clean() {
		sprite.clearEntityModifiers();
		sprite.clearUpdateHandlers();
	}

	/*
		Sprawdzenie czy przeciwnik jest żywy
	*/
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
