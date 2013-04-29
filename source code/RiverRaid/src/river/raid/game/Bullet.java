package river.raid.game;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;

	/*
		Klasa pocisku
	*/
public class Bullet {

	BaseActivity activity;
	Text result;
	TiledSprite sprite;
	public Bullet() {
		activity = BaseActivity.getSharedInstance();
		sprite = new TiledSprite(0, 0, activity.regAdditions, activity.getVertexBufferObjectManager());
		sprite.setCurrentTileIndex(7);
	}
}
