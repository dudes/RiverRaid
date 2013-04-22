package river.raid.game;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;

public class Package {

//	public Rectangle sprite;
	BaseActivity activity;
	Text result;
	AnimatedSprite sprite;
	public Package() {
		activity = BaseActivity.getSharedInstance();
		sprite = new AnimatedSprite(0, 0, activity.regPackage, activity.getVertexBufferObjectManager());
		sprite.setCurrentTileIndex(0);
//		sprite = new Rectangle(0, 0, 10, 10, BaseActivity.getSharedInstance()
//				.getVertexBufferObjectManager());
//
//		sprite.setColor(0.09904f, 0f, 0.1786f);
	}
}
