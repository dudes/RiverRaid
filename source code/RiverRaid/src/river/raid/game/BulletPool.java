//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package river.raid.game;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class BulletPool extends GenericPool<Bullet> {

	public static BulletPool instance;

	public static BulletPool sharedBulletPool() {
		if (instance == null)
			instance = new BulletPool();
		return instance;

	}

	private BulletPool() {
		super();
	}

	@Override
	protected Bullet onAllocatePoolItem() {
		return new Bullet();
	}

	protected void onHandleRecycleItem(final Bullet b) {
		b.sprite.clearEntityModifiers();
		b.sprite.clearUpdateHandlers();
		b.sprite.setVisible(false);
		b.sprite.detachSelf();
		Log.v("Jimvaders", "BulletPool onHandleRecycleItem()");
	}
}