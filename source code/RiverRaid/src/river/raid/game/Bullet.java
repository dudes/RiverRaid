package river.raid.game;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;

public class Bullet {

//	public Rectangle sprite;
	BaseActivity activity;
	Text result;
	TiledSprite sprite;
	public Bullet() {
		activity = BaseActivity.getSharedInstance();
		sprite = new TiledSprite(0, 0, activity.regAdditions, activity.getVertexBufferObjectManager());
		sprite.setCurrentTileIndex(7);
//		sprite = new Rectangle(0, 0, 10, 10, BaseActivity.getSharedInstance()
//				.getVertexBufferObjectManager());
//
//		sprite.setColor(0.09904f, 0f, 0.1786f);
	}
}
