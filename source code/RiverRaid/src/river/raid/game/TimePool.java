//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package river.raid.game;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class TimePool extends GenericPool<Time> {

	public static TimePool instance;

	public static TimePool sharedBulletPool() {
		if (instance == null)
			instance = new TimePool();
		return instance;

	}

	private TimePool() {
		super();
	}

	@Override
	protected Time onAllocatePoolItem() {
		return new Time();
	}

	protected void onHandleRecycleItem(final Bullet b) {
		b.sprite.clearEntityModifiers();
		b.sprite.clearUpdateHandlers();
		b.sprite.setVisible(false);
		b.sprite.detachSelf();
		Log.v("Jimvaders", "BulletPool onHandleRecycleItem()");
	}
}